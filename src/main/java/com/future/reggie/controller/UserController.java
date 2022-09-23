package com.future.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.future.reggie.common.R;
import com.future.reggie.pojo.User;
import com.future.reggie.service.UserService;
import com.future.reggie.utils.SMSUtils;
import com.future.reggie.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;


    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session){
        //获取手机号
        String phone = user.getPhone();
        if(StringUtils.isNotEmpty(phone)){
            //生成4位随机验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("验证码为：{}",code);
            //调用阿里云API
            SMSUtils.sendMessage("阿里云短信测试","SMS_154950909",phone,code);
            //生成的验证码保存到session中
            //session.setAttribute(phone,code);

            //将验证码缓存到redis中，并设置有效期
            redisTemplate.opsForValue().set(phone,code, 5,TimeUnit.MINUTES);

            return R.success("手机验证码发送成功");
        }

        return R.error("手机验证码发送失败");
    }

    /**
     * 移动端用户登录
     * @param
     * @param session
     * @return
     */
    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session){
        log.info(map.toString());
        //获取手机号
        String phone = map.get("phone").toString();
        //获取验证码
        String code = map.get("code").toString();
        //比对
        //Object codeInSession = session.getAttribute(phone);
        
        //从redis中获取验证码
        Object codeInSession = redisTemplate.opsForValue().get(phone);
        //判断用户是否为新用户
        if(codeInSession != null && codeInSession.equals(code)){
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone,phone);
            User user = userService.getOne(queryWrapper);
            if(user == null){
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }
            session.setAttribute("user",user.getId());

            //登录成功，删除缓存
            redisTemplate.delete(phone);

            return R.success(user);
        }
        return R.error("验证码不正确");
    }
}
