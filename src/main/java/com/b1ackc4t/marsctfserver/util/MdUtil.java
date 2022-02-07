package com.b1ackc4t.marsctfserver.util;

import org.pegdown.PegDownProcessor;

public class MdUtil {

    private static final int descrLength = 200;

    /**
     * 去除html代码中含有的标签
     * @param htmlStr
     * @return
     */
    private static String delHtmlTags(String htmlStr) {
        //定义script的正则表达式，去除js可以防止注入
        String scriptRegex="<script[^>]*?>[\\s\\S]*?<\\/script>";
        //定义style的正则表达式，去除style样式，防止css代码过多时只截取到css样式代码
        String styleRegex="<style[^>]*?>[\\s\\S]*?<\\/style>";
        //定义HTML标签的正则表达式，去除标签，只提取文字内容
        String htmlRegex="<[^>]+>";
        //定义空格,回车,换行符,制表符
        String spaceRegex = "\\s*|\t|\r|\n";

        // 过滤script标签
        htmlStr = htmlStr.replaceAll(scriptRegex, "");
        // 过滤style标签
        htmlStr = htmlStr.replaceAll(styleRegex, "");
        // 过滤html标签
        htmlStr = htmlStr.replaceAll(htmlRegex, "");
        // 过滤空格等
        htmlStr = htmlStr.replaceAll(spaceRegex, "");
        return htmlStr.trim(); // 返回文本字符串
    }
    /**
     * 获取HTML代码里的内容
     * @param htmlStr
     * @return
     */
    private static String getTextFromHtml(String htmlStr){
        //去除html标签
        htmlStr = delHtmlTags(htmlStr);
        //去除空格" "
        htmlStr = htmlStr.replaceAll(" ","");
        return htmlStr;
    }

    public static String mdToText(String mdContent){
        PegDownProcessor pdp = new PegDownProcessor(Integer.MAX_VALUE);
        String htmlContent = pdp.markdownToHtml(mdContent);
        return getTextFromHtml(htmlContent);
    }

    /**
     * 获取md文档的摘要
     * @param mdContext
     * @return
     */
    public static String getDescrByMd(String mdContext) {
        String plain = mdToText(mdContext);
        if (plain.length() <= descrLength) return plain;
        return plain.substring(0, descrLength);
    }

}
