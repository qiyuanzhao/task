package com.lavector.crawlers.tasks.service.impl;


import com.lavector.crawlers.tasks.configuration.RabbitConfig;
import com.lavector.crawlers.tasks.entity.CrawlerTask;
import com.lavector.crawlers.tasks.entity.DownloadType;
import com.lavector.crawlers.tasks.entity.DownloaderTask;
import com.lavector.crawlers.tasks.event.EventType;
import com.lavector.crawlers.tasks.repository.CrawlerTaskRepository;
import com.lavector.crawlers.tasks.repository.DownloadTaskRepository;
import com.lavector.crawlers.tasks.service.TaskService;
import com.lavector.crawlers.tasks.utlis.RabbitSendUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional
class TaskServiceImpl implements TaskService {

    private Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);


    private CrawlerTaskRepository crawlerTaskRepository;

    private DownloadTaskRepository downloadTaskRepository;

    @Autowired
    private RabbitSendUtils rabbitSendUtils;

    public TaskServiceImpl(CrawlerTaskRepository taskRepository, DownloadTaskRepository downloadTaskRepository) {
        this.crawlerTaskRepository = taskRepository;
        this.downloadTaskRepository = downloadTaskRepository;
    }


    @Override
    public Page<CrawlerTask> findTask(Pageable pageable, String createdUserId, String keyword) {
        Page<CrawlerTask> all;
        if (StringUtils.isEmpty(keyword)) {
            all = crawlerTaskRepository.findAll(pageable, createdUserId);
        } else {
            all = crawlerTaskRepository.findByKeyword(pageable, createdUserId, "%" + keyword + "%");
        }
        return all;
    }

    @Override
    public CrawlerTask findByTaskId(String id) {
        return crawlerTaskRepository.findById(id).orElse(null);
    }

    @Override
    public CrawlerTask crateCrawlerTask(CrawlerTask taskEntity) {
        CrawlerTask save = crawlerTaskRepository.save(taskEntity);
        if (StringUtils.isEmpty(save.getId())) {
            return null;
        }
        logger.info("创建任务成功");
        rabbitSendUtils.sendMessage(RabbitConfig.WEIBOCRAWLER, save, null, EventType.CREATED);
        logger.info("发送任务成功");
        return save;
    }


    @Override
    public CrawlerTask update(CrawlerTask taskEntity) {
        CrawlerTask save = crawlerTaskRepository.save(taskEntity);
        if (StringUtils.isEmpty(save.getId())) {
            return null;
        }
        return save;
    }

    @Override
    public DownloaderTask saveDownloadTask(DownloaderTask taskEntity) {
        DownloaderTask save = saveOrUpdateDownloadTask(taskEntity);

        logger.info("创建任务成功");
        rabbitSendUtils.sendMessage(RabbitConfig.MESSAGEDOWNLOAD, save, null, EventType.CREATED);
        logger.info("发送任务成功");

        save.setDownloadType(DownloadType.DOWNLOADING);

        //更新下载状态
        saveOrUpdateDownloadTask(save);

        return save;
    }

    @Override
    public DownloaderTask saveOrUpdateDownloadTask(DownloaderTask taskEntity) {
        DownloaderTask downloadTask = downloadTaskRepository.findDownloadTaskByCrawlerTaskId(taskEntity.getCrawlerTaskId(), taskEntity.getCreatedUserId());
        if (downloadTask != null) {
            downloadTask.setDownloadType(taskEntity.getDownloadType());
            taskEntity.setId(downloadTask.getId());
        }
        return downloadTaskRepository.save(taskEntity);
    }


    @Override
    public DownloaderTask findDownloadTaskByCrawlerTaskId(String crawlerTaskId, String createdUserId) {
        DownloaderTask downloaderTask = downloadTaskRepository.findDownloadTaskByCrawlerTaskId(crawlerTaskId, createdUserId);
        return downloaderTask;
    }


}
