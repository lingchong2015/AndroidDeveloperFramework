package com.curry.stephen.lcappframework.activity.main;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.curry.stephen.lcandroidlib.net.RequestAsyncTask;
import com.curry.stephen.lcandroidlib.utils.AndroidFileSystemHelper;
import com.curry.stephen.lcappframework.R;
import com.curry.stephen.lcappframework.base.AppBaseActivity;
import com.curry.stephen.lcappframework.engine.PullBookParser;
import com.curry.stephen.lcappframework.entity.BookBean;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class MainActivity extends AppBaseActivity {

    private TextView mTextViewInfo;

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);

        mTextViewInfo = (TextView) findViewById(R.id.text_view_info);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatingActionButtonOnClick(view);
            }
        });
    }

    @Override
    protected void loadData() {

    }

    private void testWebService() {
        RequestAsyncTask requestAsyncTask = new RequestAsyncTask() {
            @Override
            public void onSuccess(String content) {
                mTextViewInfo.setText(content);
            }

            @Override
            public void onFail(String errorMessage) {
                mTextViewInfo.setText(errorMessage);
            }
        };
        requestAsyncTask.execute("getJson", "http://www.weather.com.cn/data/sk/101010100.html");
//        requestAsyncTask.execute("http://www.weather.com.cn/data/login.api", "");
    }

    private void testXml() {
        try {
            InputStream inputStream = getAssets().open("book.xml");
            PullBookParser pullBookParser = new PullBookParser();
            List<BookBean> bookBeen = pullBookParser.parse(inputStream);
            StringBuilder stringBuilder = new StringBuilder();
            for (BookBean item : bookBeen) {
                stringBuilder.append(item.toString());
            }
            mTextViewInfo.setText(stringBuilder.toString());
            String booksXml = pullBookParser.serialize(bookBeen);

//            AndroidFileSystemHelper.print(null, this);

            String path = AndroidFileSystemHelper.getExternalAppPrivateDirectory(this).getAbsolutePath();
            String filePath = String.format("%s/%s", path, "book.xml");
            Log.i(TAG, filePath);
            OutputStream outputStream = new FileOutputStream(filePath);
            outputStream.write(booksXml.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void floatingActionButtonOnClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }
}
