package com.example.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.reggie.entity.ShoppingCart;
import com.example.reggie.mapper.ShoppingCartMapper;
import com.example.reggie.service.ShoppingCartService;
import org.springframework.stereotype.Service;

/**
 * @author Ustinain
 * @Time 2023/5/16  17:16
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart>implements ShoppingCartService {
}
