package com.bytebeats.uid;

import org.junit.Test;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 * @create 2017-04-27 20:59
 */
public class TwitterIdGeneratorTest {

    final int count = 1000* 1000;

    @Test
    public void testId(){

        TwitterIdGenerator idGenerator = new TwitterIdGenerator(1L);
        long start = System.nanoTime();
        for(int i=0; i<count; i++){
            idGenerator.nextId();
        }
        System.out.println("耗时:"+(System.nanoTime() - start)+"nanoseconds");
    }
}
