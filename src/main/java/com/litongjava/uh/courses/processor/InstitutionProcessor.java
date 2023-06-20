package com.litongjava.uh.courses.processor;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class InstitutionProcessor implements PageProcessor {

  private Site site = Site.me().setRetryTimes(3).setSleepTime(10000);

  @Override
  public void process(Page page) {
    String selector = "ul.institutions li a";
    List<String> links = page.getHtml().css(selector, "href").all();
    List<String> names = page.getHtml().css(selector, "text").all();

    page.putField("links", links);
    page.putField("names", names);
  }

  @Override
  public Site getSite() {
    return site;
  }

}
