package juice.uid;

import juice.uid.assigner.WorkerIdAssigner;
import juice.uid.generator.IdGenerator;

/**
 * @author Ricky Fung
 */
public interface IdGeneratorFactory {

    IdGenerator createIdGenerator(WorkerIdAssigner workerIdAssigner, long epoch);
}
