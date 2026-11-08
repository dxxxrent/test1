package com.gxyan.controller;

import com.gxyan.common.Const;
import com.gxyan.common.ServerResponse;
import com.gxyan.pojo.Employee;
import com.gxyan.service.IEmployeeService;
import com.gxyan.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Slf4j // 日志记录
@RestController // 标记为控制器
@RequestMapping("user") // 路径前缀
public class UserController {

    @Autowired
    private IUserService userService; // 用户服务接口
    @Autowired
    private IEmployeeService employeeService; // 员工服务接口

    // 登录接口
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ServerResponse login(String employeeId, String password, HttpSession session) {
        ServerResponse response = userService.login(Integer.valueOf(employeeId), password); // 调用登录服务
        if (response.isSuccess()) {
            session.setAttribute(Const.CURRENT_USER, response.getData()); // 设置当前用户到会话

            Map<String, String> map = new HashMap<>(1);
            map.put("token", session.getId()); // 返回会话ID作为token
            response = ServerResponse.createBySuccess(map);
        }
        log.info("userId:{}, password:{}, data:{}", employeeId, password, response.getData()); // 记录登录信息
        return response; // 返回响应
    }

    // 登出接口
    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public ServerResponse logout(HttpSession session) {
        session.removeAttribute(Const.CURRENT_USER); // 移除当前用户
        return ServerResponse.createBySuccess(); // 返回成功响应
    }

    // 获取用户信息接口
    @RequestMapping(value = "info", method = RequestMethod.POST)
    public ServerResponse<Employee> info(HttpSession session) {
        Employee employee = (Employee) session.getAttribute(Const.CURRENT_USER); // 从会话获取用户信息
        if (employee == null) {
            return ServerResponse.createByErrorMessage("用户未登录，无法获取当前用户信息"); // 用户未登录
        }
        return ServerResponse.createBySuccess(employee); // 返回用户信息
    }

    // 更新用户信息接口
    @RequestMapping(value = "updateMessage", method = RequestMethod.POST)
    public ServerResponse updateMessage(Employee employee) {
        return employeeService.updateEmployee(employee); // 更新员工信息
    }

    // 验证密码接口
    @RequestMapping(value = "validPassword", method = RequestMethod.POST)
    public ServerResponse validPassword(HttpSession session, String validPass) {
        Employee employee = (Employee) session.getAttribute(Const.CURRENT_USER); // 从会话获取用户
        return employeeService.validPassword(employee.getId(), validPass); // 验证密码
    }

    // 更新密码接口
    @RequestMapping(value = "updatePassword", method = RequestMethod.POST)
    public ServerResponse updatePassword(HttpSession session, String oldPass, String newPass) {
        Employee employee = (Employee) session.getAttribute(Const.CURRENT_USER); // 从会话获取用户
        return employeeService.updatePassword(employee.getId(), oldPass, newPass); // 更新密码
    }
}
