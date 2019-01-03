package com.zhangyingwei.cockroach2.monitor.mvc.service;

import com.zhangyingwei.cockroach2.monitor.model.QueueModel;
import com.zhangyingwei.cockroach2.monitor.storage.StorageHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangyw
 * @date: 2019/1/3
 * @desc:
 */
public class QueueService {

    public Map getQueryInfo() {
        Map<String, Object> info = new HashMap<>();
        QueueModel queueModel = StorageHandler.getQueryModel();
        info.put("createtime", queueModel.getCreateTimestemp());
        info.put("initcalacify", queueModel.getCalacify());
        info.put("block", queueModel.getWithBlock());
        info.put("currentlength", queueModel.getSize());
        info.put("limit", queueModel.getLimit());
        info.put("outsize", queueModel.getOutSize());
        return info;
    }
}
