package com.litongjava.uh.courses.demo;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class InstitutionProcessor implements PageProcessor {

  private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

  @Override
  public void process(Page page) {
    List<String> links = page.getHtml().css("ul.institutions li a", "href").all();
    List<String> names = page.getHtml().css("ul.institutions li a", "text").all();

    page.putField("links", links);
    page.putField("names", names);
  }

  @Override
  public Site getSite() {
    return site;
  }

}
