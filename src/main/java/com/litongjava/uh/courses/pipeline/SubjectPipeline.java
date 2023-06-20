package com.litongjava.uh.courses.pipeline;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.litongjava.data.utils.SnowflakeIdGenerator;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by litonglinux@qq.com on 2023/6/20_7:05
 */
@Slf4j
public class SubjectPipeline implements Pipeline {

  //String pattern = "i=(\\w+)&t=(\\d+)";
  String pattern = "i=(.*?)&t=(.*?)&s=(.*)";
  private List<Record> records;

  public SubjectPipeline(List<Record> records) {
    this.records = records;
  }

  @SneakyThrows
  @Override
  public void process(ResultItems resultItems, Task task) {
    //https://www.sis.hawaii.edu/uhdad/avail.classes?i=KAP&t=202410&s=ACC
    List<String> links = resultItems.get("links");
    List<String> names = resultItems.get("names");

    int size = links.size();
    for (int i = 0; i < size; i++) {
      String uri = links.get(i);
      String name = names.get(i);
      String abbrName = null;
      String t = null;
      String s = null;
      Pattern compiledPattern = Pattern.compile(pattern);
      Matcher matcher = compiledPattern.matcher(uri);

      if (matcher.find()) {
        abbrName = matcher.group(1);
        t = matcher.group(2);
        s = matcher.group(3);

      } else {
        System.out.println("没有匹配的值");
        continue;
      }

      Long semesterId = getSemesterId(abbrName, t);
      String sql = "select count(1) from subject where semester_id=? and s=?";
      Integer integer = Db.queryInt(sql, semesterId, s);
      if (integer > 0) {
        continue;
      } else {
        Record record = new Record();
        record.put("id", new SnowflakeIdGenerator(Thread.currentThread().getId(), 0).generateId());
        record.put("semester_id", semesterId);
        record.put("name", name);
        record.put("s", s);
        boolean institution = Db.save("subject", record);
        log.info("save {},{}", abbrName, institution);
      }
    }
  }

  private Long getSemesterId(String abbrName, String t) {
    for (Record record : records) {
      if (record.getStr("abbr_name").equals(abbrName) && record.getStr("t").equals(t)) {
        return record.getLong("id");
      }
    }
    return null;
  }

}
