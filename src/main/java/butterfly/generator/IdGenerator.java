package butterfly.generator;

import butterfly.domain.IdMetaData;

/**
 * @author Ricky Fung
 */
public interface IdGenerator {

    /**
     * generate single id
     * @return
     */
    long genId();

    /**
     * generate batch id
     * @param num
     * @return
     */
    long[] genBatchIds(int num);

    /**
     * parse id meta data info include：[timestamp, workerId, sequence]
     * @param uid
     * @return
     */
    IdMetaData parseUid(long uid);
}
