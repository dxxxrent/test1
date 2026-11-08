package com.gxyan.dao;

import com.gxyan.pojo.Notice;
import com.gxyan.vo.NoticeQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeMapper {
    List<Notice> selectList(NoticeQuery query);
    int insert(Notice notice);
    int update(Notice notice);
    int deleteById(Long id);
}
