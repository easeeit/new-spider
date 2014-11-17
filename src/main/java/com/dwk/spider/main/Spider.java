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

}
