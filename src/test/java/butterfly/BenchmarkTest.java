package butterfly;

import butterfly.assigner.impl.SimpleWorkerIdAssigner;
import butterfly.generator.IdGenerator;
import butterfly.generator.impl.SnowflakeIdGenerator;
import org.junit.Before;
import org.junit.Test;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 */
public class BenchmarkTest {
    long epoch = 1480521600000L;
    int workerIdBits = 10;
    int sequenceBits = 12;

    int workId = 0;
    private int count = 1000 * 1000;

    private IdGenerator idGenerator;

    @Before
    public void init() {
        idGenerator = new SnowflakeIdGenerator(epoch, workerIdBits, sequenceBits,
                new SimpleWorkerIdAssigner(1<<workerIdBits, workId));
    }

    @Test
    public void testGenId() {

        long start = System.currentTimeMillis();
        for (int i=0; i<count; i++) {
            idGenerator.genId();
        }
        long cost = System.currentTimeMillis() - start;
        System.out.println(String.format("gen %d ids cost:%d millis", count, cost));
    }

    @Test
    public void testGenBatchIds() {

        int batch = 5;
        int loop = count/batch;
        long start = System.currentTimeMillis();
        for (int i=0; i<loop; i++) {
            idGenerator.genBatchIds(batch);
        }
        long cost = System.currentTimeMillis() - start;
        System.out.println(String.format("gen %d ids cost:%d millis", count, cost));
    }
}
