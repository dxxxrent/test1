package com.gxyan.service.impl;

import com.gxyan.common.ServerResponse;
import com.gxyan.dao.EmployeeMapper;
import com.gxyan.pojo.Employee;
import com.gxyan.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 网站用户相关业务
 */

@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    // 自动注入 EmployeeMapper
    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public ServerResponse<Employee> login(Integer userId, String password) {
        // 检查用户ID是否存在
        int resultCount = employeeMapper.checkUserId(userId);
        if (resultCount == 0) {
            // 如果用户ID不存在，返回错误信息
            return ServerResponse.createByErrorMessage("用户名不存在");
        }

        // 查询用户的登录信息
        Employee employee = employeeMapper.selectLogin(userId, password);
        if (employee == null) {
            // 如果查询结果为空，返回密码错误信息
            return ServerResponse.createByErrorMessage("密码错误");
        }

        // 清空密码字段，避免明文密码泄露
        employee.setPassword(StringUtils.EMPTY);
        // 登录成功，返回成功信息和员工对象
        return ServerResponse.createBySuccess("登录成功", employee);
    }
}
