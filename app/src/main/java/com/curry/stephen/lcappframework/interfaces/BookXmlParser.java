package com.curry.stephen.lcappframework.interfaces;

import com.curry.stephen.lcappframework.entity.BookBean;

import java.io.InputStream;
import java.util.List;

/**
 * Created by Administrator on 2017/1/28.
 */

public interface BookXmlParser {

    /**
     * 解析输入流，得到Book列表。
     * @param inputStream Xml文件输入流。
     * @return Book列表。
     */
    List<BookBean> parse(InputStream inputStream) throws Exception;

    /**
     * 将Book列表序列号为Xml字符串形式。
     * @param bookBeanBeanList Book列表。
     * @return Xml字符串。
     */
    String serialize(List<BookBean> bookBeanBeanList) throws Exception;
}
