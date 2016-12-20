package com.bytebeats;

import com.bytebeats.snowflake.IdGenerator;
import com.bytebeats.snowflake.IdWorker;
import org.junit.Test;
import java.util.Date;

public class AppTest {
    private int count = 10000;

    @Test
    public void testIdGenerator() {

        IdGenerator generator = new IdGenerator(0);

        int i = 0;
        while(i<count){
            System.out.println(generator.nextId());
            i++;
        }
    }

    @Test
    public void testSnowflake() {

        IdWorker worker = new IdWorker(0, 0);
        System.out.println(worker.nextId());
    }

    @Test
    public void testTimestamp() {

        Date date = new Date(1288834974657L);
        System.out.println(date);

    }
}
