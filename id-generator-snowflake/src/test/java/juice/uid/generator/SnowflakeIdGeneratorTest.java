package juice.uid.generator;

import juice.uid.DefaultIdGeneratorFactory;
import juice.uid.IdGeneratorFactory;
import juice.uid.assigner.DefaultWorkerIdAssigner;
import juice.uid.assigner.ZookeeperWorkerIdAssigner;
import org.junit.Ignore;
import org.junit.Test;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 * @create 2017-04-27 20:59
 */
public class SnowflakeIdGeneratorTest {

    private final IdGeneratorFactory factory = new DefaultIdGeneratorFactory();

    @Test
    @Ignore
    public void testGetUidWithZk(){

        IdGenerator idGenerator = factory.createIdGenerator(new ZookeeperWorkerIdAssigner("127.0.0.1:2181", "/juice/uid/worker"), 1480521600000L);

        long uid = idGenerator.getUid();
        String extra = idGenerator.parseUid(uid);
        System.out.println(extra);
    }

    @Test
    public void testGetUid(){

        IdGenerator idGenerator = factory.createIdGenerator(new DefaultWorkerIdAssigner(1L), 1480521600000L);
        long uid = idGenerator.getUid();
        String extra = idGenerator.parseUid(uid);
        System.out.println(extra);
    }


}
