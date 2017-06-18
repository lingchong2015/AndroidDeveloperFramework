package com.curry.stephen.lcappframework.activity.main;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.curry.stephen.lcandroidlib.utils.AndroidFileSystemHelper;
import com.curry.stephen.lcandroidlib.view.PieChart;
import com.curry.stephen.lcappframework.R;
import com.curry.stephen.lcappframework.base.AppBaseActivity;
import com.curry.stephen.lcappframework.engine.PullBookParser;
import com.curry.stephen.lcappframework.entity.BookBean;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class MainActivity extends AppBaseActivity implements View.OnTouchListener, GestureDetector.OnGestureListener {

    private TextView mTextViewInfo;

    private GestureDetector mGestureDetector;

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void initVariables() {
        mGestureDetector = new GestureDetector(this, this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);

        mTextViewInfo = (TextView) findViewById(R.id.text_view_info);
        mTextViewInfo.setClickable(true);
        mTextViewInfo.setOnTouchListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatingActionButtonOnClick(view);
            }
        });

//        final PieChart pie = (PieChart) this.findViewById(R.id.Pie);
//        pie.addItem("Agamemnon", 2, getColor(R.color.seafoam));
//        pie.addItem("Bocephus", 3.5f, getColor(R.color.chartreuse));
//        pie.addItem("Calliope", 2.5f, getColor(R.color.emerald));
//        pie.addItem("Daedalus", 3, getColor(R.color.bluegrass));
//        pie.addItem("Euripides", 1, getColor(R.color.turquoise));
//        pie.addItem("Ganymede", 3, getColor(R.color.slate));
    }

    @Override
    protected void loadData() {

    }

//    private void testWebService() {
//        RequestAsyncTask requestAsyncTask = new RequestAsyncTask() {
//            @Override
//            public void onSuccess(String content) {
//                mTextViewInfo.setText(content);
//            }
//
//            @Override
//            public void onFail(String errorMessage) {
//                mTextViewInfo.setText(errorMessage);
//            }
//        };
//        requestAsyncTask.execute("getJson", "http://www.weather.com.cn/data/sk/101010100.html");
////        requestAsyncTask.execute("http://www.weather.com.cn/data/login.api", "");
//    }

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

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.i(TAG, String.format("e1-x: %.2f, e1-y: %.2f, e2-x: %.2f, e2-y: %.2f, distance-x: %.2f, distance-y: %.2f",
                e1.getX(), e1.getY(), e2.getX(), e2.getY(), distanceX, distanceY));

        Log.i(TAG, String.format("vectorToScalarScroll: %.2f", vectorToScalarScroll(distanceX, distanceY, e2.getX(), e2.getY())));

        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.i(TAG, String.format("e1-x: %.2f, e1-y: %.2f, e2-x: %.2f, e2-y: %.2f, velocity-x: %.2f, velocity-y: %.2f",
                e1.getX(), e1.getY(), e2.getX(), e2.getY(), velocityX, velocityY));

        return true;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    private static float vectorToScalarScroll(float dx, float dy, float x, float y) {
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        float dotProduct = ((-y) * dx + x * dy);
        float sign = Math.signum(dotProduct);

        return sign * distance;
    }
}
