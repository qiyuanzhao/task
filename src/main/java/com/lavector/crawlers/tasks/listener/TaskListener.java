package com.lavector.crawlers.tasks.listener;

import com.alibaba.fastjson.JSONObject;
import com.lavector.crawlers.tasks.configuration.RabbitConfig;
import com.lavector.crawlers.tasks.entity.DownloadType;
import com.lavector.crawlers.tasks.entity.DownloaderTask;
import com.lavector.crawlers.tasks.event.TaskEvent;
import com.lavector.crawlers.tasks.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RabbitListener(queues = RabbitConfig.DOWNLOADCOMPLETE)
public class TaskListener {

    private Logger logger = LoggerFactory.getLogger(TaskListener.class);

    private TaskService taskService;

    public TaskListener(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * 修改下载task
     *
     * @param
     * @return
     */
    @RabbitHandler
    private void updateDownloaderTask(String jsonString) {
        logger.info("获取的消息：{}", jsonString);
        TaskEvent taskEvent = JSONObject.parseObject(jsonString, TaskEvent.class);
        String taskId = taskEvent.getTaskId();
        String status = taskEvent.getStatus();
        DownloadType downloadType = Enum.valueOf(DownloadType.class, status);
        DownloaderTask downloaderTask = taskService.findDownloadTaskByCrawlerTaskId(taskId,taskEvent.getUserId());
        if (downloaderTask != null) {
            downloaderTask.setUpdatedTime(new Date());
            downloaderTask.setDownloadType(downloadType);
            taskService.saveOrUpdateDownloadTask(downloaderTask);
        }

    }


}
