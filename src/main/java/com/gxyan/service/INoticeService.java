package com.gxyan.service;

import com.gxyan.common.ServerResponse;
import com.gxyan.pojo.Notice;
import com.gxyan.vo.NoticeQuery;

public interface INoticeService {
    ServerResponse getList(NoticeQuery query);
    ServerResponse create(Notice notice);
    ServerResponse update(Notice notice);
    ServerResponse delete(Long id);
}
