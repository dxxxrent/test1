package com.gxyan.controller;

import com.gxyan.common.ServerResponse;
import com.gxyan.pojo.Notice;
import com.gxyan.service.INoticeService;
import com.gxyan.vo.NoticeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("notice")
public class NoticeController {
    @Autowired
    private INoticeService noticeService;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ServerResponse getList(NoticeQuery query) {
        return noticeService.getList(query);
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public ServerResponse create(Notice notice) {
        return noticeService.create(notice);
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public ServerResponse update(Notice notice) {
        return noticeService.update(notice);
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public ServerResponse delete(Long id) {
        return noticeService.delete(id);
    }
}
