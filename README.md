# cockroach2

[![](https://travis-ci.org/zhangyingwei/cockroach2.svg?branch=master)](https://travis-ci.org/zhangyingwei/cockroach2)
[![](https://img.shields.io/badge/language-java-orange.svg)]()
[![](https://img.shields.io/badge/jdk-1.8-green.svg)]()
[![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

> 对 [cockraoch](https://github.com/zhangyingwei/cockroach) 进行完全重构，在之前一些设计不足的方面进行了补充，完成了一直以来心中所想的一个爬虫框架的一些基础功能。

![demo-image](docs/images/cockroach-demo.png)


## 简介

cockroach2 是 [cockraoch](https://github.com/zhangyingwei/cockroach) 的完全重构版本，因为架构设计的原因以及代码写着写着就头脑发热的原因个，cockroach在开发的过程中做了一些本不该它做的事情，同样也有一部分本该它做的事情做不到，所以犹豫了好久干脆重写了，犹豫的原因是舍不得之前那百十来个小星星，后来实在是忍不下去了，干脆一了百了，咬咬牙重头再来。

没错，还是一个小巧、灵活、健壮的内容（pa）获取（chong）框架。

简单到什么程度呢，仍然是几句话就可以创建一个内容（pa）获取（chong）程序。

## 快速开始

### 依赖部分

```xml
<repositories>
  <repository>
    <id>github</id>
    <name>GitHub OWNER Apache Maven Packages</name>
    <url>https://maven.pkg.github.com/zhangyingwei/cockroach2</url>
  </repository>
</repositories>
```

```xml
<dependency>
    <groupId>com.zhangyingwei</groupId>
    <artifactId>cockroach2-core</artifactId>
    <version>0.0.7</version>
</dependency>
```

### 代码部分：

```java
public class CockroachContextTest {
    public static void main(String[] args) {
        CockroachConfig config = new CockroachConfig();
        CockroachContext context = new CockroachContext(config);
        QueueHandler queueHandler = new QueueHandler.Builder().withBlock(false).build();
        queueHandler.add(new Task("http://zhangyingwei.com"));
        context.start(queueHandler);
    }
}
```

没错，就是这么简单。这个程序就是获取 `http://zhangyingwei.com` 这个页面的内容并将结果打印出来。
在结果处理这个问题上，程序中默认使用 PringStore 这个类将所有结果打印出来。

### 监测部分

代码中通过监听器把一系列操作以日志的形式发送到日志队列。消费日志队列即可得到程序的运行情况。添加监控程序的日志消费者，即可开启监控界面。

#### 添加监控程序日志消费者

```text
CockroachConfig config = new CockroachConfig().addLogConsumer(CockroachMonitorConsumer.class);
```

![web-main](docs/images/main.png)

![web-task](docs/images/task.png)

具体实例可以参考： [samples](cockroach2-samples/src/main/java/com/zhangyingwei/cockroach2/samples)

## 变化

花了这么大的精力进行重写，到底与第一版有什么区别呢？

* 结构更加清晰
* 执行器的生命周期管理更加清晰
* 执行器管理更加智能化
    * 举个例子： 如果任务队列因为任务数量过多阻塞了，而所有的执行器都因需要提交任务到队列中而被消息队列挡在门外候着，这时程序就会陷入一个死亡宁静中。 而这个时候，程序就会提交一种叫零时工的任务处理器来缓解这种情况。当然这只是一种情况，其他的留在文档里写吧。
* 增加了生命周期日志
* 增加了日志订阅功能，可以通过订阅日志，获取程序的运行情况，从而自己实现一些功能。

## 非常重要！！！

写文档真的是太烦了！

介于api跟第一版本没有太大的出入，可以先参考第一版的文档。

如果不行的话，就加我微信吧！

抽时间再写文档。

## 联系方式
* 邮箱： zhangyw001@gmail.com
* 微信： fengche361

## Lisence

Lisenced under [Apache 2.0 lisence](./LICENSE)
