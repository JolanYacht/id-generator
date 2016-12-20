package com.bytebeats;

import com.bytebeats.snowflake.IdWorker;
import org.junit.Test;
import java.util.Date;

public class AppTest {

    @Test
    public void testApp() {

        IdWorker worker = new IdWorker(0, 0);
        System.out.println(worker.nextId());
    }

    @Test
    public void testTimestamp() {

        Date date = new Date(1288834974657L);
        System.out.println(date);

    }
}
