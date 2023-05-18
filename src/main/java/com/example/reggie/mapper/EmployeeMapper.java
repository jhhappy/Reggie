package com.example.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.reggie.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yangyibufeng
 * @Description
 * @date 2023/4/21-17:23
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
