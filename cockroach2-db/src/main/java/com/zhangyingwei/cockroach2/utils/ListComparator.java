package com.zhangyingwei.cockroach2.utils;

import java.util.List;

public interface ListComparator<T> {
    boolean accept(List<T> olds,T newValue);
}
