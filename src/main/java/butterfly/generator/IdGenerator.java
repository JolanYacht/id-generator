package butterfly.generator;

import butterfly.domain.UidMetaData;

/**
 * 全局唯一id生成器
 * @author Ricky Fung
 */
public interface IdGenerator {

    /**
     * 获取uid
     * @return
     */
    long getUid();

    /**
     * 解析uid包含的信息：[Timestamp, WorkerId, Sequence]
     * @param uid
     * @return
     */
    UidMetaData parseUid(long uid);
}
