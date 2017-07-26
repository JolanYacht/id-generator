package juice.uid.generator;

import org.junit.Test;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 * @create 2017-04-27 20:59
 */
public class SnowflakeIdGeneratorTest {

    @Test
    public void testGetUidWithZk(){

        IdGenerator idGenerator = new IdGeneratorBuilder()
                .zkAddress("localhost:2181")
                .namespace("/myapp/uid/worker")
                .epoch(1480521600000L)
                .build();

        long uid = idGenerator.getUid();
        String extra = idGenerator.parseUid(uid);
        System.out.println(extra);
    }

    @Test
    public void testGetUid(){

        IdGenerator idGenerator = new IdGeneratorBuilder()
                .workId(1L)
                .epoch(1480521600000L)
                .build();

        long uid = idGenerator.getUid();
        String extra = idGenerator.parseUid(uid);
        System.out.println(extra);
    }


}
