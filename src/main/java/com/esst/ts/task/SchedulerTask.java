package com.esst.ts.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务基本模板
 * SHY
 */
@Component
public class SchedulerTask {
    private final Logger log = LoggerFactory.getLogger(SchedulerTask.class);

    @Scheduled(cron = "0 0 4 * * ?")
    private void cleaner() {
        log.info("定时清理开始。。。");
    }

}
