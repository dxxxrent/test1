package com.gxyan.dao;

import com.gxyan.pojo.Customer;
import com.gxyan.vo.CustomerQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerMapper {
    /**
     * 根据ID删除客户记录
     * @param id 客户ID
     * @return 删除成功的记录数
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 新增客户记录
     * @param record 客户对象
     * @return 插入成功的记录数
     */
    int insert(Customer record);

    /**
     * 选择性新增客户记录，仅插入非空字段
     * @param record 客户对象
     * @return 插入成功的记录数
     */
    int insertSelective(Customer record);

    /**
     * 根据ID查询客户记录
     * @param id 客户ID
     * @return 客户对象
     */
    Customer selectByPrimaryKey(Long id);

    /**
     * 根据ID选择性更新客户信息
     * @param record 客户对象
     * @return 更新成功的记录数
     */
    int updateByPrimaryKeySelective(Customer record);

    /**
     * 根据ID更新客户信息
     * @param record 客户对象
     * @return 更新成功的记录数
     */
    int updateByPrimaryKey(Customer record);

    /**
     * 根据查询条件选择性查询客户列表
     * @param customerQuery 查询条件
     * @return 客户列表
     */
    List<Customer> selectSelective(CustomerQuery customerQuery);

    /**
     * 根据身份证号查询客户记录
     * @param idCard 身份证号
     * @return 客户对象
     */
    Customer selectByIdCard(String idCard);
}
