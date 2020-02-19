package com.lavector.crawlers.tasks.controller;

import com.lavector.crawlers.tasks.controller.dto.CrawlerTaskDTO;
import com.lavector.crawlers.tasks.controller.dto.CrawlerTasksDTO;
import com.lavector.crawlers.tasks.controller.dto.DownloadTaskDTO;
import com.lavector.crawlers.tasks.entity.CrawlerTask;
import com.lavector.crawlers.tasks.entity.DownloadType;
import com.lavector.crawlers.tasks.entity.DownloaderTask;
import com.lavector.crawlers.tasks.entity.Status;
import com.lavector.crawlers.tasks.service.TaskService;
import com.lavector.crawlers.tasks.utlis.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@CrossOrigin
@RestController
@RequestMapping("/tasks")
public class TaskController {


    @Autowired
    private MessageController messageDownload;

    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * 根据用户ID查询所有
     *
     * @param numToLoad
     * @param pageNum
     * @return
     */
    @GetMapping
    public ResponseEntity findTask(@RequestParam(required = false, value = "numToLoad", defaultValue = "10") int numToLoad,
                                   @RequestParam(required = false, value = "pageNum", defaultValue = "0") int pageNum,
                                   @RequestParam(required = true, value = "userId") String userId,
                                   @RequestParam(required = false, value = "keyword") String keyword) {
        Pageable pageable = PageRequest.of(pageNum, numToLoad);
        Page<CrawlerTask> taskPage = taskService.findTask(pageable, userId, keyword);
        List<CrawlerTaskDTO> collect = taskPage.getContent().stream().map(CrawlerTaskDTO::new).collect(Collectors.toList());
        CrawlerTasksDTO tasksDTO = new CrawlerTasksDTO(collect, taskPage.getTotalPages(), taskPage.getTotalElements());
        return ResponseUtil.buildSuccess(tasksDTO);
//        return ResponseUtil.buildSuccess();
    }

    /**
     * 创建爬取任务
     *
     * @param taskEntity
     * @return
     */
    @PostMapping("/crawler")
    public ResponseEntity createCrawlerTask(@Valid @RequestBody CrawlerTaskDTO taskEntity) {
        CrawlerTask task = new CrawlerTask();
        taskEntity.applyTo(task);
        task.setCreatedTime(new Date());
        task.setUpdatedTime(new Date());
        task.setStatus(Status.CREATED);

        task = taskService.crateCrawlerTask(task);
        if (task != null) {
            return ResponseUtil.buildSuccess(new CrawlerTaskDTO(task));
        }
        return ResponseUtil.buildError(new CrawlerTaskDTO(), HttpStatus.BAD_REQUEST);
    }


    /**
     * 创建下载任务
     *
     * @param response
     * @param downloadTaskDTO
     * @return
     */
    @PostMapping("/downloader")
    public ResponseEntity createDownloadTask(HttpServletResponse response, @Valid @RequestBody DownloadTaskDTO downloadTaskDTO) {
        DownloaderTask downloadTask = taskService.findDownloadTaskByCrawlerTaskId(downloadTaskDTO.getCrawlerTaskId(), downloadTaskDTO.getCreatedUserId());
        if (downloadTask != null) {
            if (DownloadType.DOWNLOADING.toString().equalsIgnoreCase(downloadTask.getDownloadType().toString())) {
                return ResponseUtil.buildSuccess(new DownloadTaskDTO(downloadTask), HttpStatus.BAD_REQUEST);
            }
            if (DownloadType.DOWNLOADSUCCESSFUL.toString().equalsIgnoreCase(downloadTask.getDownloadType().toString())) {
                return messageDownload.downLoadMessage(response, downloadTaskDTO.getId());
            }
        }
        DownloaderTask task = new DownloaderTask();
        downloadTaskDTO.applyTo(task);
        task.setCreatedTime(new Date());
        task.setUpdatedTime(new Date());
        task = taskService.saveDownloadTask(task);
        if (task != null) {
            return ResponseUtil.buildSuccess(new DownloadTaskDTO(task), HttpStatus.CREATED);
        }
        return ResponseUtil.buildError(new DownloadTaskDTO(), HttpStatus.BAD_REQUEST);
    }



    /**
     * 修改数据
     *
     * @param taskEntity
     * @return
     */
    @PutMapping
    public ResponseEntity updateTask(@Valid @RequestBody CrawlerTaskDTO taskEntity) {
        if (taskEntity.getId() == null) {
            return ResponseUtil.buildError();
        }
        CrawlerTask entity = taskService.findByTaskId(taskEntity.getId());
        if (entity != null) {
            taskEntity.applyTo(entity);
            entity.setUpdatedTime(new Date());
            return ResponseUtil.buildSuccess(new CrawlerTaskDTO(taskService.update(entity)));
        }
        return ResponseUtil.buildError(taskEntity);
    }


    /**
     * 删除 修改状态
     *
     * @param id
     */
    @DeleteMapping
    public ResponseEntity updateTaskStatus(@RequestParam(required = true, value = "id") String id) {
        if (StringUtils.isEmpty(id)) {
            return ResponseUtil.buildError();
        }
        CrawlerTask byTaskId = taskService.findByTaskId(id);
        byTaskId.setStatus(Status.DELETED);
        byTaskId.setUpdatedTime(new Date());
        CrawlerTask save = taskService.update(byTaskId);
        if (save != null) {
            return ResponseUtil.buildSuccess(new CrawlerTaskDTO(save));
        }
        return ResponseUtil.buildError(new CrawlerTaskDTO(), HttpStatus.BAD_REQUEST);
    }


}
