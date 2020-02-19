package com.lavector.crawlers.tasks.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;


@Entity(name = "download_task")
public class DownloaderTask extends Task {


    @Enumerated(EnumType.STRING)
    private DownloadType downloadType = DownloadType.NOTDOWNLOAD;

    private String crawlerTaskId;

    public String getCrawlerTaskId() {
        return crawlerTaskId;
    }

    public void setCrawlerTaskId(String crawlerTaskId) {
        this.crawlerTaskId = crawlerTaskId;
    }

    public DownloadType getDownloadType() {
        return downloadType;
    }

    public void setDownloadType(DownloadType downloadType) {
        this.downloadType = downloadType;
    }


}
