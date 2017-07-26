## 快速入门
### 1. 硬编码workId
```
    IdGenerator idGenerator = new IdGeneratorBuilder()
            .workId(1L)
            .epoch(1480521600000L)
            .build();

    long uid = idGenerator.getUid();
    String extra = idGenerator.parseUid(uid);
    System.out.println(extra);
```

### 2.使用Zookeeper获取workId
```
    IdGenerator idGenerator = new IdGeneratorBuilder()
                .zkAddress("localhost:2181")
                .namespace("/myapp/uid/worker")
                .epoch(1480521600000L)
                .build();

    long uid = idGenerator.getUid();
    String extra = idGenerator.parseUid(uid);
    System.out.println(extra);

```