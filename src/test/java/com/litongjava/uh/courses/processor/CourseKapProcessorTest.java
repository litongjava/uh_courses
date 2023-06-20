package com.litongjava.uh.courses.processor;

import com.litongjava.uh.courses.init.TableToJsonInit;
import com.litongjava.uh.courses.pipeline.CourseKapPipeline;
import org.junit.Before;
import org.junit.Test;
import us.codecraft.webmagic.Spider;

import static org.junit.Assert.*;

/**
 * Created by litonglinux@qq.com on 2023/6/20_22:25
 */
public class CourseKapProcessorTest {

  @Before
  public void before() {
    TableToJsonInit.initActiveRecord();
  }

  @Test
  public void testSpider() {
    String url = "https://www.sis.hawaii.edu/uhdad/avail.classes?i=KAP&t=202410&s=CULN";
    int threadNum = 1;
    Spider.create(new CourseKapProcessor())
      // url
      .addUrl(url) // Add the url you want to scrape
      //.addPipeline(new CourseKapPipeline(null))
      //
      .thread(threadNum).run();
  }

  @Test
  public void testTrim() {
    System.out.println("start");
    System.out.println(" ".trim().length());
    System.out.println("  ".trim().length());
    System.out.println("   ".trim().length());
    System.out.println("                   ".trim().length());
    System.out.println("end");
  }

  @Test
  public void stringToInt() {
    //int i = Integer.parseInt(" ");
    int i = Integer.parseInt(" ".trim());
    System.out.println(i);
  }

}