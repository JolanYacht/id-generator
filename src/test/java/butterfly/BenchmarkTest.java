package butterfly;

import butterfly.assigner.impl.SimpleWorkerIdAssigner;
import butterfly.generator.IdGenerator;
import butterfly.generator.SnowflakeIdGenerator;
import org.junit.Test;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 * @create 2016-12-19 18:12
 */
public class BenchmarkTest {
    private int count = 1000 * 1000;

    @Test
    public void testSnowflake() {

        IdGenerator idGenerator = new SnowflakeIdGenerator(new SimpleWorkerIdAssigner((long) (1<<12), 1L), 1480521600000L);
        long start = System.currentTimeMillis();
        for (int i=0; i<count; i++) {
            idGenerator.getUid();
        }
        long cost = System.currentTimeMillis() - start;
        System.out.println(String.format("gen %d ids cost:%d millis", count, cost));
    }
}
