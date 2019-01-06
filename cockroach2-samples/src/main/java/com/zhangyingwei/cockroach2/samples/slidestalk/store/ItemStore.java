package com.zhangyingwei.cockroach2.samples.slidestalk.store;

import com.zhangyingwei.cockroach2.common.Task;
import com.zhangyingwei.cockroach2.core.store.IStore;
import com.zhangyingwei.cockroach2.session.response.CockroachResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

/**
 * @author zhangyw
 * @date: 2018/12/14
 * @desc:
 */
@Slf4j
public class ItemStore implements IStore {
    private IStore imageDowload = new ImgDowloadStore();
    @Override
    public void store(CockroachResponse response) {
        if (response.isGroup("ss.categories.item")) {
            String type = response.getTask().getData();
            String title = "D://books/".concat(type).concat("/").concat(
                    response.select(".slta-slide-introduce > .slta-intro-top > label").attr("title")
            );
            log.info("title: {}", title);
            response.select("#slide_div_box ul > li > img").stream()
                    .forEach(img -> {
                        String url = img.attr("data-src");
                        if (url == null || url.length() == 0) {
                            url = img.attr("src");
                        }
                        Task task = new Task(url, "ss.categories.item.imgs").lowerPriorityBy(response.getTask());
                        String[] urlItems = url.split("\\/");
                        task.setData(new HashMap<String,String>(){
                            {
                                put("name", title);
                                put("imgName", urlItems[urlItems.length-1]);
                            }
                        });
                        response.getQueue().add(task);
                    });
        } else {
            this.imageDowload.store(response);
        }
    }
}
