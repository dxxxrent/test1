package com.gxyan.controller;

import com.gxyan.common.ServerResponse;
import com.gxyan.pojo.Customer;
import com.gxyan.service.ICustomerService;
import com.gxyan.vo.CustomerQuery;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 处理客户相关的请求
 */
@Slf4j
@RestController
@RequestMapping("customer") // 定义请求路径前缀为/customer
public class CustomerController {

    @Autowired
    private ICustomerService customerService; // 自动注入 ICustomerService

    // 添加客户信息
    @RequestMapping(value = "addCustomer", method = RequestMethod.GET)
    public ServerResponse addCustomer(Customer customer) {
        return customerService.addCustomer(customer); // 调用服务层添加客户
    }

    // 获取客户列表
    @RequestMapping(value = "getList", method = RequestMethod.GET)
    public ServerResponse getList(CustomerQuery customerQuery) {
        return customerService.getList(customerQuery); // 调用服务层获取客户列表
    }

    // 导出当前筛选条件下的全部客户，不受列表分页影响
    @RequestMapping(value = "exportList", method = RequestMethod.GET)
    public ServerResponse<List<Customer>> exportList(CustomerQuery customerQuery) {
        return customerService.getExportList(customerQuery);
    }

    // 更新客户信息
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public ServerResponse update(Customer customer) {
        return customerService.updateCustomer(customer); // 调用服务层更新客户信息
    }
}
