package butterfly.assigner.impl;

import butterfly.assigner.BaseWorkerIdAssigner;

/**
 * 人工指定workerId
 * @author Ricky Fung
 */
public class SimpleWorkerIdAssigner extends BaseWorkerIdAssigner {

    private final int id;

    public SimpleWorkerIdAssigner(int maxWorkerId, int id) {
        super(maxWorkerId);
        this.id = id;
    }

    @Override
    public int getWorkId() {
        return id;
    }
}
