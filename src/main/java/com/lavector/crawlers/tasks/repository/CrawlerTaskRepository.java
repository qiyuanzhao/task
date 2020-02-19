package com.lavector.crawlers.tasks.repository;


import com.lavector.crawlers.tasks.entity.CrawlerTask;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface CrawlerTaskRepository extends JpaRepository<CrawlerTask, String> {

//    @Query("select t from CrawlerTask t where t.id = ?1")
//    Optional<CrawlerTask> findCrawlerTaskById(String id);

    @Query(value = "select * from crawler_task where status <> 'DELETED' and created_user_id = ?1", nativeQuery = true)
    Page<CrawlerTask> findAll(Pageable var1, String createdUserId);

    @Query(value = "select * from crawler_task where status <> 'DELETED' and created_user_id = ?1 and name like ?2 ", nativeQuery = true)
    Page<CrawlerTask> findByKeyword(Pageable var1, String createdUserId, String keyword);

}
