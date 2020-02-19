package com.lavector.crawlers.tasks.service;


import com.lavector.crawlers.tasks.entity.CrawlerTask;
import com.lavector.crawlers.tasks.entity.DownloaderTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {

    CrawlerTask findByTaskId(String id);

    CrawlerTask crateCrawlerTask(CrawlerTask taskEntity);

    CrawlerTask update(CrawlerTask taskEntity);

    Page<CrawlerTask> findTask(Pageable pageable, String createdUserId, String keyword);

    DownloaderTask saveDownloadTask(DownloaderTask taskEntity);

    DownloaderTask findDownloadTaskByCrawlerTaskId(String crawlerTaskId, String createdUserId);

    DownloaderTask saveOrUpdateDownloadTask(DownloaderTask taskEntity);

}
