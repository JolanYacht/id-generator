package com.bytebeats.uid;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 */
public class ZkWorkerIdAssigner implements WorkerIdAssigner {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private String namespace;
    private String zkAddress;

    public ZkWorkerIdAssigner(String namespace, String zkAddress) {
        this.namespace = namespace;
        this.zkAddress = zkAddress;
    }

    @Override
    public long getWorkId() {

        String ip = "localhost";
        CuratorFramework client = null;
        try {
            client = initZk(zkAddress);
            String path = namespace + "/id-";
            String result = client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(path, ip.getBytes());
            System.out.println(result);

            List<String> children = client.getChildren().forPath(namespace);
            System.out.println(children);
            return 0;
        } catch (Exception e) {
            logger.error("", e);
            throw new RuntimeException("create node failed", e);
        } finally {
            if(client!=null){
                client.close();
            }
        }
    }

    private CuratorFramework initZk(String address){
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient(address, retryPolicy);
        client.start();
        return client;
    }
}
