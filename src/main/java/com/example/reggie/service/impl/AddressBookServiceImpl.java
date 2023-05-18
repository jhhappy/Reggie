package com.example.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.reggie.entity.AddressBook;
import com.example.reggie.mapper.AddressBookMapper;
import com.example.reggie.service.AddressBookService;
import org.springframework.stereotype.Service;

/**
 * @author Ustinain
 * @Time 2023/5/16  12:19
 */
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}
