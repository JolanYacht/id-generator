package com.bytebeats.uid;

/**
 * Twitter Snowflake
 * 1. 41位为时间戳
 * 2. 10位workerId(10位的长度最多支持部署1024个节点）
 * 3. 12位自增序列号（12位顺序号支持每个节点每毫秒产生4096个ID序号）
 *
 * @author Ricky Fung
 * @create 2016-12-19 17:44
 */
public class TwitterIdGenerator {
    private final long EPOCH = 1480521600000L;    //2016-12-01 00:00:00

    public static final long WORKER_ID_BITS = 10L;   //机器标识位数
    public static final long SEQUENCE_BITS = 12L;  //毫秒内自增位

    private static final long MAX_WORKER_ID = -1L ^ (-1L << WORKER_ID_BITS);   //机器ID最大值

    private static final long WORKER_ID_LEFT_SHIFT_BITS = SEQUENCE_BITS;    //机器ID偏左移12位

    private static final long TIMESTAMP_LEFT_SHIFT_BITS  = SEQUENCE_BITS + WORKER_ID_BITS; //时间毫秒左移22位

    private static final long SEQUENCE_MASK = (1 << SEQUENCE_BITS) - 1;

    private long workerId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    public TwitterIdGenerator(long workerId) {
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", MAX_WORKER_ID));
        }
        this.workerId = workerId;
    }

    public synchronized long nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        if (lastTimestamp == timestamp) {
            if (0L == (++sequence & SEQUENCE_MASK)) {
                timestamp = waitUntilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        return ((timestamp - EPOCH) << TIMESTAMP_LEFT_SHIFT_BITS) | (workerId << WORKER_ID_LEFT_SHIFT_BITS) | sequence;
    }

    private long waitUntilNextMillis(long lastTimestamp) {
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
