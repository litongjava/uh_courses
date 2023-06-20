package com.litongjava.uh.courses.services;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.DbTemplate;
import com.jfinal.plugin.activerecord.Record;
import com.litongjava.data.utils.SnowflakeIdGenerator;
import com.litongjava.uh.courses.init.TableToJsonInit;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class SpiderServiceTest {

  SpiderService spiderService = new SpiderService();

  @Before
  public void before() {
    TableToJsonInit.initActiveRecord();
  }

  @Test
  public void test_insertInstitution() {
    Record record = new Record();
    record.put("id", new SnowflakeIdGenerator(0, 0).generateId());
    record.put("abb_name", "KCC");
    record.put("name", "Kapi'olani Community College");
    boolean institution = Db.save("institution", record);
    System.out.println(institution);

  }

  @Test
  public void spiderInstitutionToDb() {
    spiderService.spiderInstitutionToDb();
  }

  @Test
  public void spiderSemesterToDb() {
    spiderService.spiderSemesterToDb();
  }

  @Test
  public void spiderSubjectToDb() {
    spiderService.spiderSubjectToDb();
  }

  @Test
  public void executeSqlFromFile() {
    DbTemplate template = Db.template("test.selectOne");
    Record first = template.findFirst();
    System.out.println(first);
  }

  @Test
  public void slectForIts() {
    DbTemplate template = Db.template("subject.slectForIts");
    List<Record> records = template.find();
    System.out.println(records);
  }

  @Test
  public void slectItsByI() {
    //DbTemplate template = Db.template("subject.slectItsByI","KAP");
    String sql = Db.getSql("subject.slectItsByI");

    List<Record> records = Db.find(sql, "KAP");
//    List<Record> records = template.find();
    System.out.println(records);
  }

  @Test
  public void testSelectWithParam() {
    //失败
    String template = "select * from institution where abbr_name = ?";
    DbTemplate kap = Db.template(template, "KAP");
    Record first = kap.findFirst();
  }

  @Test
  public void spiderCourseToDbFirKap() {
    spiderService.spiderCourseToDbFirKap();
  }

}
