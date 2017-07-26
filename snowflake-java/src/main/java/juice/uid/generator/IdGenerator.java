package juice.uid.generator;

/**
 * 全局唯一id生成器
 *
 * @author Ricky Fung
 * @create 2017-04-27 21:05
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
    String parseUid(long uid);
}
