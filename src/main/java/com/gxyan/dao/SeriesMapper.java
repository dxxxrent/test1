package com.gxyan.dao;

import com.gxyan.pojo.Series;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeriesMapper {
    /**
     * 根据主键删除系列
     * @param seriesId 系列ID
     * @return 删除成功的记录数
     */
    int deleteByPrimaryKey(Integer seriesId);

    /**
     * 插入新的系列
     * @param record 系列对象
     * @return 插入成功的记录数
     */
    int insert(Series record);

    /**
     * 选择性插入系列，仅插入非空字段
     * @param record 系列对象
     * @return 插入成功的记录数
     */
    int insertSelective(Series record);

    /**
     * 根据主键查询系列
     * @param seriesId 系列ID
     * @return 系列对象
     */
    Series selectByPrimaryKey(Integer seriesId);

    /**
     * 根据主键选择性更新系列
     * @param record 系列对象
     * @return 更新成功的记录数
     */
    int updateByPrimaryKeySelective(Series record);

    /**
     * 根据主键更新系列
     * @param record 系列对象
     * @return 更新成功的记录数
     */
    int updateByPrimaryKey(Series record);

    /**
     * 根据品牌ID查询系列列表
     * @param brandId 品牌ID
     * @return 系列列表
     */
    List<Series> selectSeriesByBrand(Integer brandId);

    /**
     * 根据品牌ID和系列名称查询系列
     * @param brandId 品牌ID
     * @param seriesName 系列名称
     * @return 系列对象
     */
    Series selectSeriesByBrandIdAndSeriesName(@Param("brandId") Integer brandId, @Param("seriesName") String seriesName);
}
