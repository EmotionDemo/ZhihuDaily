package com.example.amicoli.zhihudemo.utils;

import com.example.amicoli.zhihudemo.bean.StoriesContentBean;
/**
 * Created by diff on 2016/2/4.
 */
public class HtmlUtils {
    public static String structHtml(StoriesContentBean storiesContentBean) {
        StringBuilder sb = new StringBuilder();
        sb.append("<div class=\"img-wrap\">")
                .append("<h1 class=\"headline-title\">")
                .append(storiesContentBean.getTitle()).append("</h1>")
                .append("<span class=\"img-source\">")
                .append(storiesContentBean.getImage_source()).append("</span>")
                .append("<img src=\"").append(storiesContentBean.getImage())
                .append("\" alt=\"\">")
                .append("<div class=\"img-mask\"></div>");
        //news_content_style.css和news_header_style.css都是在assets里的
        String mNewsContent = "<link rel=\"stylesheet\" type=\"text/css\" href=\"news_content_style.css\"/>"
                + "<link rel=\"stylesheet\" type=\"text/css\" href=\"news_header_style.css\"/>"
                + storiesContentBean.getBody().replace("<div class=\"img-place-holder\">", sb.toString());
        return mNewsContent;
    }
}
