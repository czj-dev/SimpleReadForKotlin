package com.rank.basiclib.utils;

import android.graphics.Color;
import android.text.Editable;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import org.xml.sax.Attributes;

import java.util.HashMap;

/**
 * 自定义标签解析类，处理：呵呵呵<span style="{color:#e60012}">哈哈哈</span>嘿嘿嘿
 * Created by Administrator on 2016/3/29.
 */
public class SpanTagHandler implements HtmlTagHandler.TagHandler {

    private int fontColor = 0;
    private int startIndex = 0;
    private int endIndex = 0;

    @Override
    public void handleTag(boolean open, String tag, Editable output, Attributes attrs) {

        if (tag.toLowerCase().equals("span")) {
            if (open) {
                //开标签，output是空（sax还没读到），attrs有值
                for (int i = 0; i < attrs.getLength(); i++) {
                    if (attrs.getLocalName(i).equals("style")) {
                        String style = attrs.getValue(i); //{color:#e60012}
                        String[] params = style.split(";");
                        HashMap<String, String> map = new HashMap<>();
                        for (String param : params) {
                            String[] values = param.split(":");
                            if (values.length == 2) {
                                map.put(values[0], values[1]);
                            }
                        }
                        if (map.containsKey("color")) {
                            fontColor = Color.parseColor(map.get("color").replace(" ", ""));
                        }
//                        fontColor = style.replace("{", "").replace("}", "").replace("color", "").replace(":", "");
                    }
                }
                startIndex = output.length();
            } else {
                //闭标签，output有值了，attrs没值
                endIndex = output.length();
                output.setSpan(new ForegroundColorSpan(fontColor), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }
}