package com.curry.stephen.lcandroidlib.net;

import android.content.res.XmlResourceParser;

import com.curry.stephen.lcandroidlib.utils.ClassSecurityOperationHelper;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

/**
 * Mobile API配置管理工具类。<br/>
 * 在第一次访问该类时会自动加载指定xml文件中的所有Mobile API信息，随后再访问该xml中的Mobile API时会从之前已经加载的信息中获取。<br/>
 * Mobile API的xml文件参考在lcandroidlib\res\xml\mobileapiurl.xml示例文件。<br/>
 * 目前一个使用UrlConfigManager类只能管理一个Mobile API xml文件。
 * @see com.curry.stephen.lcandroidlib.net.URLData
 * @author Stephen Curry
 * @since lcandroidlib 0.1
 */
public class UrlConfigManager {

    private static ArrayList<URLData> mUrlDataList;

    public static ArrayList<URLData> getmUrlDataList() {
        return mUrlDataList;
    }

    public static URLData getURLData(XmlResourceParser xmlResourceParser, String requestKey) {
        if (mUrlDataList != null && mUrlDataList.size() > 0) {
            for (URLData item : mUrlDataList) {
                if (item.getKey().equals(requestKey)) {
                    return item;
                }
            }
            return null;
        } else {
            return getUrlDataFromXml(xmlResourceParser, requestKey);
        }
    }

    private static URLData getUrlDataFromXml(XmlResourceParser xmlResourceParser, String requestKey) {
        try {
            int eventType = xmlResourceParser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (xmlResourceParser.getName().equals("node")) {
                            xmlResourceParser.next();
                            String key = xmlResourceParser.getText();
                            if (key.equals(requestKey)) {
                                URLData urlData = new URLData();
                                urlData.setKey(key);
                                xmlResourceParser.next();
                                urlData.setExpires(ClassSecurityOperationHelper.convertToLong(xmlResourceParser.getText(), 0));
                                xmlResourceParser.next();
                                urlData.setNetType(xmlResourceParser.getText());
                                xmlResourceParser.next();
                                urlData.setUrl(xmlResourceParser.getText());
                                xmlResourceParser.next();
                                urlData.setMockClass(xmlResourceParser.getText());
                                mUrlDataList.add(urlData);
                            }
                        }
                        break;
                    default:
                        break;
                }
                eventType = xmlResourceParser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            xmlResourceParser.close();
        }
        return null;
    }
}

