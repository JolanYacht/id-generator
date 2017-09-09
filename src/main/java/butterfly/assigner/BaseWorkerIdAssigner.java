package butterfly.assigner;

/**
 * @author Ricky Fung
 */
public abstract class BaseWorkerIdAssigner implements WorkerIdAssigner {
    protected int maxWorkerId;

    public BaseWorkerIdAssigner(int maxWorkerId) {
        this.maxWorkerId = maxWorkerId;
    }

    public int getMaxWorkerId() {
        return maxWorkerId;
    }
}
