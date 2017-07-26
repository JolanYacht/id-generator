## 快速入门
### 1. 硬编码workId
```
    IdGeneratorFactory factory = new DefaultIdGeneratorFactory();
    
    IdGenerator idGenerator = factory.createIdGenerator(new DefaultWorkerIdAssigner(1L), 1480521600000L);
    long uid = idGenerator.getUid();
    String extra = idGenerator.parseUid(uid);
    System.out.println(extra);
```

### 2.使用Zookeeper获取workId
```
    IdGeneratorFactory factory = new DefaultIdGeneratorFactory();
    IdGenerator idGenerator = factory.createIdGenerator(new ZookeeperWorkerIdAssigner("localhost:2181", "/juice/uid/worker"), 1480521600000L);
    
    long uid = idGenerator.getUid();
    String extra = idGenerator.parseUid(uid);
    System.out.println(extra);

```