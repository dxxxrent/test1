package com.gxyan.service.impl;

import com.gxyan.common.ServerResponse;
import com.gxyan.dao.OrderDetailsMapper;
import com.gxyan.dao.OrderMapper;
import com.gxyan.service.IChartService;
import com.gxyan.vo.EmpChart;
import com.gxyan.vo.IndexSales;
import com.gxyan.vo.SalesChart;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 销售额相关业务
 */

@Slf4j // 日志记录
@Service // 标记为服务层组件
public class ChartServiceImpl implements IChartService {

    @Autowired // 自动注入 OrderMapper
    private OrderMapper orderMapper;

    @Autowired // 自动注入 OrderDetailsMapper
    private OrderDetailsMapper detailsMapper;

    @Override
    public ServerResponse getEmpChart(String date) {
        // 根据日期获取员工图表数据
        List<EmpChart> chartData = orderMapper.selectChartByDate(date);
        if (chartData != null) {
            return ServerResponse.createBySuccess(chartData); // 返回成功响应
        }
        return ServerResponse.createByErrorMessage("木有数据哦"); // 返回错误信息
    }

    @Override
    public ServerResponse getIndexChart() {
        // 获取昨天的员工图表数据
        List<EmpChart> chartData = orderMapper.selectYesterdayChart();
        if (chartData != null) {
            return ServerResponse.createBySuccess(chartData); // 返回成功响应
        }
        return ServerResponse.createByErrorMessage("木有数据哦"); // 返回错误信息
    }

    @Override
    public ServerResponse getSaleNum() {
        // 获取昨天的销售数量
        int num = detailsMapper.selectYesterdayNum();
        return ServerResponse.createBySuccess(num); // 返回销售数量
    }

    @Override
    public ServerResponse getSalesChart(String start, String end) {
        // 根据起止日期获取销售图表数据
        List<SalesChart> salesCharts = detailsMapper.selectSalesChart(start, end);
        if (salesCharts != null) {
            return ServerResponse.createBySuccess(salesCharts); // 返回成功响应
        }
        return ServerResponse.createByErrorMessage("木有数据哦"); // 返回错误信息
    }

    @Override
    public ServerResponse getIndexSales(Integer id) {
        // 根据 ID 获取特定的销售数据
        IndexSales indexSales = detailsMapper.selectIndexSales(id);
        if (indexSales != null) {
            return ServerResponse.createBySuccess(indexSales); // 返回成功响应
        }
        return ServerResponse.createByErrorMessage("木有数据哦"); // 返回错误信息
    }

    @Override
    public ServerResponse getEmpSalesChart(Integer id, String date) {
        // 根据员工 ID 和日期获取员工销售图表数据
        List<EmpChart> chartData = detailsMapper.selectEmpChart(id, date);
        if (chartData != null) {
            return ServerResponse.createBySuccess(chartData); // 返回成功响应
        }
        return ServerResponse.createByErrorMessage("木有数据哦"); // 返回错误信息
    }
}
