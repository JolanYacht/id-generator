package pg.snowflake;

import pg.snowflake.assigner.WorkerIdAssigner;
import pg.snowflake.generator.IdGenerator;

/**
 * @author Ricky Fung
 */
public interface IdGeneratorFactory {

    IdGenerator createIdGenerator(WorkerIdAssigner workerIdAssigner, long epoch);
}
