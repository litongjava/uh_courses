package com.litongjava.uh.courses.pipeline;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.litongjava.data.utils.SnowflakeIdGenerator;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by litonglinux@qq.com on 2023/6/20_7:05
 */
@Slf4j
public class SemesterPipeline implements Pipeline {

  String pattern = "i=(\\w+)&t=(\\d+)";
  private List<Record> records;

  public SemesterPipeline(List<Record> records) {
    this.records = records;
  }

  @SneakyThrows
  @Override
  public void process(ResultItems resultItems, Task task) {
    //<a href="./avail.classes?i=HAW&amp;t=202410">Fall 2023</a>
    List<String> links = resultItems.get("links");
    List<String> names = resultItems.get("names");

    int size = links.size();
    for (int i = 0; i < size; i++) {

      String uri = links.get(i);
      String abbrName = null;
      String t = null;
      Pattern compiledPattern = Pattern.compile(pattern);
      Matcher matcher = compiledPattern.matcher(uri);

      if (matcher.find()) {
        abbrName = matcher.group(1);
        t = matcher.group(2);

      } else {
        System.out.println("没有匹配的值");
        continue;
      }

      Long institutionId = getInstitutionId(abbrName);
      String sql = "select count(1) from semester where institution_id=? and t=?";
      Integer integer = Db.queryInt(sql, institutionId, t);
      if (integer > 0) {
        continue;
      } else {
        Record record = new Record();
        record.put("id", new SnowflakeIdGenerator(Thread.currentThread().getId(), 0).generateId());
        record.put("institution_id", institutionId);
        record.put("name", names.get(i));
        record.put("t", t);
        boolean institution = Db.save("semester", record);
        log.info("save {},{}", abbrName, institution);
      }
    }
  }

  private Long getInstitutionId(String abbrName) {
    for (Record record : records) {
      if (record.get("abbr_name").equals(abbrName)) {
        return record.getLong("id");
      }
    }
    return null;
  }
}
