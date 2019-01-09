package com.zhangyingwei.cockroach2.common.utils;

import java.util.Random;

/**
 * @author zhangyw
 * @date: 2019/1/9
 * @desc:
 */
public class NameUtil {
    private static String[] names = {
            "Amalthea","Aphrodite","Apollo","Ares","Artemis","Athena","Bio","Circe","Clymene","Cratos","Dionysus","Glaucus","Hades","Hepheastus","Hera","Hermes","Hesperides","Iris","Leucothea","Menoetius","Pallas","Perse","Poseidon","Proteus","Scamander","Syrinx","Talos","Zeus"
    };
    private static Random nameRandom = new Random();
    public static String getName() {
        return names[nameRandom.nextInt(names.length)];
    }
}
