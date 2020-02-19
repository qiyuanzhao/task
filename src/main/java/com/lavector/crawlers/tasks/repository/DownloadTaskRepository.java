package com.lavector.crawlers.tasks.repository;


import com.lavector.crawlers.tasks.entity.DownloaderTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DownloadTaskRepository extends JpaRepository<DownloaderTask, String> {

    @Query(value = "select * from download_task where crawler_task_id = ?1 and created_user_id = ?2", nativeQuery = true)
    DownloaderTask findDownloadTaskByCrawlerTaskId(String crawlerTaskId, String createdUserId);

}
