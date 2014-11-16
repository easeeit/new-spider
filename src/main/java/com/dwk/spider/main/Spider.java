package com.dwk.spider.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dwk.spider.service.UrlService;

public class Spider {

  public static void main(String[] x) throws Exception {
    ApplicationContext context= new ClassPathXmlApplicationContext("/spring/spring.xml");
    UrlService us = (UrlService)context.getBean("urlService");
    us.fetch();
  }
//  public static void main(String[] args) throws Exception {
//    String content = HttpUtils.call(Method.get, "http://ps4.tgbus.com/news/");
////     System.out.println(content);
//    content = content.replaceAll("\\r\\n", ""); // 一行
//    Pattern p = Pattern.compile("<div class=\"list\"><ul>(.+?)</ul></div>"); // 1.定位到列表
//    Pattern p2 = Pattern.compile("<li><cite>.*?</cite>.*?<a href=\"([^\"]*)\".*?</a>.*?</li>"); // 2.获得列表元素中的文章url
//    Matcher m = p.matcher(content);
//    if (m.find()) {
//      String g = m.group(1);
//      System.out.println(g);
//      Matcher m2 = p2.matcher(g);
//      while(m2.find()) {
//        String url = m2.group(1);
//        System.out.println(url);
//      }
//    }
//  }
//  
//  public static void main(String[] args) throws Exception {
//    String content = HttpUtils.call(Method.get, "http://www.gamehome.tv/Article/xbox360/news/");
////     System.out.println(content);
//    content = content.replaceAll("\\r\\n", ""); // 一行
//    Pattern p = Pattern.compile("<div class=\"list_loop\">(.+)</div>"); // 1.定位到列表
//    Pattern p2 = Pattern.compile("<div class=\"list_title\"><a href=\"([^\"]*)\".*?</a></div>"); // 2.获得列表元素中的文章url
//    Matcher m = p.matcher(content);
//    if (m.find()) {
//      String g = m.group(1);
//      System.out.println(g);
//      Matcher m2 = p2.matcher(g);
//      while(m2.find()) {
//        String url = m2.group(1);
//        System.out.println(url);
//      }
//    }
//  }
  
//  public static void main(String[] args) throws Exception {
//    String urlPrefix = "http://tv.duowan.com";
//    String content = HttpUtils.call(Method.get, "http://tv.duowan.com/tag/225381878701.html");
////     System.out.println(content);
//    content = content.replaceAll("\\r\\n", ""); // 一行
//    Pattern p = Pattern.compile("<div id=\"list-page\">(.+)</div>"); // 1.定位到列表
//    Pattern p2 = Pattern.compile("<li><span>.*?</span><a href=\"([^\"]*)\".*?</a></li>"); // 2.获得列表元素中的文章url
//    Matcher m = p.matcher(content);
//    if (m.find()) {
//      String g = m.group(1);
//      System.out.println(g);
//      Matcher m2 = p2.matcher(g);
//      while(m2.find()) {
//        String url = m2.group(1);
//        if (!url.startsWith("http://")) {
//          url = urlPrefix + url;
//        }
//        System.out.println(url);
//      }
//    }
//  }
  
//  public static void main(String[] args) throws Exception {
//    String content = HttpUtils.call(Method.get, "http://e.games.sina.com.cn/t/news_list/ps4/");
////     System.out.println(content);
//    content = content.replaceAll("\\r\\n", ""); // 一行
//    Pattern p = Pattern.compile("<ul class=\"hp_newslist\">(.+?)</ul>"); // 1.定位到列表
//    Pattern p2 = Pattern.compile("<li>.*?<a href=\"([^\"]*)\".*?</a>.*?</li>"); // 2.获得列表元素中的文章url
//    Matcher m = p.matcher(content);
//    if (m.find()) {
//      String g = m.group(1);
//      System.out.println(g);
//      Matcher m2 = p2.matcher(g);
//      while(m2.find()) {
//        String url = m2.group(1);
//        System.out.println(url);
//      }
//    }
//  }

}
