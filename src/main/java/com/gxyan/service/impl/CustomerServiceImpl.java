package com.gxyan.service.impl;

import com.github.pagehelper.PageHelper;
import com.gxyan.common.ServerResponse;
import com.gxyan.dao.CustomerMapper;
import com.gxyan.pojo.Customer;
import com.gxyan.service.ICustomerService;
import com.gxyan.vo.CustomerQuery;
import com.gxyan.vo.ListVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 用户相关业务
 */

@Slf4j // 日志记录
@Service // 标记为服务层组件
public class CustomerServiceImpl implements ICustomerService {

    @Autowired // 自动注入 CustomerMapper
    private CustomerMapper customerMapper;

    @Override
    public ServerResponse addCustomer(Customer customer) {
        customer.setId(createCustomerId()); // 创建客户编号
        int resultCount = customerMapper.insertSelective(customer);
        if (resultCount != 0) {
            return ServerResponse.createBySuccess(); // 返回成功响应
        }
        return ServerResponse.createByErrorMessage("添加失败"); // 返回错误信息
    }

    @Override
    public ServerResponse getList(CustomerQuery customerQuery) {
        prepareQuery(customerQuery);
        // 分页查询客户列表
        List<Customer> list = PageHelper.startPage(customerQuery.getPage(), customerQuery.getLimit()).doSelectPage(() -> customerMapper.selectSelective(customerQuery));
        if (list != null) {
            ListVo listVo = new ListVo();
            listVo.setItems(list); // 设置客户项
            listVo.setTotal(PageHelper.count(() -> customerMapper.selectSelective(customerQuery))); // 设置总数
            return ServerResponse.createBySuccess(listVo); // 返回成功响应
        }
        return ServerResponse.createByErrorMessage("获取客户列表失败"); // 返回错误信息
    }

    @Override
    public ServerResponse<List<Customer>> getExportList(CustomerQuery customerQuery) {
        prepareQuery(customerQuery);
        return ServerResponse.createBySuccess(customerMapper.selectSelective(customerQuery));
    }

    /** 规范化查询参数，尤其是仅允许白名单排序字段进入 MyBatis 的动态 SQL。 */
    private void prepareQuery(CustomerQuery customerQuery) {
        if (customerQuery.getPage() < 1) {
            customerQuery.setPage(1);
        }
        if (customerQuery.getLimit() < 1) {
            customerQuery.setLimit(5);
        } else if (customerQuery.getLimit() > 100) {
            customerQuery.setLimit(100);
        }

        customerQuery.setName(trimToNull(customerQuery.getName()));
        customerQuery.setPhone(trimToNull(customerQuery.getPhone()));
        customerQuery.setIdCard(trimToNull(customerQuery.getIdCard()));

        String[] sort = customerQuery.getOrderBy() == null
                ? new String[0]
                : customerQuery.getOrderBy().trim().split("\\s+");
        String field = sort.length > 0 ? sort[0] : "id";
        String direction = sort.length > 1 ? sort[1].toLowerCase(Locale.ROOT) : "desc";
        if ("createTime".equals(field)) {
            field = "create_time";
        } else if (!"id".equals(field)) {
            field = "id";
        }
        if (!("asc".equals(direction) || "desc".equals(direction))) {
            direction = "desc";
        }
        customerQuery.setOrderBy(field + " " + direction);
    }

    private String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    @Override
    public ServerResponse updateCustomer(Customer customer) {
        // 更新客户信息
        int resultCount = customerMapper.updateByPrimaryKey(customer);
        if (resultCount != 0) {
            return ServerResponse.createBySuccess(); // 返回成功响应
        }
        log.error(customer.toString()); // 记录错误日志
        return ServerResponse.createByErrorMessage("更新失败"); // 返回错误信息
    }

    /**
     * 创建客户编号
     * 格式为：yyMMdd 加 五位递增的数字，数字每天重置为1
     * @return 客户编号
     */
    private Long createCustomerId() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");
        String format = dateFormat.format(new Date()) + "10000"; // 日期格式化
        return Long.valueOf(format) + (num++); // 返回客户编号
    }

    private int num = 1; // 递增计数器

    @Scheduled(cron="0 0 0 * * ?") // 定时任务，重置计数器
    private void clearNum() {
        num = 1; // 每天重置
    }
}
