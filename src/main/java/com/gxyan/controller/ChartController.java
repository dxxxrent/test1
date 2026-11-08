package com.gxyan.controller;

import com.gxyan.common.ServerResponse;
import com.gxyan.service.IChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据可视化echarts的数据访问
 * 处理员工的销售量
 */
@RestController
@RequestMapping("chart")
public class ChartController {

    @Autowired
    private IChartService chartService;

    /**
     * 获取 全部员工的月销量报表 数据
     * @param date
     * @return
     */
    @RequestMapping(value = "getEmpChart", method = RequestMethod.GET)
    public ServerResponse getEmpChart(String date) {
        return chartService.getEmpChart(date);
    }

    /**
     * 获取经理主页 昨日销量报表 数据
     * @return
     */
    @RequestMapping(value = "getIndexChart", method = RequestMethod.GET)
    public ServerResponse getIndexChart() {
        return chartService.getIndexChart();
    }

    /**
     * 获取经理主页 昨日销量
     * @return
     */
    @RequestMapping(value = "getSaleNum", method = RequestMethod.GET)
    public ServerResponse getSaleNum() {
        return chartService.getSaleNum();
    }

    /**
     * 获取 销售报表 数据
     * @param start
     * @param end
     * @return
     */
    @RequestMapping(value = "getSalesChart", method = RequestMethod.GET)
    public ServerResponse getSalesChart(String start, String end) {
        return chartService.getSalesChart(start, end);
    }

    /**
     * 获取员工主页 本月销售额 数据
     * @param id
     * @return
     */
    @RequestMapping(value = "getIndexSales", method = RequestMethod.GET)
    public ServerResponse getIndexSales(Integer id) {
        return chartService.getIndexSales(id);
    }

    @RequestMapping(value = "getEmpSalesChart", method = RequestMethod.GET)
    public ServerResponse getEmpSalesChart(Integer id, String date) {
        return chartService.getEmpSalesChart(id, date);
    }
}
