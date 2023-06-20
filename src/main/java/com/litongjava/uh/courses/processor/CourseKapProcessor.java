package com.litongjava.uh.courses.processor;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
public class CourseKapProcessor implements PageProcessor {
  private Site site = Site.me().setRetryTimes(3).setSleepTime(10000);


  @Override
  public void process(Page page) {
    Elements rows = page.getHtml().getDocument().select("table.listOfClasses > tbody > tr");
    String sourceUrl = page.getUrl().toString();

    List<Map<String, Object>> dataList = new ArrayList<>();
    for (int i = 0; i < rows.size(); i++) {
      Element row = rows.get(i);
      Elements columns = row.select("td");
      Map<String, Object> dataMap = new HashMap<>();
      int size = columns.size(); // 正常请求下值是15
      if (size > 14) {
        Elements nextRowTds = row.nextElementSibling().select("td");
        if (!(nextRowTds.size() > 14)) {
          dataMap.put("remark", nextRowTds.first().text().trim());
          //跳过下一行
          i++;
        }
        dataMap.put("sources_url", sourceUrl);
        try {
          dataMap.put("focus_on", columns.get(0).text().trim());
          dataMap.put("crn", columns.get(1).text().trim().trim());
          dataMap.put("course", columns.get(2).text().trim());
          dataMap.put("section", columns.get(3).text().trim());
          dataMap.put("title", columns.get(4).text().trim());
          dataMap.put("credits", columns.get(5).text().trim());
          dataMap.put("instructor", columns.get(6).text().trim());
          dataMap.put("curr_enrolled", columns.get(7).text().trim());
          dataMap.put("seats_avail", columns.get(8).text().trim());
          dataMap.put("curr_waitlisted", columns.get(9).text().trim());
          dataMap.put("wait_avail", columns.get(10).text().trim());
          dataMap.put("days", columns.get(11).text().trim());
          dataMap.put("time", columns.get(12).text().trim());
          dataMap.put("room", columns.get(13).text().trim());
          dataMap.put("dates", columns.get(14).text().trim());
          dataMap.put("details_url", columns.get(1).select("a").attr("href").trim());
        } catch (Exception e) {
          log.error("exception:{},{},{}", e.getMessage(), sourceUrl, i);
          e.printStackTrace();
        }
        dataList.add(dataMap);
      }
    }
    page.putField("dataList", dataList);

  }

  @Override
  public Site getSite() {
    return site;
  }

}
