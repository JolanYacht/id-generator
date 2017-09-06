package butterfly.generator;

import org.junit.Test;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 * @create 2017-04-27 21:04
 */
public class InstagramIdGeneratorTest {

    final int count = 1000* 1000;

    @Test
    public void benchmark(){

        InstagramIdGenerator idGenerator = new InstagramIdGenerator(1L, 1L);
        long start = System.currentTimeMillis();
        for(int i=0; i<count; i++){
            idGenerator.nextId();
        }
        System.out.println(String.format("gen %d ids cost:%d millis", count, (System.currentTimeMillis() - start)));
    }
}
