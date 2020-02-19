package com.lavector.crawlers.tasks;

import com.alibaba.fastjson.JSON;
import com.lavector.crawlers.tasks.entity.CrawlerTask;
import com.lavector.crawlers.tasks.service.TaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TaskApplication.class)
public class TaskTest {

    @Autowired
    private TaskService taskService;


    /**
     * 查询一个
     */
    @Test
    public void findByTaskId() {
        CrawlerTask task = taskService.findByTaskId("510cb93e9ee94f998357068fa0799af8");
        System.out.println(task.toString());
    }

    /**
     * 查询所有
     */
    @Test
    public void findTask() {
        Page<CrawlerTask> task = taskService.findTask(PageRequest.of(0, 10),"10","");

        task.getContent().forEach(System.out::println);

//        task.stream().map(Task::toString).forEach(System.out::println);
    }

    /**
     * 保存
     */
    @Test
    public void save() {

        String str = "{\"id\":\"510cb93e9ee94f998357068fa0799af8\",\"name\":\"zhangsan\",\"createTime\":\"2018-08-27\",\"beginTime\":\"2018-08-20\",\"endTime\":\"2019-08-22\",\"keywords\":[\"红腰子\"],\"excludeKeywords\":[\"哈哈\"],\"createdUserId\":\"10\",\"updatedTime\":\"2017-08-23\",\"site\":[\"weibo\",\"redbook\"]}";

        CrawlerTask task = JSON.parseObject(str, CrawlerTask.class);

        CrawlerTask save = taskService.crateCrawlerTask(task);

        System.out.println(save.toString());

    }



}
