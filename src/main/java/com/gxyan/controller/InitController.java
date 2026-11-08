package com.gxyan.controller;

import com.gxyan.common.ServerResponse;
import com.gxyan.service.IInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("init") // 定义请求路径前缀为/init
public class InitController {

    @Autowired
    private IInitService initService; // 自动注入 IInitService

    // 获取系列选项
    @RequestMapping(value = "seriesOpt", method = RequestMethod.GET)
    public ServerResponse seriesOpt() {
        return initService.seriesOpt(); // 调用服务层获取系列选项
    }

    // 获取品牌选项
    @RequestMapping(value = "brandOpt", method = RequestMethod.GET)
    public ServerResponse brandOpt() {
        return initService.brandOpt(); // 调用服务层获取品牌选项
    }

    // 获取门店选项
    @RequestMapping(value = "storeOpt", method = RequestMethod.GET)
    public ServerResponse storeOpt(Integer seriesId) {
        return initService.storeOpt(seriesId); // 调用服务层获取指定系列的门店选项
    }

    // 获取客户信息
    @RequestMapping(value = "getCustomer", method = RequestMethod.GET)
    public ServerResponse getCustomer(String idCard) {
        return initService.getCustomer(idCard); // 调用服务层根据身份证获取客户信息
    }
}
