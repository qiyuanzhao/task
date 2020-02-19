package com.lavector.crawlers.tasks.controller.dto;


import com.lavector.crawlers.tasks.entity.CrawlerTask;
import com.lavector.crawlers.tasks.entity.Status;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class CrawlerTaskDTO {

    private String id;

    @NotBlank
    private String name;

    @NotNull
    private Date beginTime;

    private Date endTime;

    @NotNull
    private String[] keywords;

    private String[] excludeKeywords;

    @NotNull
    private String site;

    @Enumerated(EnumType.STRING)
    private Status status;

    @NotBlank
    private String createdUserId;

    private Date createdTime;


    public CrawlerTaskDTO() {
    }

    public CrawlerTaskDTO(CrawlerTask task) {
        this.id = task.getId();
        this.beginTime = task.getBeginTime();
        this.excludeKeywords = task.getExcludeKeywords();
        this.endTime = task.getEndTime();
        this.name = task.getName();
        this.keywords = task.getKeywords();
        this.status = task.getStatus();
        this.keywords = task.getKeywords();
        this.createdUserId = task.getCreatedUserId();
        this.site = StringUtils.join(task.getSite(), ",");
        this.createdTime = task.getCreatedTime();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String[] getKeywords() {
        return keywords;
    }

    public void setKeywords(String[] keywords) {
        this.keywords = keywords;
    }

    public String[] getExcludeKeywords() {
        return excludeKeywords;
    }

    public void setExcludeKeywords(String[] excludeKeywords) {
        this.excludeKeywords = excludeKeywords;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(String createdUserId) {
        this.createdUserId = createdUserId;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public void applyTo(CrawlerTask taskEntity) {
        taskEntity.setName(this.getName());
        taskEntity.setBeginTime(this.getBeginTime());
        taskEntity.setId(this.getId());
        taskEntity.setExcludeKeywords(this.getExcludeKeywords());
        taskEntity.setKeywords(this.getKeywords());
        taskEntity.setEndTime(this.getEndTime());
        taskEntity.setCreatedUserId(this.getCreatedUserId());
        taskEntity.setSite(this.getSite());
    }
}
