package com.wyb.code.quickindex;

import android.support.annotation.NonNull;

/**
 * Created by ${kissfoot} on 2016/9/19.
 */
public class Friends implements Comparable<Friends>{

    private String name;
    private String pinyin;

    public Friends(String name) {
        this.name = name;
        //在此处直接将name转换成对应的拼音
        this.pinyin = PinYinUtil.getPinYin(name);
    }

    public String getName() {
        return name;
    }

    public String getPinyin() {
        return pinyin;
    }

    //如果返回值<0:表示当前值<another的值;
    //如果返回值>0:表示当前值>another的值;
    //如果返回值=0:表示当前值=another的值.
    @Override
    public int compareTo(@NonNull Friends friends) {
        return this.pinyin.compareTo(friends.getPinyin());
    }
}
