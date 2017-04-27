package com.bytebeats.uid;

import org.junit.Test;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 */
public class ZkWorkerIdAssignerTest {

    private String address = "localhost:2181";

    @Test
    public void testId(){

        WorkerIdAssigner idAssigner = new ZkWorkerIdAssigner("/myapp/uid/work", address);
        long workId = idAssigner.getWorkId();
        System.out.println(workId);
    }
}
