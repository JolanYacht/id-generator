package com.bytebeats.uid;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 * @create 2016-12-19 18:12
 */
public class BenchmarkTest {
    private int count = 1000000;

    public static void main(String[] args) {

        new BenchmarkTest().testSnowflake();
    }

    private void testSnowflake() {

        InstagramIdGenerator worker = new InstagramIdGenerator(0, 0);
        long start = System.currentTimeMillis();
        int i= 0;
        while(i<count){
            worker.nextId();
            i++;
        }
        long cost = System.currentTimeMillis() - start;
        System.out.println(cost+"ms,\t"+(count/cost)+"/ms");
    }
}
