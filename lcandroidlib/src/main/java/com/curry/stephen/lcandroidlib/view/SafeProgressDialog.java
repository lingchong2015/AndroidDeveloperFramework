package com.curry.stephen.lcandroidlib.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Administrator on 2017/2/22.
 */

public class SafeProgressDialog extends ProgressDialog {

    private Activity mActivity;

    public SafeProgressDialog(Context context) {
        super(context);
        mActivity = (Activity) context;
    }

    @Override
    public void dismiss() {
        if ((mActivity != null) && !mActivity.isFinishing()) {
            super.dismiss();
        }
    }
}
