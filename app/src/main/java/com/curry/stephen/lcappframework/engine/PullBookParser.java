package com.curry.stephen.lcappframework.engine;

import android.util.Xml;

import com.curry.stephen.lcappframework.entity.BookBean;
import com.curry.stephen.lcappframework.interfaces.BookXmlParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/28.
 */

public class PullBookParser implements BookXmlParser {

    @Override
    public List<BookBean> parse(InputStream inputStream) throws Exception {
        List<BookBean> bookBeen = null;
        BookBean bookBean = null;

        XmlPullParser xmlPullParser = Xml.newPullParser();
        xmlPullParser.setInput(inputStream, "utf-8");

        int eventType = xmlPullParser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    bookBeen = new ArrayList<>();
                    break;
                case XmlPullParser.START_TAG:
                    if (xmlPullParser.getName().equals("bookBean")) {
                        bookBean = new BookBean();
                    } else if (xmlPullParser.getName().equals("id")) {
                        eventType = xmlPullParser.next();
                        bookBean.setId(Integer.parseInt(xmlPullParser.getText()));
                    } else if (xmlPullParser.getName().equals("name")) {
                        eventType = xmlPullParser.next();
                        bookBean.setName(xmlPullParser.getText());
                    } else if (xmlPullParser.getName().equals("price")) {
                        eventType = xmlPullParser.next();
                        bookBean.setPrice(Float.parseFloat(xmlPullParser.getText()));
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if (xmlPullParser.getName().equals("bookBean")) {
                        bookBeen.add(bookBean);
                        bookBean = null;
                    }
                    break;
            }
            eventType = xmlPullParser.next();
        }
        return bookBeen;
    }

    @Override
    public String serialize(List<BookBean> bookBeen) throws Exception {
        XmlSerializer xmlSerializer = Xml.newSerializer();
        StringWriter stringWriter = new StringWriter();
        xmlSerializer.setOutput(stringWriter);
        xmlSerializer.startDocument("utf-8", true);
        xmlSerializer.startTag("", "bookBeen");
        for (BookBean item : bookBeen) {
            xmlSerializer.startTag("", "book");
            xmlSerializer.attribute("", "id", String.valueOf(item.getId()));
            xmlSerializer.startTag("", "name");
            xmlSerializer.text(item.getName());
            xmlSerializer.endTag("", "name");
            xmlSerializer.startTag("", "price");
            xmlSerializer.text(String.valueOf(item.getPrice()));
            xmlSerializer.endTag("", "price");
            xmlSerializer.endTag("", "book");
        }
        xmlSerializer.endTag("", "bookBeen");
        xmlSerializer.endDocument();

        return stringWriter.toString();
    }
}
