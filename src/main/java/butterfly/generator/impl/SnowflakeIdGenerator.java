package butterfly.generator.impl;

import butterfly.assigner.WorkerIdAssigner;
import butterfly.domain.IdMetaData;
import butterfly.generator.IdGenerator;

/**
 * Twitter Snowflake
 *
 * @author Ricky Fung
 */
public class SnowflakeIdGenerator implements IdGenerator {

    private final long epoch;    //纪元
    private final int workerIdBits;   //机器标识位数
    private final int sequenceBits;  //毫秒内自增位

    private final long maxWorkerId;   //机器ID最大值

    private final int workerIdLeftShiftBits;    //机器ID偏左移位数
    private final int timestampLeftShiftBits; //时间毫秒左移位数

    private final long sequenceMask;

    private long workerId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    public SnowflakeIdGenerator(long epoch, int workerIdBits, int sequenceBits, WorkerIdAssigner workerIdAssigner) {
        this(epoch, workerIdBits, sequenceBits, workerIdAssigner.getWorkId());
    }

    public SnowflakeIdGenerator(long epoch, int workerIdBits, int sequenceBits, long workerId) {
        this.epoch = epoch;
        this.workerIdBits = workerIdBits;
        this.sequenceBits = sequenceBits;
        this.maxWorkerId = -1L ^ (-1L << workerIdBits);

        this.timestampLeftShiftBits = workerIdBits + sequenceBits;
        this.workerIdLeftShiftBits = sequenceBits;

        this.sequenceMask = (1 << sequenceBits) - 1;

        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("workerId must in [0, %d]", maxWorkerId));
        }
        this.workerId = workerId;
        if(epoch<0 || epoch>System.currentTimeMillis()){
            throw new IllegalArgumentException(String.format("epoch must in (0, %d]", System.currentTimeMillis()));
        }
    }

    @Override
    public synchronized long genId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        if (lastTimestamp == timestamp) {
            if (0L == (++sequence & this.sequenceMask)) {
                timestamp = waitUntilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        return ((timestamp - epoch) << timestampLeftShiftBits) | (workerId << workerIdLeftShiftBits) | sequence;
    }

    @Override
    public long[] genBatchIds(int num) {
        long[] arr = new long[num];
        for (int i=0; i<num; i++) {
            arr[i] = genId();
        }
        return arr;
    }

    @Override
    public IdMetaData parseUid(long uid) {

        long ts = (uid >> timestampLeftShiftBits) + epoch;
        long worker = (uid >> workerIdLeftShiftBits) & ((1 << workerIdBits) - 1);
        long seq = uid & sequenceMask;

        IdMetaData metaData = new IdMetaData();
        metaData.setTimestamp(ts);
        metaData.setWorkerId(worker);
        metaData.setSequence(seq);
        return metaData;
    }

    private long waitUntilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }
}
