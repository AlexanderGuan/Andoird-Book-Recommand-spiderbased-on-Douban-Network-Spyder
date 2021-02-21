package com.example.testact.http;

import java.util.List;


public class ListUtils {

    public static int getSize(List<?> list) {
        return list == null ? 0 : list.size();
    }

    public static <V> boolean addDistinctEntry(List<V> sourceList, V entry) {
        return sourceList != null && !sourceList.contains(entry) ? sourceList.add(entry) : false;
    }

}
