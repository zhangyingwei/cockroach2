package com.zhangyingwei.cockroach2.monitor.msg;

import com.zhangyingwei.cockroach2.common.Constants;
import com.zhangyingwei.cockroach2.common.utils.IdUtils;
import com.zhangyingwei.cockroach2.monitor.msg.consumer.CockroachTaskConsumer;
import com.zhangyingwei.cockroach2.monitor.msg.consumer.DefaultCockroachConsumer;
import com.zhangyingwei.cockroach2.monitor.msg.consumer.ICMsgConsumer;
import com.zhangyingwei.cockroach2.monitor.msg.producer.CockroachMsgProducer;
import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Slf4j
public class LogMsgHandler {
    private Map<String,BlockingQueue<Msg>> queueMap = new ConcurrentHashMap<String, BlockingQueue<Msg>>();
    private CockroachMsgProducer producer = new CockroachMsgProducer(queueMap);
    private List<ICMsgConsumer> consumers = new ArrayList<ICMsgConsumer>();
    private ExecutorService service = Executors.newCachedThreadPool((runnable) -> {
        Thread thread = new Thread(runnable);
        thread.setName(Constants.THREAD_NAME_MOINTOR_CONSUMER + IdUtils.getId(LogMsgHandler.class.getName()));
        return thread;
    });
    private Boolean run = true;

    public LogMsgHandler() {}

    public void produce(Msg msg) {
        producer.produce(msg);
    }

    public void registerConsumer(ICMsgConsumer consumer) {
        consumers.add(consumer);
        service.execute(() -> {
            try {
                BlockingQueue<Msg> queue = new ArrayBlockingQueue<Msg>(Constants.MSG_QUEUE_SIZE);
                queueMap.put(consumer.getGroup(), queue);
                while (run) {
                    Msg msg = queue.poll(5,TimeUnit.SECONDS);
                    if (msg != null) {
                        if (consumer.acceptGroup(msg.getGroup())) {
                            consumer.consusmer(msg);
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public Boolean isTerminated() {
        return service.isTerminated();
    }

    public void shutdown() {
        try {
            for (BlockingQueue<Msg> queue : this.queueMap.values()) {
                while (queue.size() > 0) {
                    TimeUnit.SECONDS.sleep(1);
                }
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }finally {
            run = false;
            service.shutdown();
            log.debug("log msg handler shutdown!");
        }
    }
}
