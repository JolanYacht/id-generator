package pg.snowflake.generator;

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
    public void testId(){

        InstagramIdGenerator idGenerator = new InstagramIdGenerator(1L, 1L);
        long start = System.nanoTime();
        for(int i=0; i<count; i++){
            idGenerator.nextId();
        }
        System.out.println("耗时:"+(System.nanoTime() - start)+"nanoseconds");
    }
}
