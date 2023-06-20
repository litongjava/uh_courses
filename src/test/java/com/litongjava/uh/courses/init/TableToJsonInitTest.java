package com.litongjava.uh.courses.init;


import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import org.junit.Before;
import org.junit.Test;

public class TableToJsonInitTest {

  @Before
  public void before() {
    TableToJsonInit.initActiveRecord();
  }

  @Test
  public void test() {
    Record findFirst = Db.findFirst("select 1");
    System.out.println(findFirst);
  }

}
