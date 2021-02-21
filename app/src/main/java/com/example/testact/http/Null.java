package com.example.testact.http;

import android.widget.EditText;
import android.widget.TextView;

public class Null {

    /**
     * 判断是否为空
     *
     * @param editText 编辑控件
     * @return
     */
    public static boolean isNull(EditText editText) {
        if (editText == null) {
            return true;
        }
        if (editText.getText().toString().length() == 0) {
            return true;
        }
        if (editText.getText().toString().equals("null")) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否为空
     *
     * @param textView 显示控件
     * @return
     */
    public static boolean isNull(TextView textView) {
        if (textView == null) {
            return true;
        }
        if (textView.getText().toString().length() == 0) {
            return true;
        }
        if (textView.getText().toString().equals("null")) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否为空
     *
     * @param content 内容
     * @return
     */
    public static boolean isNull(String content) {
        if (content == null) {
            return true;
        }
        if (content.length() == 0) {
            return true;
        }
        if (content.equals("null")) {
            return true;
        }
        return false;
    }

    /**
     * 获取控件值
     *
     * @param textView 显示控件
     * @return
     */
    public static String value(TextView textView) {
        if (textView == null) {
            return "";
        }
        String content = textView.getText().toString();
        if (content.equals("null")) {
            return "";
        }
        if (content.length() == 0) {
            return "";
        }
        return textView.getText().toString();
    }

    /**
     * 获取控件值
     *
     * @param editText 编辑控件
     * @return
     */
    public static String value(EditText editText) {
        if (editText == null) {
            return "";
        }
        String content = editText.getText().toString();
        if (content.equals("null")) {
            return "";
        }
        if (content.length() == 0) {
            return "";
        }
        return editText.getText().toString();
    }

    /**
     * 判断数据
     *
     * @param content
     * @return
     */
    public static String value(String content) {
        if (content == null) {
            return "";
        }
        if (content.length() == 0) {
            return "";
        }
        if (content.equals("null")) {
            return "";
        }
        return content;
    }

}
