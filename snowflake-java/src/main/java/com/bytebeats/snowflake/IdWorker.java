package com.bytebeats.snowflake;

/**
 * Twitter Snowflake
 * 1. 41位为时间戳
 * 2. 5位datacenterId
 * 3. 5位workerId(10位的长度最多支持部署1024个节点）
 * 4. 12位自增序列号（12位顺序号支持每个节点每毫秒产生4096个ID序号）
 *
 * @author Ricky Fung
 * @create 2016-12-19 17:44
 */
public class IdWorker {
    private final long twepoch = 1288834974657L;    //从某个时间戳起
    private final long workerIdBits = 5L;   //机器标识位数
    private final long datacenterIdBits = 5L;   //数据中心标识位数

    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);   //机器ID最大值
    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);   //数据中心ID最大值
    private final long sequenceBits = 12L;  //毫秒内自增位

    private final long workerIdShift = sequenceBits;    //机器ID偏左移12位
    private final long datacenterIdShift = sequenceBits + workerIdBits; //数据中心ID左移17位
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits; //时间毫秒左移22位
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    private long workerId;
    private long datacenterId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    public IdWorker(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    public synchronized long nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        return ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift) | (workerId << workerIdShift) | sequence;
    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }

}
