package com.litongjava.uh.courses.demo;

import us.codecraft.webmagic.Spider;

public class MyPageProcessorDemo {
  // WebMagic使用的默认下载器是HttpClient
  public static void main(String[] args) {

    // 提供自己定义的PageProcessor
    String url = "https://www.jd.com/moreSubject.aspx";
    Spider.create(new MyPageProcessor())
        // 设置初始下载url地址
        .addUrl(url).run();

  }
}
