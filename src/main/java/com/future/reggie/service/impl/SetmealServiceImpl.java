package com.future.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.future.reggie.common.CustomException;
import com.future.reggie.dto.SetmealDto;
import com.future.reggie.mapper.SetmealMapper;
import com.future.reggie.pojo.Setmeal;
import com.future.reggie.pojo.SetmealDish;
import com.future.reggie.service.SetmealDishService;
import com.future.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;
    @Override
    public void saveWithDish(SetmealDto setmealDto) {
        //保存套餐的基本信息，操作setmeal
        this.save(setmealDto);

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map((item) ->{
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());
        //保存套餐和菜品的关联关系
        setmealDishService.saveBatch(setmealDishes);


    }


    /**
     * 删除套餐，同时需要删除套餐和菜品的关联数据
     * @param ids
     */

    @Override
    public void removeWithDish(List<Long> ids) {
        //查询套餐的状态，是否可以删除
        LambdaQueryWrapper<Setmeal> queryWrapper= new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId,ids);
        queryWrapper.eq(Setmeal::getStatus,1);
        int count = this.count(queryWrapper);
        if(count > 0){
            throw new CustomException("套餐正在售卖，不能删除");
        }
        //可以删除，先删除套餐表中的数据
        this.removeByIds(ids);


        //删除关系表中的数据
        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SetmealDish::getSetmealId,ids);

        setmealDishService.remove(lambdaQueryWrapper);

    }
}
