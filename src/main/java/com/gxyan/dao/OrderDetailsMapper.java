package com.gxyan.dao;

import com.gxyan.pojo.OrderDetails;
import com.gxyan.vo.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailsMapper {
    /**
     * 根据主键删除订单详情
     * @param id 订单详情ID
     * @return 删除成功的记录数
     */
    int deleteByPrimaryKey(String id);

    /**
     * 插入新的订单详情
     * @param record 订单详情对象
     * @return 插入成功的记录数
     */
    int insert(OrderDetails record);

    /**
     * 选择性插入订单详情，仅插入非空字段
     * @param record 订单详情对象
     * @return 插入成功的记录数
     */
    int insertSelective(OrderDetails record);

    /**
     * 根据主键查询订单详情
     * @param id 订单详情ID
     * @return 订单详情对象
     */
    OrderDetails selectByPrimaryKey(String id);

    /**
     * 根据主键选择性更新订单详情
     * @param record 订单详情对象
     * @return 更新成功的记录数
     */
    int updateByPrimaryKeySelective(OrderDetails record);

    /**
     * 根据主键更新订单详情
     * @param record 订单详情对象
     * @return 更新成功的记录数
     */
    int updateByPrimaryKey(OrderDetails record);

    /**
     * 根据订单ID查询订单详情列表
     * @param orderId 订单ID
     * @return 订单详情列表
     */
    List<OrderDetails> selectByOrderId(Long orderId);

    /**
     * 根据订单ID查询具体详情
     * @param orderId 订单ID
     * @return 具体详情列表
     */
    List<Details> selectDetailsByOrderId(Long orderId);

    /**
     * 根据查询条件选择性查询订单详情
     * @param detailsQuery 查询条件
     * @return 订单详情列表
     */
    List<DetailsList> selectSelective(DetailsQuery detailsQuery);

    /**
     * 查询昨日的订单数量
     * @return 昨日订单数量
     */
    int selectYesterdayNum();

    /**
     * 根据时间范围查询销售图表数据
     * @param start 开始时间
     * @param end 结束时间
     * @return 销售图表数据列表
     */
    List<SalesChart> selectSalesChart(@Param("start") String start, @Param("end") String end);

    /**
     * 根据员工ID查询首页销售数据
     * @param id 员工ID
     * @return 首页销售数据对象
     */
    IndexSales selectIndexSales(Integer id);

    /**
     * 根据员工ID和日期查询员工销售图表数据
     * @param id 员工ID
     * @param date 日期
     * @return 员工销售图表数据列表
     */
    List<EmpChart> selectEmpChart(@Param("id") Integer id, @Param("date") String date);
}
