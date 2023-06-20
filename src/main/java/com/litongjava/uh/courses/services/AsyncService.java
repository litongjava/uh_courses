package com.litongjava.uh.courses.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class AsyncService {

  @Autowired
  private SpiderService spiderService;

  @Async
  public CompletableFuture<String> spiderCourseToDbFirKap() {
    // 模拟耗时的任务
    spiderService.spiderCourseToDbFirKap();
    return CompletableFuture.completedFuture("Task finished");
  }
}
