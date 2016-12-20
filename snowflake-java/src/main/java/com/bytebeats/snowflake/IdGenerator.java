package com.bytebeats.snowflake;

/**
 *
 * 1. 41位为时间戳
 * 2. 10位workerId(10位的长度最多支持部署1024个节点）
 * 3. 12位自增序列号（12位顺序号支持每个节点每毫秒产生4096个ID序号）
 *
 * @author Ricky Fung
 * @create 2016-12-19 17:44
 */
public class IdGenerator {
    private final long twepoch = 1288834974657L;    //从某个时间戳起
    private final long workerIdBits = 10L;   //机器标识位数

    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);   //机器ID最大值
    private final long sequenceBits = 12L;  //毫秒内自增位

    private final long workerIdShift = sequenceBits;    //机器ID偏左移12位
    private final long timestampLeftShift = sequenceBits + workerIdBits; //时间毫秒左移22位
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    private long workerId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    public IdGenerator(long workerId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        this.workerId = workerId;
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

        return ((timestamp - twepoch) << timestampLeftShift) | (workerId << workerIdShift) | sequence;
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
