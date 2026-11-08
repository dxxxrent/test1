package com.gxyan.controller;

import com.gxyan.common.ServerResponse;
import com.gxyan.pojo.Employee;
import com.gxyan.service.IEmployeeService;
import com.gxyan.vo.EmployeeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 员工控制器
 * 处理与员工相关的请求
 */
@RestController // 标记为控制器
@RequestMapping("employee") // 路径前缀
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService; // 员工服务接口

    // 添加员工接口
    @RequestMapping(value = "addEmployee", method = RequestMethod.POST)
    public ServerResponse addEmployee(Employee employee) {
        return employeeService.addEmployee(employee); // 调用服务添加员工
    }

    // 获取员工列表接口
    @RequestMapping(value = "getList", method = RequestMethod.GET)
    public ServerResponse getList(EmployeeQuery employeeQuery) {
        return employeeService.getList(employeeQuery); // 调用服务获取员工列表
    }

    // 更新员工信息接口
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public ServerResponse update(Employee employee) {
        return employeeService.updateEmployee(employee); // 调用服务更新员工信息
    }
}
