package com.gxyan.service;

import com.gxyan.common.ServerResponse;
import com.gxyan.pojo.Customer;
import com.gxyan.vo.CustomerQuery;

import java.util.List;

/**
 * @author gxyan
 * @date 2019/1/6 10:05
 */
public interface ICustomerService {
    ServerResponse addCustomer(Customer customer);

    ServerResponse getList(CustomerQuery customerQuery);

    ServerResponse<List<Customer>> getExportList(CustomerQuery customerQuery);

    ServerResponse updateCustomer(Customer customer);
}
