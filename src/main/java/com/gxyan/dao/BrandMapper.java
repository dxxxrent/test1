package com.gxyan.dao;

import com.gxyan.pojo.Brand;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandMapper {
    /**
     * 根据品牌ID删除品牌
     * @param brandId 品牌ID
     * @return 删除成功的记录数
     */
    int deleteByPrimaryKey(Integer brandId);

    /**
     * 新增一个品牌
     * @param record 品牌对象
     * @return 插入成功的记录数
     */
    int insert(Brand record);

    /**
     * 新增一个品牌，选择性插入非空字段
     * @param record 品牌对象
     * @return 插入成功的记录数
     */
    int insertSelective(Brand record);

    /**
     * 根据品牌ID查询品牌
     * @param brandId 品牌ID
     * @return 品牌对象
     */
    Brand selectByPrimaryKey(Integer brandId);

    /**
     * 根据品牌ID选择性更新品牌信息
     * @param record 品牌对象
     * @return 更新成功的记录数
     */
    int updateByPrimaryKeySelective(Brand record);

    /**
     * 根据品牌ID更新品牌信息
     * @param record 品牌对象
     * @return 更新成功的记录数
     */
    int updateByPrimaryKey(Brand record);

    /**
     * 获取所有品牌
     * @return 品牌列表
     */
    List<Brand> getBrand();

    /**
     * 根据品牌名称查询品牌
     * @param brandName 品牌名称
     * @return 品牌对象
     */
    Brand selectByBrandName(String brandName);
}
