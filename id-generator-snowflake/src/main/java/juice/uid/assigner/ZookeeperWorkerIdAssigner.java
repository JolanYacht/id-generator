package juice.uid.assigner;

import juice.uid.util.NetUtils;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.util.BitSet;
import java.util.List;

/**
 * 启动时通过Zookeeper获取workerId
 * @author Ricky Fung
 */
public class ZookeeperWorkerIdAssigner implements WorkerIdAssigner {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String zkAddress;
    private String namespace;
    private int sessionTimeout;
    private int connectTimeout;
    private ZkClient zkClient;
    private static final String PREFIX = "worker_";
    private static final int MAX_CHILDREN_SIZE = 1024;

    public ZookeeperWorkerIdAssigner(String zkAddress, String namespace) {
        this(zkAddress, 60 * 1000, 5000, namespace);
    }

    public ZookeeperWorkerIdAssigner(String zkAddress, int sessionTimeout, int connectTimeout, String namespace) {
        this.zkAddress = zkAddress;
        this.sessionTimeout = sessionTimeout;
        this.connectTimeout = connectTimeout;
        this.namespace = namespace;
        initZooKeeper(this.zkAddress, this.sessionTimeout, this.connectTimeout);
    }

    @Override
    public long getWorkId() {
        InetAddress address = NetUtils.getLocalAddress();
        String ip = "N/A";
        if(address!=null) {
            ip = NetUtils.getLocalAddress().getHostAddress();
        }
        String path;
        if(namespace.endsWith("/")) {
            path = namespace + PREFIX;
        } else {
            path = namespace + "/"+ PREFIX;
        }

        zkClient.createPersistent(namespace, true);

        List<String> children = zkClient.getChildren(namespace);
        if(children!=null && children.size()>MAX_CHILDREN_SIZE) {
            throw new IllegalStateException(String.format("children size > %s in namespace:%s", MAX_CHILDREN_SIZE, namespace));
        }

        logger.info("create node path:{}, data:{}", path, ip);
        String result = zkClient.createEphemeralSequential(path, ip);
        logger.info("create node path:{}, result:{}", path, result);

        if(children==null || children.size()==0) {
            return 0;
        }

        BitSet bs = new BitSet(MAX_CHILDREN_SIZE);
        int index;
        for(String child : children) {
            index = parseId(child)%1024;
            bs.set(index);
        }
        index = parseId(result)%1024;
        if(bs.get(index)) {
            throw new IllegalStateException("worker id:"+index+" is already used.");
        }
        return index;
    }

    private void initZooKeeper(String zkAddress, int sessionTimeout, int connectTimeout) {
        logger.info("init ZooKeeper address:{} sessionTimeout:{}, connectTimeout:{}", zkAddress, sessionTimeout, connectTimeout);
        // 连接到ZK服务，多个可以用逗号分割写
        zkClient = new ZkClient(zkAddress, sessionTimeout, connectTimeout);
        logger.info("init ZooKeeper address:{} over!", zkAddress);
    }

    public int parseId(String child) {
        String str = child.substring(child.lastIndexOf("_")+1);
        if(str.startsWith("0")) {
            return Integer.parseInt(str.substring(str.indexOf("0")+1));
        }
        return Integer.parseInt(str);
    }

    public void close() {
        zkClient.close();
    }

    public static void main(String[] args) {

        ZookeeperWorkerIdAssigner assigner = new ZookeeperWorkerIdAssigner("127.0.0.1:2181", "/juice/uid/worker");
        System.out.println(assigner.parseId("/juice/uid/worker/worker_1000000001"));
        assigner.close();
    }
}
