package butterfly;

import butterfly.assigner.WorkerIdAssigner;
import butterfly.generator.SnowflakeIdGenerator;
import butterfly.generator.IdGenerator;

/**
 * @author Ricky Fung
 */
public class DefaultIdGeneratorFactory implements IdGeneratorFactory {

    @Override
    public IdGenerator createIdGenerator(WorkerIdAssigner workerIdAssigner, long epoch) {
        return new SnowflakeIdGenerator(workerIdAssigner, epoch);
    }
}
