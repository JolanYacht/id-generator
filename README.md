# id-generator
Distributed unique id generator.

## 分布式唯一主键生成器
众所周知，分库分表首先要解决的就是分布式唯一主键的问题，业界也有很多相关方案：<br>

| 实现方案 | 优点 | 缺点 |
| :----: | :----: | :----: |
| UUID | 本地生成，不需要RPC，低延时； 扩展性好，基本没有性能上限 | 无法保证趋势递增；UUID过长128位，不易存储，往往用字符串表示 |
| Snowflake   | 分布式生成，无单点； 趋势递增，生成效率快     | 没有全局时钟的情况下，只能保证趋势递增； 当通过NTP进行时钟同步时可能会出现重复ID；数据间隙较大    |
| proxy服务+数据库分段获取ID | 分布式生成，段用完后需要去DB获取，同server有序 | 可能产生数据空洞，即有些ID没有分配就被跳过了，主要原因是在服务重启的时候发生；|

### Snowflake
Snowflake 生成的 unique ID 的组成 (由高位到低位):

1. 41 bits: Timestamp (毫秒级)
2. 10 bits: 节点 ID (datacenter ID 5 bits + worker ID 5 bits)
3. 12 bits: sequence number

一共 63 bits (最高位是 0)
unique ID 生成过程:

* 10 bits 的机器号, 在 ID 分配 Worker 启动的时候，从一个 Zookeeper 集群获取 (保证所有的 Worker 不会有重复的机器号)；
* 41 bits 的 Timestamp: 每次要生成一个新 ID 的时候，都会获取一下当前的 Timestamp, 然后分两种情况生成 sequence number；
* 如果当前的 Timestamp 和前一个已生成 ID 的 Timestamp 相同 (在同一毫秒中)，就用前一个 ID 的 sequence number + 1 作为新的 sequence number (12 bits);  
  如果本毫秒内的所有 ID 用完，等到下一毫秒继续 (这个等待过程中, 不能分配出新的 ID)；
* 如果当前的 Timestamp 比前一个 ID 的 Timestamp 大, 随机生成一个初始 sequence number (12bits) 作为本毫秒内的第一个 sequence number；

整个过程中只是在 Worker 启动的时候会对外部有依赖 (需要从 Zookeeper 获取 Worker 号) 之后就可以独立工作了，做到了去中心化。


### Ticket服务
通过一张通用的Ticket表来实现分布式ID的持久化，执行update更新语句来获取一批Ticket，这些获取到的Ticket会在内存中进行分配，分配完之后再从DB获取下一批Ticket。

```
CREATE TABLE `tb_ticket` (
  `id` BIGINT(15) NOT NULL AUTO_INCREMENT COMMENT '自增id主键',
  `tag` VARCHAR(40) NOT NULL COMMENT'业务tag',
  `max_id` BIGINT(20) NOT NULL COMMENT'当前该Tag已分配出去的最大ID值',
  `step` INT(6) NOT NULL COMMENT'号段长度',
  `desc` VARCHAR(200) NOT NULL COMMENT'desc',
  `create_time` TIMESTAMP NOT NULL COMMENT'创建时间',
  `update_time` TIMESTAMP NOT NULL COMMENT'更新时间',
  PRIMARY KEY (`ID`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT'Ticket服务表';
```

具体实现：<br>
每个业务tag对应一条DB记录，DB MaxID字段记录当前该Tag已分配出去的最大ID值。

IDGenerator服务启动之初向DB申请一个号段，传入号段长度如 genStep = 10000，DB事务置 MaxID = MaxID + genStep，DB设置成功代表号段分配成功。每次IDGenerator号段分配都通过原子加的方式，待分配完毕后重新申请新号段。

## Reference
flickr: http://code.flickr.net/blog/2010/02/08/ticket-servers-distributed-unique-primary-keys-on-the-cheap/<br>
twitter snowflake: https://github.com/twitter/snowflake<br>
instagram: http://instagram-engineering.tumblr.com/post/10853187575/sharding-ids-at-instagram<br>
