package com.wyb.code.quickindex;

import android.text.TextUtils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * Created by ${kissfoot} on 2016/9/19.
 */
public class PinYinUtil {

    //获取汉子对应拼音
    public static String getPinYin(String hanzi) {
        String pinyin = "";

        //当汉子为空的情况下返回“”；
        if (TextUtils.isEmpty(hanzi)){
            return pinyin;
        }

        //设置汉语拼音的输出转换格式
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        //将汉语拼音设置为大写格式
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        //设置汉语拼音的声调格式，此处不带声调
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

        //将汉字进行拆分
        char[] chars = hanzi.toCharArray();
        //遍历数组
        for (char aChar: chars){
            //如果名称中包含空格，则将空格忽略，比如：张 三
            if (Character.isWhitespace(aChar)){
                continue;
            }

            if (aChar > 127){
                //如果是汉子，因为每个汉字2个字节，所以汉子的长度肯定大于127位
                //故此根据字节长度简单判断是否汉字。

                try {
                    //此处返回的是拼音数组，是因为一个汉字可能是多音字，存在多个拼音
                    String[] pinyins = PinyinHelper.toHanyuPinyinStringArray(aChar,format);
                    if (pinyins == null){
                        //全角字符字节数也会大于127，所以此处的数组长度可能为0，所以需要进一步判断
                        pinyin += aChar;
                    }else {
                        //即使有多音字，也只取数组第一个
                        pinyin += pinyins[0];
                    }
                } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                    badHanyuPinyinOutputFormatCombination.printStackTrace();
                }
            }else {
                //半角字符字节数小于127。
                //如果不是汉子，则直接拼接字符串，如：张a三
                pinyin += aChar;
            }
        }
        return pinyin;
    }
}
