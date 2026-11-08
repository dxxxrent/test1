package com.gxyan.service.impl;

import com.github.pagehelper.PageHelper;
import com.gxyan.common.ServerResponse;
import com.gxyan.dao.NoticeMapper;
import com.gxyan.pojo.Notice;
import com.gxyan.service.INoticeService;
import com.gxyan.vo.ListVo;
import com.gxyan.vo.NoticeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServiceImpl implements INoticeService {
    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public ServerResponse getList(NoticeQuery query) {
        query.setPage(Math.max(1, query.getPage()));
        query.setLimit(query.getLimit() < 1 ? 10 : Math.min(query.getLimit(), 100));
        if (query.getTitle() != null) {
            query.setTitle(query.getTitle().trim());
        }
        List<Notice> notices = PageHelper.startPage(query.getPage(), query.getLimit())
                .doSelectPage(() -> noticeMapper.selectList(query));
        ListVo result = new ListVo();
        result.setItems(notices);
        result.setTotal(PageHelper.count(() -> noticeMapper.selectList(query)));
        return ServerResponse.createBySuccess(result);
    }

    @Override
    public ServerResponse create(Notice notice) {
        String validationError = validate(notice);
        if (validationError != null) {
            return ServerResponse.createByErrorMessage(validationError);
        }
        if (notice.getStatus() == null) {
            notice.setStatus(0);
        }
        return noticeMapper.insert(notice) == 1
                ? ServerResponse.createBySuccessMessage("公告已创建")
                : ServerResponse.createByErrorMessage("创建公告失败");
    }

    @Override
    public ServerResponse update(Notice notice) {
        if (notice.getId() == null) {
            return ServerResponse.createByErrorMessage("缺少公告编号");
        }
        String validationError = validate(notice);
        if (validationError != null) {
            return ServerResponse.createByErrorMessage(validationError);
        }
        return noticeMapper.update(notice) == 1
                ? ServerResponse.createBySuccessMessage("公告已更新")
                : ServerResponse.createByErrorMessage("公告不存在或未发生变化");
    }

    @Override
    public ServerResponse delete(Long id) {
        if (id == null) {
            return ServerResponse.createByErrorMessage("缺少公告编号");
        }
        return noticeMapper.deleteById(id) == 1
                ? ServerResponse.createBySuccessMessage("公告已删除")
                : ServerResponse.createByErrorMessage("公告不存在");
    }

    private String validate(Notice notice) {
        if (notice.getTitle() == null || notice.getTitle().trim().isEmpty()) {
            return "请输入公告标题";
        }
        if (notice.getTitle().trim().length() > 100) {
            return "公告标题不能超过100个字符";
        }
        if (notice.getContent() == null || notice.getContent().trim().isEmpty()) {
            return "请输入公告内容";
        }
        if (notice.getStatus() != null && notice.getStatus() != 0 && notice.getStatus() != 1) {
            return "公告状态无效";
        }
        return null;
    }
}
