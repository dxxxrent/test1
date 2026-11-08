package com.gxyan.dao;

import com.gxyan.pojo.Order;
import com.gxyan.vo.EmpChart;
import com.gxyan.vo.OrderQuery;
import com.gxyan.vo.OrderList;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OrderMapper {
    /**
     * 根据主键删除订单
     * @param id 订单ID
     * @return 删除成功的记录数
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入新的订单
     * @param record 订单对象
     * @return 插入成功的记录数
     */
    int insert(Order record);

    /**
     * 选择性插入订单，仅插入非空字段
     * @param record 订单对象
     * @return 插入成功的记录数
     */
    int insertSelective(Order record);

    /**
     * 根据主键查询订单
     * @param id 订单ID
     * @return 订单对象
     */
    Order selectByPrimaryKey(Long id);

    /**
     * 根据主键选择性更新订单
     * @param record 订单对象
     * @return 更新成功的记录数
     */
    int updateByPrimaryKeySelective(Order record);

    /**
     * 根据主键更新订单
     * @param record 订单对象
     * @return 更新成功的记录数
     */
    int updateByPrimaryKey(Order record);

    /**
     * 根据订单ID更新支付时间
     * @param orderId 订单ID
     * @return 更新成功的记录数
     */
    int updatePayTimeByPrimaryKey(Long orderId);

    /**
     * 根据查询条件查询销售订单列表
     * @param orderQuery 查询条件
     * @return 销售订单列表
     */
    List<OrderList> selectSale(OrderQuery orderQuery);

    /**
     * 更新订单状态
     * @param orderId 订单ID
     * @param status 新状态
     * @return 更新成功的记录数
     */
    int updateStatusByPrimaryKey(@Param("orderId") Long orderId, @Param("status") String status);

    /**
     * 根据订单ID更新总价
     * @param orderId 订单ID
     * @param totalPrice 新的总价
     * @return 更新成功的记录数
     */
    int addTotalPriceByPrimaryKey(@Param("orderId") Long orderId, @Param("totalPrice") BigDecimal totalPrice);

    /**
     * 根据日期查询员工销售图表数据
     * @param date 日期
     * @return 员工销售图表数据列表
     */
    List<EmpChart> selectChartByDate(String date);

    /**
     * 查询昨日的员工销售图表数据
     * @return 昨日员工销售图表数据列表
     */
    List<EmpChart> selectYesterdayChart();
}
