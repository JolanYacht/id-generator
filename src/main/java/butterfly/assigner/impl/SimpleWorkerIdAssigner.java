package butterfly.assigner.impl;

import butterfly.assigner.BaseWorkerIdAssigner;

/**
 * 人工指定workerId
 * @author Ricky Fung
 */
public class SimpleWorkerIdAssigner extends BaseWorkerIdAssigner {

    private final long id;

    public SimpleWorkerIdAssigner(int maxWorkerId, long id) {
        super(maxWorkerId);
        this.id = id;
    }

    @Override
    public long getWorkId() {
        return id;
    }
}
