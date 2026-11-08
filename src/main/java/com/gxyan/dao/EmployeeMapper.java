package com.gxyan.dao;

import com.gxyan.pojo.Employee;
import com.gxyan.vo.EmployeeQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeMapper {
    /**
     * 根据ID删除员工记录
     * @param id 员工ID
     * @return 删除成功的记录数
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 新增员工记录
     * @param record 员工对象
     * @return 插入成功的记录数
     */
    int insert(Employee record);

    /**
     * 选择性新增员工记录，仅插入非空字段
     * @param record 员工对象
     * @return 插入成功的记录数
     */
    int insertSelective(Employee record);

    /**
     * 根据ID查询员工记录
     * @param id 员工ID
     * @return 员工对象
     */
    Employee selectByPrimaryKey(Integer id);

    /**
     * 根据ID选择性更新员工信息
     * @param record 员工对象
     * @return 更新成功的记录数
     */
    int updateByPrimaryKeySelective(Employee record);

    /**
     * 根据ID更新员工信息
     * @param record 员工对象
     * @return 更新成功的记录数
     */
    int updateByPrimaryKey(Employee record);

    /**
     * 检查用户ID是否存在
     * @param id 员工ID
     * @return 存在返回1，不存在返回0
     */
    int checkUserId(Integer id);

    /**
     * 员工登录验证
     * @param id 员工ID
     * @param password 密码
     * @return 员工对象
     */
    Employee selectLogin(@Param("id") Integer id, @Param("password") String password);

    /**
     * 根据查询条件选择性查询员工列表
     * @param employeeQuery 查询条件
     * @return 员工列表
     */
    List<Employee> selectSelective(EmployeeQuery employeeQuery);

    /**
     * 根据ID查询员工密码
     * @param id 员工ID
     * @return 密码
     */
    String selectPasswordByPrimaryKey(Integer id);

    /**
     * 根据ID和旧密码更新员工密码
     * @param id 员工ID
     * @param oldPass 旧密码
     * @param newPass 新密码
     * @return 更新成功的记录数
     */
    int updatePasswordByPrimaryKeyAndOldPass(@Param("id") Integer id, @Param("oldPass") String oldPass, @Param("newPass") String newPass);
}
