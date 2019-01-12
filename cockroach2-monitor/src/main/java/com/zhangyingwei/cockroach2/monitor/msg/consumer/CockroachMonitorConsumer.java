package com.zhangyingwei.cockroach2.monitor.msg.consumer;

import com.zhangyingwei.cockroach2.db.CockroachDb;
import com.zhangyingwei.cockroach2.monitor.MonitroServer;
import com.zhangyingwei.cockroach2.monitor.msg.Msg;
import com.zhangyingwei.cockroach2.utils.ListComparator;

import java.util.Comparator;
import java.util.List;

public class CockroachMonitorConsumer implements ICMsgConsumer {
    private CockroachDb cockroachDb = new CockroachDb();
    private MonitroServer monitroServer = new MonitroServer(cockroachDb);

    public CockroachMonitorConsumer() {
        this.monitroServer.start();
    }

    @Override
    public void consusmer(Msg msg) {
        if (msg.getGroup().equals(Msg.Group.TASK)) {
            this.taskConsumer(msg);
        } else if (msg.getGroup().equals(Msg.Group.EXECUTOR)) {
            this.executorConsumer(msg);
        }
    }

    private void executorConsumer(Msg msg) {
        switch (msg.getMsgOf(Msg.Keys.EXECUTOR_ACTION)+"") {
            case "start": {
                this.cockroachDb.accumulator("executorCount");
                this.cockroachDb.accumulator("executorStart");
//                this.cockroachDb.replaceInList("executor", msg, new Comparator<Msg>() {
//                    @Override
//                    public int compare(Msg old, Msg newMsg) {
//                        if (old.getName().equals(newMsg.getName())) {
//                            return 0;
//                        }
//                        return -1;
//                    }
//                });
                break;
            }
            case "stop": {
                this.cockroachDb.subtract("executorStart");
                this.cockroachDb.accumulator("executorEnd");
//                this.cockroachDb.replaceInList("executor", msg, new Comparator<Msg>() {
//                    @Override
//                    public int compare(Msg old, Msg newMsg) {
//                        if (old.getName().equals(newMsg.getName())) {
//                            return 0;
//                        }
//                        return -1;
//                    }
//                });
                break;
            }
            case "execute":{
//                this.cockroachDb.replaceInList("executor", msg, new Comparator<Msg>() {
//                    @Override
//                    public int compare(Msg old, Msg newMsg) {
//                        if (old.getName().equals(newMsg.getName())) {
//                            return 0;
//                        }
//                        return -1;
//                    }
//                });
                break;
            }
            default: {
                break;
            }
        }
    }

    private void taskConsumer(Msg msg) {
        switch (msg.getMsgOf(Msg.Keys.TASK_ACTION)+"") {
            case "before": {
                this.cockroachDb.accumulator("taskCount");
                this.taskGroupInfoSave(msg);
                break;
            }
            case "execute" :{
                this.cockroachDb.accumulator("taskRunning");
                break;
            }
            case "success" :{
                this.cockroachDb.accumulator("taskSuccess");
                this.cockroachDb.subtract("taskRunning");
                break;
            }
            case "failed" :{
                this.cockroachDb.accumulator("taskFailed");
                this.cockroachDb.subtract("taskRunning");
                break;
            }
            default: {
                break;
            }
        }
    }

    private void taskGroupInfoSave(Msg msg) {
        this.cockroachDb.putInList("tasklist",msg);
    }

    @Override
    public boolean acceptGroup(Msg.Group group) {
        return true;
    }

    @Override
    public String getGroup() {
        return CockroachMonitorConsumer.class.getName();
    }
}
