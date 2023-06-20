package com.litongjava.uh.courses.pipeline;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.litongjava.data.utils.SnowflakeIdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

@Slf4j
public class InstitutionPipeline implements Pipeline {

  @Override
  public void process(ResultItems resultItems, Task task) {
    List<String> links = resultItems.get("links");
    List<String> names = resultItems.get("names");
    //插入数据库
    for (int i = 0; i < links.size(); i++) {
      String link = links.get(i);
      String[] split = link.split("=");
      if (split.length < 2) {
        continue;
      }
      String abbrName = split[1];
      Integer integer = Db.queryInt("select count(1) from institution where abbr_name=?", abbrName);
      if (integer > 0) {
        continue;
      } else {
        Record record = new Record();
        record.put("id", new SnowflakeIdGenerator(0, 0).generateId());
        record.put("abbr_name", abbrName);
        record.put("name", names.get(i));
        boolean institution = Db.save("institution", record);
        log.info("save {},{}", abbrName, institution);
      }
    }
  }
}