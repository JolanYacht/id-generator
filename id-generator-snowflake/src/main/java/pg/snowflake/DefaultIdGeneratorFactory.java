package pg.snowflake;

import pg.snowflake.assigner.WorkerIdAssigner;
import pg.snowflake.generator.IdGenerator;
import pg.snowflake.generator.SnowflakeIdGenerator;

/**
 * @author Ricky Fung
 */
public class DefaultIdGeneratorFactory implements IdGeneratorFactory {

    @Override
    public IdGenerator createIdGenerator(WorkerIdAssigner workerIdAssigner, long epoch) {
        return new SnowflakeIdGenerator(workerIdAssigner, epoch);
    }
}
