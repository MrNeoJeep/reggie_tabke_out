package com.future.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.future.reggie.pojo.Category;

public interface CategoryService extends IService<Category> {

    public void remove(Long id);
}
