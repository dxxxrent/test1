package com.gxyan.service.impl;

import com.github.pagehelper.PageHelper;
import com.gxyan.common.Const;
import com.gxyan.common.ServerResponse;
import com.gxyan.dao.EmployeeMapper;
import com.gxyan.pojo.Employee;
import com.gxyan.service.IEmployeeService;
import com.gxyan.vo.EmployeeQuery;
import com.gxyan.vo.ListVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 员工相关业务
 */

@Slf4j // 日志记录
@Service // 标记为服务层组件
public class EmployeeServiceImpl implements IEmployeeService {

    @Autowired // 自动注入 EmployeeMapper
    private EmployeeMapper employeeMapper;

    @Override
    public ServerResponse addEmployee(Employee employee) {
        employee.setId(createEmployeeId()); // 创建员工编号
        employee.setRole(Const.Number.ONE); // 设置角色为默认值
        log.info(employee.toString()); // 记录员工信息
        int resultCount = employeeMapper.insert(employee); // 插入员工数据
        if (resultCount > 0) {
            return ServerResponse.createBySuccess(); // 返回成功响应
        }
        return ServerResponse.createByErrorMessage("添加失败"); // 返回错误信息
    }

    @Override
    public ServerResponse updateEmployee(Employee employee) {
        int resultCount = employeeMapper.updateByPrimaryKeySelective(employee); // 更新员工信息
        if (resultCount > 0) {
            return ServerResponse.createBySuccess(); // 返回成功响应
        }
        return ServerResponse.createByErrorMessage("更新失败"); // 返回错误信息
    }

    @Override
    public ServerResponse getList(EmployeeQuery employeeQuery) {
        // 分页查询员工列表
        List<Employee> list = PageHelper.startPage(employeeQuery.getPage(), employeeQuery.getLimit())
                .doSelectPage(() -> employeeMapper.selectSelective(employeeQuery));
        if (list != null) {
            ListVo listVo = new ListVo();
            listVo.setItems(list); // 设置员工项
            listVo.setTotal(PageHelper.count(() -> employeeMapper.selectSelective(employeeQuery))); // 设置总数
            return ServerResponse.createBySuccess(listVo); // 返回成功响应
        }
        return ServerResponse.createByErrorMessage("获取员工列表失败"); // 返回错误信息
    }

    @Override
    public ServerResponse validPassword(Integer id, String validPass) {
        String password = employeeMapper.selectPasswordByPrimaryKey(id); // 获取员工密码
        if (password.equals(validPass)) {
            return ServerResponse.createBySuccess(); // 密码匹配，返回成功
        }
        return ServerResponse.createByError(); // 密码不匹配，返回错误
    }

    @Override
    public ServerResponse updatePassword(Integer id, String oldPass, String newPass) {
        int resultCount = employeeMapper.updatePasswordByPrimaryKeyAndOldPass(id, oldPass, newPass); // 更新密码
        if (resultCount > 0) {
            return ServerResponse.createBySuccess(); // 返回成功响应
        }
        return ServerResponse.createByErrorMessage("更新失败"); // 返回错误信息
    }

    /**
     * 创建员工编号
     * 格式为：yyMM 加 三位递增的数字，数字每月重置为1
     * @return 员工编号
     */
    private Integer createEmployeeId() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMM");
        String format = dateFormat.format(new Date()) + "000"; // 日期格式化
        return Integer.valueOf(format) + (num++); // 返回员工编号
    }

    private int num = 1; // 递增计数器

    @Scheduled(cron="0 0 0 1 * ?") // 每月重置计数器
    private void clearNum() {
        num = 1; // 每月重置
    }
}
