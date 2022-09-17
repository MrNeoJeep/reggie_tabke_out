package com.future.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.future.reggie.mapper.AddressBookMapper;
import com.future.reggie.pojo.AddressBook;
import com.future.reggie.service.AddressBookService;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {

}
