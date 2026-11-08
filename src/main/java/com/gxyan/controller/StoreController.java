package com.gxyan.controller;

import com.gxyan.common.ServerResponse;
import com.gxyan.pojo.Car;
import com.gxyan.service.IStoreService;
import com.gxyan.vo.StoreQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("store") // 定义请求路径前缀为/store
public class StoreController {

    // 自动注入 IStoreService
    @Autowired
    private IStoreService storeService;

    // 添加品牌
    @RequestMapping(value = "addBrand", method = RequestMethod.GET)
    public ServerResponse addBrand(String brand) {
        return storeService.addBrand(brand);
    }

    // 删除品牌
    @RequestMapping(value = "delBrand", method = RequestMethod.GET)
    public ServerResponse delBrand(Integer brandId) {
        return storeService.delBrand(brandId);
    }

    // 添加系列
    @RequestMapping(value = "addSeries", method = RequestMethod.GET)
    public ServerResponse addSeries(Integer brandId, String seriesName) {
        return storeService.addSeries(brandId, seriesName);
    }

    // 删除系列
    @RequestMapping(value = "delSeries", method = RequestMethod.GET)
    public ServerResponse delSeries(Integer seriesId) {
        return storeService.delSeries(seriesId);
    }

    // 添加商店
    @RequestMapping(value = "addStore", method = RequestMethod.POST)
    public ServerResponse addStore(Car car) {
        return storeService.addStore(car);
    }

    // 获取商店列表
    @RequestMapping(value = "getList", method = RequestMethod.GET)
    public ServerResponse getList(StoreQuery storeQuery) {
        return storeService.getList(storeQuery);
    }

    // 更新商店信息
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public ServerResponse update(Car car) {
        return storeService.updateStore(car);
    }
}
