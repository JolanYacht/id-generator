package com.bytebeats.id.generator.redis;

public class IdGenerator {

	private int retryTimes;

	public long next(String tag) {
		return next(tag, 0);
	}

	public long next(String tag, long shardId) {
		for (int i = 0; i < retryTimes; ++i) {
			Long id = innerNext(tag, shardId);
			if (id != null) {
				return id;
			}
		}
		throw new RuntimeException("Can not generate id!");
	}

	Long innerNext(String tag, long shardId) {
		System.currentTimeMillis();
		return null;
	}

	public static long buildId(long milliseconds, long shardId,
							   long seq) {

		return (milliseconds << 22) + (shardId << 10) + seq;
	}

	static public IdGeneratorBuilder builder() {
		return new IdGeneratorBuilder();
	}

	static class IdGeneratorBuilder {

		public IdGeneratorBuilder logicShards(int num) {
			return this;
		}
		public IdGeneratorBuilder addHost(String host, int port) {

			return this;
		}

		public IdGenerator build() {
			return new IdGenerator();
		}
	}
}
