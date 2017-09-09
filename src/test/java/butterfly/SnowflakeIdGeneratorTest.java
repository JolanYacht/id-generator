package butterfly;

import butterfly.assigner.impl.SimpleWorkerIdAssigner;
import butterfly.assigner.impl.ZookeeperWorkerIdAssigner;
import butterfly.domain.UidMetaData;
import butterfly.generator.IdGenerator;
import butterfly.generator.impl.SnowflakeIdGenerator;
import org.junit.Ignore;
import org.junit.Test;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 */
public class SnowflakeIdGeneratorTest {
    long epoch = 1480521600000L;
    int workerIdBits = 10;
    int sequenceBits = 12;

    int workerId = 0;

    @Test
    @Ignore
    public void testZkIdAssigner(){

        IdGenerator idGenerator = new SnowflakeIdGenerator(epoch, workerIdBits, sequenceBits,
                new ZookeeperWorkerIdAssigner("127.0.0.1:2181", "/pg/uid/worker", 1<<workerIdBits));

        long uid = idGenerator.getUid();
        UidMetaData meta = idGenerator.parseUid(uid);
        System.out.println(uid+"\t"+meta);
    }

    @Test
    public void testSimpleIdAssigner(){

        IdGenerator idGenerator = new SnowflakeIdGenerator(epoch, workerIdBits, sequenceBits,
                new SimpleWorkerIdAssigner(1<<workerIdBits, workerId));

        long uid = idGenerator.getUid();
        UidMetaData meta = idGenerator.parseUid(uid);
        System.out.println(uid+"\t"+meta);

        uid = idGenerator.getUid();
        meta = idGenerator.parseUid(uid);
        System.out.println(uid+"\t"+meta);

    }


}
