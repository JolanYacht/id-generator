package butterfly.domain;

/**
 * @author Ricky Fung
 */
public class IdMetaData {
    private long timestamp;
    private long workerId;
    private long sequence;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(long workerId) {
        this.workerId = workerId;
    }

    public long getSequence() {
        return sequence;
    }

    public void setSequence(long sequence) {
        this.sequence = sequence;
    }

    @Override
    public String toString() {
        return "IdMetaData{" +
                "timestamp=" + timestamp +
                ", workerId=" + workerId +
                ", sequence=" + sequence +
                '}';
    }
}
