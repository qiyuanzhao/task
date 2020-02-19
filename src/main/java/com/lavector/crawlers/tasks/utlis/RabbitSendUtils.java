package com.lavector.crawlers.tasks.utlis;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lavector.crawlers.tasks.entity.CrawlerTask;
import com.lavector.crawlers.tasks.entity.DownloaderTask;
import com.lavector.crawlers.tasks.event.EventType;
import com.lavector.crawlers.tasks.event.TaskEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitSendUtils {

    private Logger logger = LoggerFactory.getLogger(RabbitSendUtils.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(String queues, Object obj, Integer totalPageCount, EventType eventType) {
        String taskId = "";
        String taskName = "";
        String userId = "";

        if (obj instanceof DownloaderTask) {
            DownloaderTask downloaderTask = (DownloaderTask) obj;
            taskId = downloaderTask.getCrawlerTaskId();
            userId = downloaderTask.getCreatedUserId();
            taskName = downloaderTask.getName();
        }
        if (obj instanceof CrawlerTask){
            CrawlerTask crawlerTask = (CrawlerTask) obj;
            taskId = crawlerTask.getId();
            userId = crawlerTask.getCreatedUserId();
            taskName = crawlerTask.getName();
        }

        Long eventTime = System.currentTimeMillis();
        String status = eventType.toString();
        TaskEvent taskEvent = new TaskEvent(taskId, taskName, userId, status, eventTime, totalPageCount);


        String taskEventJson = JSONObject.toJSONString(taskEvent);
        logger.info("发送的队列：{} ， 消息内容：{}", queues, taskEventJson);
//        System.out.println(taskEventJson);
        rabbitTemplate.convertAndSend(queues, taskEventJson);
    }


}
