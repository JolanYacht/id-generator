package butterfly;

import butterfly.assigner.impl.SimpleWorkerIdAssigner;
import butterfly.generator.IdGenerator;
import butterfly.generator.impl.SnowflakeIdGenerator;
import org.junit.Test;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 * @create 2016-12-19 18:12
 */
public class BenchmarkTest {
    long epoch = 1480521600000L;
    int workerIdBits = 10;
    int sequenceBits = 12;

    private int count = 1000 * 1000;

    @Test
    public void testSnowflake() {

        IdGenerator idGenerator = new SnowflakeIdGenerator(epoch, workerIdBits, sequenceBits,
                new SimpleWorkerIdAssigner(1<<workerIdBits, 0L));
        long start = System.currentTimeMillis();
        for (int i=0; i<count; i++) {
            idGenerator.getUid();
        }
        long cost = System.currentTimeMillis() - start;
        System.out.println(String.format("gen %d ids cost:%d millis", count, cost));
    }
}
