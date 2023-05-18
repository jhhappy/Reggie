package com.example.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.reggie.entity.Category;
import com.example.reggie.entity.Employee;

public interface CategoryService extends IService<Category> {
    public void remove(Long id);
}
