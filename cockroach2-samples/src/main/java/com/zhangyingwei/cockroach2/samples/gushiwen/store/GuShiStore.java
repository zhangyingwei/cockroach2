package com.zhangyingwei.cockroach2.samples.gushiwen.store;

import com.zhangyingwei.cockroach2.common.Task;
import com.zhangyingwei.cockroach2.core.store.IStore;
import com.zhangyingwei.cockroach2.session.response.CockroachResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangyw
 * @date: 2019/1/9
 * @desc:
 */
public class GuShiStore implements IStore {
    @Override
    public void store(CockroachResponse response) {
        if (response.isGroupStartWith("gs.author")) {
            if (response.isGroup("gs.author")) {
                String sumPage = response.select("#sumPage").text();
                if (response.getTask().getData() != null) {
                    for (int i = 1; i < Integer.parseInt(sumPage); i++) {
                        Map<String, String> params = response.getTask().getData();
                        Task task = new Task("https://so.gushiwen.org/authors"+params.get("preffix").concat("A").concat(Integer.parseInt(params.get("page")) + i + "").concat(params.get("suffix")), "gs.author");
                        response.getQueue().add(task);
                    }
                } else {
                    response.select(".sons > .cont").stream().forEach(element -> {
                        String title = element.select("p > a > b").text();
                        String content = element.select(".contson").text();
                        System.out.println("-------------------");
                        System.out.println(title);
                        System.out.println(content);
                        System.out.println("-------------------");
                    });
                }
            }
        } else {
            response.select(".sonspic").stream()
                    .map(element -> element.select(".cont > p > a:nth-child(1)").attr("href"))
                    .forEach(href -> {
                        String realHref = "https://so.gushiwen.org/authors" + href.replace("_", "sw_").replace(".aspx", "A1.aspx");
                        Task task = new Task(realHref,response.getTask().getGroup().concat(".author"));
                        Map params = new HashMap();
                        params.put("page", "1");
                        params.put("preffix", href.replace("_", "sw_").replace(".aspx",""));
                        params.put("suffix", ".aspx");
                        task.setData(params);
                        response.getQueue().add(task);
                    });
        }
    }

    @Override
    public void faild(CockroachResponse response) {
        System.out.println("task faild");
    }
}
