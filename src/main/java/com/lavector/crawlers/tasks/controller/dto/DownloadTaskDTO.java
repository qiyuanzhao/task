package com.lavector.crawlers.tasks.controller.dto;


import com.lavector.crawlers.tasks.entity.DownloadType;
import com.lavector.crawlers.tasks.entity.DownloaderTask;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class DownloadTaskDTO {



    private String id;

    @NotNull
    private String crawlerTaskId;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createdTime;

    @Enumerated(EnumType.STRING)
    private DownloadType downloadType;

    private String createdUserId;

    private String name;


    public DownloadTaskDTO() {

    }

    public DownloadTaskDTO(DownloaderTask downloaderTask) {
        this.id = downloaderTask.getId();
        this.crawlerTaskId = downloaderTask.getCrawlerTaskId();
        this.createdTime = downloaderTask.getCreatedTime();
        this.downloadType = downloaderTask.getDownloadType();
        this.createdUserId = downloaderTask.getCreatedUserId();
        this.name = downloaderTask.getName();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCrawlerTaskId() {
        return crawlerTaskId;
    }

    public void setCrawlerTaskId(String crawlerTaskId) {
        this.crawlerTaskId = crawlerTaskId;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public DownloadType getDownloadType() {
        return downloadType;
    }

    public void setDownloadType(DownloadType downloadType) {
        this.downloadType = downloadType;
    }

    public String getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(String createdUserId) {
        this.createdUserId = createdUserId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void applyTo(DownloaderTask downloaderTask) {
        downloaderTask.setCrawlerTaskId(this.crawlerTaskId);
        downloaderTask.setDownloadType(this.downloadType);
        downloaderTask.setCreatedTime(this.createdTime);
        downloaderTask.setCreatedUserId(this.createdUserId);
        downloaderTask.setId(this.id);
        downloaderTask.setName(this.name);
    }


}
