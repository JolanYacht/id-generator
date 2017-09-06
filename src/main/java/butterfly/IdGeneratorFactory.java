package butterfly;

import butterfly.assigner.WorkerIdAssigner;
import butterfly.generator.IdGenerator;

/**
 * @author Ricky Fung
 */
public interface IdGeneratorFactory {

    IdGenerator createIdGenerator(WorkerIdAssigner workerIdAssigner, long epoch);
}
