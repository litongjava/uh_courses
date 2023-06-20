package com.litongjava.uh.courses;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.litongjava.hotswap.wrapper.spring.boot.SpringApplicationWrapper;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class UhCoursesApp {

  public static void main(String[] args) {
    long start = System.currentTimeMillis();
    SpringApplicationWrapper.run(UhCoursesApp.class, args);
    long end = System.currentTimeMillis();
    System.out.println((end - start) + "(ms)");

  }
}