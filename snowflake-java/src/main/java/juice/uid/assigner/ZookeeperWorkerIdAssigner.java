package juice.uid.assigner;

/**
 * 启动时通过Zookeeper获取workerId
 * @author Ricky Fung
 */
public class ZookeeperWorkerIdAssigner implements WorkerIdAssigner {
    private String zkAddress;
    private String namespace;

    public ZookeeperWorkerIdAssigner(String zkAddress, String namespace) {
        this.zkAddress = zkAddress;
        this.namespace = namespace;
    }

    @Override
    public long getWorkId() {
        return 0;
    }
}
