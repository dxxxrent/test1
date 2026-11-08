package com.gxyan.service.impl;

import com.gxyan.common.ServerResponse;
import com.gxyan.dao.BrandMapper;
import com.gxyan.dao.CarMapper;
import com.gxyan.dao.CustomerMapper;
import com.gxyan.dao.SeriesMapper;
import com.gxyan.pojo.Brand;
import com.gxyan.pojo.Car;
import com.gxyan.pojo.Customer;
import com.gxyan.pojo.Series;
import com.gxyan.service.IInitService;
import com.gxyan.vo.SeriesTree;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 初始化
 */

@Slf4j // 日志记录
@Service // 标记为服务层组件
public class InitServiceImpl implements IInitService {

    @Autowired
    private BrandMapper brandMapper; // 品牌数据访问对象
    @Autowired
    private SeriesMapper seriesMapper; // 系列数据访问对象
    @Autowired
    private CarMapper carMapper; // 汽车数据访问对象
    @Autowired
    private CustomerMapper customerMapper; // 客户数据访问对象

    @Override
    public ServerResponse<List<SeriesTree>> seriesOpt() {
        List<Brand> brandList = brandMapper.getBrand(); // 获取品牌列表

        if (!CollectionUtils.isEmpty(brandList)) {
            List<SeriesTree> treeList = new ArrayList<>();
            for (Brand brandItem : brandList) {
                SeriesTree tree = new SeriesTree();
                tree.setValue(brandItem.getBrandId()); // 设置品牌ID
                tree.setLabel(brandItem.getBrandName()); // 设置品牌名称

                List<Series> seriesList = seriesMapper.selectSeriesByBrand(brandItem.getBrandId()); // 获取系列列表
                if (!CollectionUtils.isEmpty(seriesList)) {
                    List<SeriesTree> seriesTreeList = new ArrayList<>();
                    for (Series series : seriesList) {
                        SeriesTree seriesTree = new SeriesTree();
                        seriesTree.setValue(series.getSeriesId()); // 设置系列ID
                        seriesTree.setLabel(series.getSeriesName()); // 设置系列名称
                        seriesTreeList.add(seriesTree); // 添加系列到列表
                    }
                    tree.setChildren(seriesTreeList); // 设置子系列
                    treeList.add(tree); // 添加品牌树到列表
                }
            }
            return ServerResponse.createBySuccess(treeList); // 返回成功响应
        }
        return ServerResponse.createByErrorMessage("未知错误，请联系管理员"); // 返回错误信息
    }

    @Override
    public ServerResponse<List<Brand>> brandOpt() {
        List<Brand> brandList = brandMapper.getBrand(); // 获取品牌列表

        if (!CollectionUtils.isEmpty(brandList)) {
            return ServerResponse.createBySuccess(brandList); // 返回成功响应
        } else {
            return ServerResponse.createByError(); // 返回错误响应
        }
    }

    @Override
    public ServerResponse storeOpt(Integer seriesId) {
        List<Car> list = carMapper.selectBySeriesId(seriesId); // 根据系列ID获取汽车列表

        if (!CollectionUtils.isEmpty(list)) {
            return ServerResponse.createBySuccess(list); // 返回成功响应
        } else {
            return ServerResponse.createByError(); // 返回错误响应
        }
    }

    @Override
    public ServerResponse getCustomer(String idCard) {
        Customer customer = customerMapper.selectByIdCard(idCard); // 根据身份证获取客户信息

        if (customer != null) {
            return ServerResponse.createBySuccess(customer); // 返回成功响应
        } else {
            return ServerResponse.createByErrorMessage("客户不存在"); // 返回错误信息
        }
    }
}
