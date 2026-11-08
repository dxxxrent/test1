package com.gxyan.controller;

import com.gxyan.common.ServerResponse;
import com.gxyan.pojo.OrderDetails;
import com.gxyan.service.IOrderService;
import com.gxyan.vo.DetailsQuery;
import com.gxyan.vo.OrderQuery;
import com.gxyan.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order") // 定义请求路径前缀为/order
public class OrderController {

    @Autowired
    private IOrderService orderService; // 自动注入 IOrderService

    // 添加订单
    @RequestMapping(value = "addOrder", method = RequestMethod.POST)
    public ServerResponse addOrder(@RequestBody OrderVo orderVo) {
        return orderService.addOrder(orderVo); // 调用服务层添加订单
    }

    // 获取订单列表
    @RequestMapping(value = "getList", method = RequestMethod.GET)
    public ServerResponse getList(OrderQuery orderQuery) {
        return orderService.getList(orderQuery); // 调用服务层获取订单列表
    }

    // 更新订单状态
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public ServerResponse update(Long orderId, String status) {
        return orderService.update(orderId, status); // 调用服务层更新订单状态
    }

    // 更新订单详情
    @RequestMapping(value = "updateDetail", method = RequestMethod.POST)
    public ServerResponse updateDetail(OrderDetails orderDetails) {
        return orderService.updateDetail(orderDetails); // 调用服务层更新订单详情
    }

    // 删除订单详情
    @RequestMapping(value = "deleteDetail", method = RequestMethod.POST)
    public ServerResponse deleteDetail(String id) {
        return orderService.deleteDetail(id); // 调用服务层删除订单详情
    }

    // 获取订单详情列表
    @RequestMapping(value = "getDetailsList", method = RequestMethod.GET)
    public ServerResponse getDetailsList(DetailsQuery detailsQuery) {
        return orderService.getDetailsList(detailsQuery); // 调用服务层获取订单详情列表
    }
}
