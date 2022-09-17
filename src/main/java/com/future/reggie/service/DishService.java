package com.future.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.future.reggie.dto.DishDto;
import com.future.reggie.pojo.Dish;
import org.springframework.transaction.annotation.Transactional;

public interface DishService extends IService<Dish> {

    //新增菜品，同时插入对应的口味数据
    @Transactional
    public void saveWithFlavor(DishDto dishDto);

    public DishDto getByIdWithFlavor(Long id);

    /**
     * 更新菜品信息，同时更新口味信息
     * @param dishDto
     */
    @Transactional
    public void updateWithFlavor(DishDto dishDto);

}
