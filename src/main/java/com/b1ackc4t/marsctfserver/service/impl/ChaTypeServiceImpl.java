package com.b1ackc4t.marsctfserver.service.impl;

import com.b1ackc4t.marsctfserver.dao.ChaTypeMapper;
import com.b1ackc4t.marsctfserver.pojo.ChaType;
import com.b1ackc4t.marsctfserver.service.ChaTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ChaTypeServiceImpl extends ServiceImpl<ChaTypeMapper, ChaType> implements ChaTypeService {
}
