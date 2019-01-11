package com.zhangyingwei.cockroach2.db;

import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.*;

/**
 * @author zhangyw
 * @date: 2019/1/11
 * @desc:
 */
public class CockroachDbTest {

    private CockroachDb cockroachDb = new CockroachDb();

    @Test
    public void accumulator() {
        cockroachDb.accumulator("task");
        Long acc = cockroachDb.getAcc("task");
        System.out.println(acc);
    }

    @Test
    public void put() {
        cockroachDb.put("executor","e1");
    }

    @Test
    public void put1() {
        cockroachDb.put("executor","e1");
        cockroachDb.put("executor","e2", new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                if (o1 != null) {
                    return -1;
                }
                return 1;
            }
        });
        System.out.println((String) cockroachDb.get("executor"));
        cockroachDb.put("executor","e3", new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                if (o1 != null) {
                    return 1;
                }
                return -1;
            }
        });
        System.out.println((String) cockroachDb.get("executor"));
    }

    @Test
    public void putInList() {
        cockroachDb.putInList("tasks","abcdefg");
        System.out.println(cockroachDb.getList("tasks"));
    }

    @Test
    public void getList() {
        for (int i = 0; i < 100; i++) {
            cockroachDb.putInList("tasks", i);
        }
        System.out.println(cockroachDb.getList("tasks"));
    }

    @Test
    public void getList1() {
        for (int i = 0; i < 100; i++) {
            cockroachDb.putInList("tasks", i);
        }
        System.out.println(cockroachDb.getList("tasks", new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        }));
    }

    @Test
    public void getList2() {
    }

    @Test
    public void getList3() {
    }
}