package com.lavector.crawlers.tasks.controller;


import com.alibaba.fastjson.JSONObject;
import com.lavector.crawlers.tasks.utlis.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@CrossOrigin
@RestController
@RequestMapping("/messages")
public class MessageController {

    private Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${messages.url}")
    private String messagesUrl;

    @Value("${downloadFilePath}")
    private String downloadFilePath;

    /**
     * 获取messages
     *
     * @param numToLoad
     * @param pageNum
     * @param taskId
     * @return
     */
    @GetMapping
    public ResponseEntity loadMessage(@RequestParam(required = false, value = "numToLoad", defaultValue = "10") int numToLoad,
                                      @RequestParam(required = false, value = "pageNum", defaultValue = "0") int pageNum,
                                      @RequestParam(required = true, value = "taskId") String taskId) {
        String requestMessageUrl = messagesUrl + "?taskId={1}&numToLoad={2}&pageNum={3}";
        logger.info("请求的message链接:{}", requestMessageUrl);
        ResponseEntity<String> forEntity = restTemplate.getForEntity(requestMessageUrl, String.class, taskId, numToLoad, pageNum);
        logger.info("返回值：{}", forEntity.getBody());
        JSONObject jsonObject = JSONObject.parseObject(forEntity.getBody());

        return ResponseUtil.buildSuccess(jsonObject, forEntity.getStatusCode());
    }


    /**
     * 下载文件
     *
     * @param response
     * @return
     */
    @GetMapping("/download/{taskId}")
    public ResponseEntity downLoadMessage(HttpServletResponse response, @PathVariable(required = true, value = "taskId") String taskId) {
        //被下载文件的名称
        String fileName = taskId + ".xlsx";

        File file = new File(downloadFilePath + fileName);
        if (file.exists()) {
            // 设置强制下载不打开
            response.setContentType("application/force-download");
            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream outputStream = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    outputStream.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
                return ResponseUtil.buildSuccess("下载成功");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return ResponseUtil.buildSuccess("下载失败");
    }


}
