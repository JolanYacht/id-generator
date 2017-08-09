package juice.uid;

import juice.uid.assigner.WorkerIdAssigner;
import juice.uid.generator.IdGenerator;
import juice.uid.generator.SnowflakeIdGenerator;

/**
 * @author Ricky Fung
 */
public class DefaultIdGeneratorFactory implements IdGeneratorFactory {

    @Override
    public IdGenerator createIdGenerator(WorkerIdAssigner workerIdAssigner, long epoch) {
        return new SnowflakeIdGenerator(workerIdAssigner, epoch);
    }
}
