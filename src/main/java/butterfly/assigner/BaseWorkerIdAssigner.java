package butterfly.assigner;

/**
 * @author Ricky Fung
 */
public abstract class BaseWorkerIdAssigner implements WorkerIdAssigner {
    protected Long maxWorkerId;

    public BaseWorkerIdAssigner(Long maxWorkerId) {
        this.maxWorkerId = maxWorkerId;
    }

    public Long getMaxWorkerId() {
        return maxWorkerId;
    }
}
