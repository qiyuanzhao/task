package com.lavector.crawlers.tasks.configuration;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * @author Yuanzhao Qi
 */
public enum Site {
    /**
     *
     */
    WEIBO("微博", "weibo"),
    TMALL("天猫", "tmall"),
    XIAOHONGSHU("小红书", "xiaohongshu"),
    BILIBILI("B站", "bilibili"),
    JD("京东", "jd"),
    DIANPING("大众点评", "dianping"),
    TAOBAO("淘宝","taobao");

    private String name;
    private String code;


    public String getName() {
        return this.name;
    }

    public String getCode() {
        return this.code;
    }

    public static Site findByCode(String code) {
        for (Site site : Site.values()) {
            if (site.getCode().equals(code)) {
                return site;
            }
        }
        return null;
    }

    @JsonCreator
    Site(String name, String code) {
        this.name = name;
        this.code = code;
    }

}
