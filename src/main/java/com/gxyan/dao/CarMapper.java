package com.gxyan.dao;

import com.gxyan.pojo.Car;
import com.gxyan.vo.StoreList;
import com.gxyan.vo.StoreQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CarMapper {
    /**
     * 根据ID删除汽车记录
     * @param id 汽车ID
     * @return 删除成功的记录数
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 新增汽车记录
     * @param record 汽车对象
     * @return 插入成功的记录数
     */
    int insert(Car record);

    /**
     * 选择性新增汽车记录，仅插入非空字段
     * @param record 汽车对象
     * @return 插入成功的记录数
     */
    int insertSelective(Car record);

    /**
     * 根据ID查询汽车记录
     * @param id 汽车ID
     * @return 汽车对象
     */
    Car selectByPrimaryKey(Long id);

    /**
     * 根据ID选择性更新汽车信息
     * @param record 汽车对象
     * @return 更新成功的记录数
     */
    int updateByPrimaryKeySelective(Car record);

    /**
     * 根据ID更新汽车信息
     * @param record 汽车对象
     * @return 更新成功的记录数
     */
    int updateByPrimaryKey(Car record);

    /**
     * 根据查询条件选择性查询商店列表
     * @param storeQuery 查询条件
     * @return 商店列表
     */
    List<StoreList> selectSelective(StoreQuery storeQuery);

    /**
     * 根据系列ID查询汽车列表
     * @param seriesId 系列ID
     * @return 汽车列表
     */
    List<Car> selectBySeriesId(Integer seriesId);

    /**
     * 根据汽车ID查询库存数量
     * @param carId 汽车ID
     * @return 库存数量
     */
    int selectRepertoryByPrimaryKey(Long carId);

    /**
     * 更新汽车库存数量
     * @param carId 汽车ID
     * @param carNumber 更新的数量
     * @return 更新成功的记录数
     */
    int updateRepertoryByPrimaryKey(@Param("carId") Long carId, @Param("carNumber") Integer carNumber);

    /**
     * 增加汽车库存数量
     * @param carId 汽车ID
     * @param carNumber 增加的数量
     * @return 更新成功的记录数
     */
    int addRepertoryByPrimaryKey(@Param("carId") Long carId, @Param("carNumber") Integer carNumber);

    /**
     * 根据汽车ID查询销售价格
     * @param carId 汽车ID
     * @return 销售价格
     */
    BigDecimal selectSalePriceByPrimaryKey(Long carId);
}
