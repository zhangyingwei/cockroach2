package com.zhangyingwei.cockroach2.samples.slidestalk.store;

import com.zhangyingwei.cockroach2.common.Task;
import com.zhangyingwei.cockroach2.core.store.IStore;
import com.zhangyingwei.cockroach2.session.response.CockroachResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

/**
 * @author zhangyw
 * @date: 2018/12/14
 * @desc:
 */
@Slf4j
public class ImgDowloadStore implements IStore {
    @Override
    public void store(CockroachResponse response) {
        log.info("download image...");
//        Map<String,String> datas = response.getTask().getData();
//        try {
//            String title = datas.get("name");
//            String imgName = datas.get("imgName");
//            byte[] bytes = response.getContent().bytes();
//            File dir = new File(title);
//            if (!dir.exists()) {
//                dir.mkdirs();
//            }
//            File img = new File(title.concat("/").concat(imgName));
//            img.createNewFile();
//            OutputStream out = new FileOutputStream(img);
//            out.write(bytes);
//            out.close();
//            log.info("save file: {}",title.concat("/").concat(imgName));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void faild(CockroachResponse response) {}
}
