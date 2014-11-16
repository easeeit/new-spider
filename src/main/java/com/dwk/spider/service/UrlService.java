package com.dwk.spider.service;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;

import com.dwk.spider.util.HttpUtils;
import com.dwk.spider.util.HttpUtils.Method;

public class UrlService {
 
  private static final String KEY_URL = "url";
  private static final String KEY_REGEX_LIST = "regexList";
  private static final String KEY_REGEX_URL = "regexUrl";
  
  private List<Map<String, String>> config;
  
 
  
  public void fetch() throws Exception {
    if (!CollectionUtils.isEmpty(config)) {
      for (Map<String,String> conf : config) {
        
        String content = HttpUtils.call(Method.get, conf.get(KEY_URL));
        // System.out.println(content);
        content = content.replaceAll("\\r\\n", ""); // 一行
        Pattern p = Pattern.compile(conf.get(KEY_REGEX_LIST)); // 1.定位到列表
        Pattern p2 = Pattern.compile(conf.get(KEY_REGEX_URL)); // 2.获得列表元素中的文章url
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
  }

  public void setConfig(List<Map<String, String>> config) {
    this.config = config;
  }
  
  
}
