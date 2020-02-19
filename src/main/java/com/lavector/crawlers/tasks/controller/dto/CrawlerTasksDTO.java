package com.lavector.crawlers.tasks.controller.dto;


import java.util.List;

public class CrawlerTasksDTO {

    private List<CrawlerTaskDTO> data;

    private Integer totalPages;

    private Long totalElements;

    private String site;

    public List<CrawlerTaskDTO> getData() {
        return data;
    }

    public void setData(List<CrawlerTaskDTO> data) {
        this.data = data;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public CrawlerTasksDTO(List<CrawlerTaskDTO> data, Integer totalPages, Long totalElements) {
        this.data = data;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }
}
