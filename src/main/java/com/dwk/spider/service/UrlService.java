package com.dwk.spider.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dwk.spider.util.HttpUtils;
import com.dwk.spider.util.HttpUtils.Method;

public class UrlService {
  
  public void fetch() throws Exception {
    String content = HttpUtils.call(Method.get, "http://e.games.sina.com.cn/t/news_list/ps4/");
    // System.out.println(content);
    content = content.replaceAll("\\r\\n", ""); // 一行
    Pattern p = Pattern.compile("<ul class=\"hp_newslist\">(.+?)</ul>"); // 1.定位到列表
    Pattern p2 = Pattern.compile("<li>.*?<a href=\"([^\"]*)\".*?</a>.*?</li>"); // 2.获得列表元素中的文章url
    Matcher m = p.matcher(content);
    if (m.find()) {
      String g = m.group(1);
      System.out.println(g);
      Matcher m2 = p2.matcher(g);
      while (m2.find()) {
        String url = m2.group(1);
        System.out.println(url);
      }
    }
  }
}
