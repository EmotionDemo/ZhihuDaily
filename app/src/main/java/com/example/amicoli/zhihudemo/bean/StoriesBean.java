package com.example.amicoli.zhihudemo.bean;

import com.example.amicoli.zhihudemo.interfaces.DisplaybleItem;

import java.util.List;

/**
 * Created by Administrator on 2017/2/9.
 */

public class StoriesBean implements DisplaybleItem{
    /**
     * title : 中国古代家具发展到今天有两个高峰，一个两宋一个明末（多图）
     * ga_prefix : 052321
     * images : ["http://p1.zhimg.com/45/b9/45b9f057fc1957ed2c946814342c0f02.jpg"]
     * type : 0
     * id : 3930445
     */
    private String title;
    private String ga_prefix;
    private int type;
    private int id;
    private List<String> images;

    public StoriesBean(String title, String ga_prefix, int type, int id, List<String> images) {
        this.title = title;
        this.ga_prefix = ga_prefix;
        this.type = type;
        this.id = id;
        this.images = images;
    }

    public StoriesBean() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String>getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
