package butterfly;

import butterfly.assigner.impl.SimpleWorkerIdAssigner;
import butterfly.assigner.impl.ZookeeperWorkerIdAssigner;
import butterfly.domain.UidMetaData;
import butterfly.generator.IdGenerator;
import butterfly.generator.SnowflakeIdGenerator;
import org.junit.Ignore;
import org.junit.Test;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 * @create 2017-04-27 20:59
 */
public class SnowflakeIdGeneratorTest {

    @Test
    @Ignore
    public void testZkIdAssigner(){

        IdGenerator idGenerator = new SnowflakeIdGenerator(new ZookeeperWorkerIdAssigner("127.0.0.1:2181", "/pg/uid/worker", (long) (1<<12)), 1480521600000L);

        long uid = idGenerator.getUid();
        UidMetaData extra = idGenerator.parseUid(uid);
        System.out.println(uid+"\t"+extra);
    }

    @Test
    public void testSimpleIdAssigner(){

        IdGenerator idGenerator = new SnowflakeIdGenerator(new SimpleWorkerIdAssigner((long) (1<<12), 0L), 1480521600000L);

        long uid = idGenerator.getUid();
        UidMetaData extra = idGenerator.parseUid(uid);
        System.out.println(uid+"\t"+extra);

        uid = idGenerator.getUid();
        extra = idGenerator.parseUid(uid);
        System.out.println(uid+"\t"+extra);
    }


}
