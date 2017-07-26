# id-generator
[![License](https://img.shields.io/badge/license-Apache%202-green.svg)](https://www.apache.org/licenses/LICENSE-2.0) [![Release Version](https://img.shields.io/badge/release-0.1.0-red.svg)](https://github.com/TiFG/id-generator/releases) [![Build Status](https://travis-ci.org/TiFG/id-generator.svg?branch=master)](https://travis-ci.org/TiFG/id-generator)

## Overview
A Java implemented, Snowflake based unique ID generator.

## Snowflake
[Twitter Snowflake](https://github.com/twitter/snowflake) 生成的 unique ID 的组成 (由高位到低位):

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


## Requirements
The minimum requirements to run the quick start are:
* JDK 1.7 or above
* A java-based project management software like [Maven](https://maven.apache.org/) or [Gradle](http://gradle.org/)

## Quick Start

[简明教程](docs/wiki/cn_quick_start.md)