package com.gxyan.pojo;

import java.util.Date;

public class Notice {
    private Long id;
    private String title;
    private String content;
    private Integer status;
    private Date createTime;
    private Date updateTime;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title == null ? null : title.trim(); }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content == null ? null : content.trim(); }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    public Date getCreateTime() { return createTime; }
    public void setCreateTime(Date createTime) { this.createTime = createTime; }
    public Date getUpdateTime() { return updateTime; }
    public void setUpdateTime(Date updateTime) { this.updateTime = updateTime; }
}
