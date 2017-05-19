package com.curry.stephen.lcandroidlib.net;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.curry.stephen.lcandroidlib.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 2017/2/13.
 */

public abstract class LCSimpleAsyncHttp extends LCAbstractAsyncHttp {

    public abstract void postJsonCallBack(int statusCode, Header[] headers, JSONObject jsonObject);

    public abstract void postJsonCallBack(int statusCode, Header[] headers, JSONArray response);

    public abstract void getJsonCallBack(int statusCode, Header[] headers, String responseString);

    public abstract void getJsonCallBack(int statusCode, Header[] headers, JSONObject response);

    public abstract void getJsonCallBack(int statusCode, Header[] headers, JSONArray response);

    public abstract void postJsonCallBack(int statusCode, Header[] headers, String responseString);

    public abstract void getFromCacheCallBack(String data);

    public abstract void getMockDataCallBack(String data);

    private WeakReference<Context> mContextWeakReference;
    private String TAG;

    public LCSimpleAsyncHttp(Context context) {
        this(context, null, null);
    }

    public LCSimpleAsyncHttp(Context context, String tag) {
        this(context, tag, null);
    }

    public LCSimpleAsyncHttp(Context context, String tag, String progressDialogText) {
        mContextWeakReference = new WeakReference<>(context);
        if (tag == null) {
            TAG = LCSimpleAsyncHttp.class.getSimpleName();
        } else {
            TAG = tag;
        }
        if (progressDialogText == null) {
            setProgressDialogText(mContextWeakReference.get().getString(R.string.loading_data));
        } else {
            setProgressDialogText(progressDialogText);
        }
    }

    public void setProgressDialogText(String text) {
        mProgressDialog = new ProgressDialog(mContextWeakReference.get());
        mProgressDialog.setMessage(text);
        mProgressDialog.setCancelable(false);
    }

    @Override
    public void successPostJsonCallBack(int statusCode, Header[] headers, JSONObject jsonObject) {
        Log.i(TAG, String.format("Request successfully, return from LCSimpleAsyncHttp.successPostJsonCallBack(int statusCode, Header[] headers, JSONObject jsonObject), result: %s", jsonObject.toString()));
        postJsonCallBack(statusCode, headers, jsonObject);
    }

    @Override
    public void failurePostJsonCallBack(int statusCode, Header[] headers, Throwable throwable, JSONObject jsonObject) {
        Log.i(TAG, String.format("Request failed, return from LCSimpleAsyncHttp.failurePostJsonCallBack(int statusCode, Header[] headers, Throwable throwable, JSONObject jsonObject), result: %s", jsonObject.toString()));
        processRequestError(statusCode);
    }

    @Override
    public void successPostJsonCallBack(int statusCode, Header[] headers, JSONArray response) {
        Log.i(TAG, String.format("Request successfully, return from LCSimpleAsyncHttp.successPostJsonCallBack(int statusCode, Header[] headers, JSONArray response), result: %s",response.toString()));
        postJsonCallBack(statusCode, headers, response);
    }

    @Override
    public void failurePostJsonCallBack(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
        Log.i(TAG, String.format("Request failed, return from LCSimpleAsyncHttp.failurePostJsonCallBack(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse), result: %s", errorResponse.toString()));
        processRequestError(statusCode);
    }

    @Override
    public void successGetJsonCallBack(int statusCode, Header[] headers, String responseString) {
        Log.i(TAG, String.format("Request successfully, return from LCSimpleAsyncHttp.successGetJsonCallBack(int statusCode, Header[] headers, String responseString), result: %s", responseString));
        getJsonCallBack(statusCode, headers, responseString);
    }

    @Override
    public void failureGetJsonCallBack(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        Log.i(TAG, String.format("Request failed, return from LCSimpleAsyncHttp.failureGetJsonCallBack(int statusCode, Header[] headers, String responseString, Throwable throwable), result: %s", responseString));
        processRequestError(statusCode);
    }

    @Override
    public void successGetJsonCallBack(int statusCode, Header[] headers, JSONObject response) {
        Log.i(TAG, String.format("Request successfully, return from LCSimpleAsyncHttp.successGetJsonCallBack(int statusCode, Header[] headers, JSONObject response), result: %s", response.toString()));
        getJsonCallBack(statusCode, headers, response);
    }

    @Override
    public void failureGetJsonCallBack(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
        Log.i(TAG, String.format("Request failed, return from LCSimpleAsyncHttp.failureGetJsonCallBack(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse), result: %s", errorResponse.toString()));
        processRequestError(statusCode);
    }

    @Override
    public void successGetJsonCallBack(int statusCode, Header[] headers, JSONArray response) {
        Log.i(TAG, String.format("Request successfully, return from LCSimpleAsyncHttp.successGetJsonCallBack(int statusCode, Header[] headers, JSONArray response), result: %s", response.toString()));
        getJsonCallBack(statusCode, headers, response);
    }

    @Override
    public void failureGetJsonCallBack(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
        Log.i(TAG, String.format("Request failed, return from LCSimpleAsyncHttp.failureGetJsonCallBack(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse), result: %s", errorResponse.toString()));
        processRequestError(statusCode);
    }

    @Override
    public void successPostJsonCallBack(int statusCode, Header[] headers, String responseString) {
        Log.i(TAG, String.format("Request successfully, return from LCSimpleAsyncHttp.successPostJsonCallBack(int statusCode, Header[] headers, String responseString), result: %s", responseString));
        postJsonCallBack(statusCode, headers, responseString);
    }

    @Override
    public void failurePostJsonCallBack(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        Log.i(TAG, String.format("Request failed, return from LCSimpleAsyncHttp.failurePostJsonCallBack(int statusCode, Header[] headers, String responseString, Throwable throwable), result: %s", responseString));
        processRequestError(statusCode);
    }

    @Override
    public void successGetFromCacheCallBack(String data) {
        Log.i(TAG, "Request successfully, return from LCSimpleAsyncHttp.successGetFromCacheCallBack.");
        getFromCacheCallBack(data);
    }

    @Override
    public void successGetMockDataCallBack(String data) {
        Log.i(TAG, "Request successfully, return from LCSimpleAsyncHttp.successGetMockDataCallBack.");
        getMockDataCallBack(data);
    }

    @Override
    public void failureGetMockDataCallBack(int statusCode) {
        Log.i(TAG, "Request failed, return from LCSimpleAsyncHttp.failureGetMockDataCallBack.");
        processRequestError(statusCode);
    }

    /**
     * HTTP: Status200– 服务器成功返回网页

     HTTP: Status404– 请求的网页不存在

     HTTP: Status503– 服务不可用

     ————————————————————————————————

     HTTP: Status 1xx(临时响应)

     ->表示临时响应并需要请求者继续执行操作的状态代码。

     详细代码及说明:

     HTTP: Status 100(继续)

     -> 请求者应当继续提出请求。 服务器返回此代码表示已收到请求的第一部分，正在等待其余部分。

     HTTP: Status 101(切换协议)

     -> 请求者已要求服务器切换协议，服务器已确认并准备切换。

     ——————————————————————————————————

     HTTP Status 2xx(成功)

     ->表示成功处理了请求的状态代码;

     HTTP Status 200(成功)

     -> 服务器已成功处理了请求。 通常，这表示服务器提供了请求的网页。

     HTTP Status 201(已创建)

     -> 请求成功并且服务器创建了新的资源。

     HTTP Status 202(已接受)

     -> 服务器已接受请求，但尚未处理。

     HTTP Status 203(非授权信息)

     -> 服务器已成功处理了请求，但返回的信息可能来自另一来源。

     HTTP Status 204(无内容)

     -> 服务器成功处理了请求，但没有返回任何内容。

     HTTP Status 205(重置内容)

     -> 服务器成功处理了请求，但没有返回任何内容。

     HTTP Status 206(部分内容)

     -> 服务器成功处理了部分 GET 请求。

     HTTP Status 3xx（重定向）

     ->这要完成请求，需要进一步操作。通常，这些状态码用来重定向。

     ——————————————————————————————————

     HTTP Status 300（多种选择）

     ->针对请求，服务器可执行多种操作。服务器可根据请求者 (user agent) 选择一项操作，或提供操作列表供请求者选择。

     HTTP Status 301（永久移动）

     ->请求的网页已永久移动到新位置。服务器返回此响应（对 GET 或 HEAD 请求的响应）时，会自动将请求者转到新位置。您应使用此代码告诉 Googlebot 某个网页或网站已永久移动到新位置。

     HTTP Status 302（临时移动）

     ->服务器目前从不同位置的网页响应请求，但请求者应继续使用原有位置来响应以后的请求。此代码与响应 GET 和 HEAD 请求的 301 代码类似，会自动将请求者转到不同的位置，但您不应使用此代码来告诉 Googlebot 某个网页或网站已经移动，因为 Googlebot 会继续抓取原有位置并编制索引。

     HTTP Status 303（查看其他位置）

     -> 请求者应当对不同的位置使用单独的 GET 请求来检索响应时，服务器返回此代码。对于除 HEAD 之外的所有请求，服务器会自动转到其他位置。

     HTTP Status 304（没有修改）

     ->自从上次请求后，请求的网页未修改过。服务器返回此响应时，不会返回网页内容。如果网页自请求者上次请求后再也没有更改过，您应将服务器配置为返回此响应（称为 If-Modified-Since HTTP 标头）。服务器可以告诉 Googlebot 自从上次抓取后网页没有变更，进而节省带宽和开销。

     HTTP Status 305（使用代理）

     -> 请求者只能使用代理访问请求的网页。如果服务器返回此响应，还表示请求者应使用代理。

     HTTP Status 307（使用代理）

     -> 服务器目前从不同位置的网页响应请求，但请求者应继续使用原有位置来响应以后的请求。此代码与响应 GET 和 HEAD 请求的 <a href=answer.py?answer=>301</a> 代码类似，会自动将请求者转到不同的位置，但您不应使用此代码来告诉 Googlebot 某个页面或网站已经移动，因为 Googlebot 会继续抓取原有位置并编制索引。

     ————————————————————————————————————

     HTTP Status 4xx(请求错误)

     ->这些状态代码表示请求可能出错，妨碍了服务器的处理。

     详细代码说明:

     HTTP Status 400（错误请求）

     ->服务器不理解请求的语法。

     HTTP Status 401（未授权）

     ->请求要求身份验证。 对于需要登录的网页，服务器可能返回此响应。

     HTTP Status 403（禁止）

     -> 服务器拒绝请求。

     HTTP Status 404（未找到）

     ->服务器找不到请求的网页。

     HTTP Status 405（方法禁用）

     ->禁用请求中指定的方法。

     HTTP Status 406（不接受）

     ->无法使用请求的内容特性响应请求的网页。

     HTTP Status 407（需要代理授权）

     ->此状态代码与 401（未授权）类似，但指定请求者应当授权使用代理。

     HTTP Status 408（请求超时）

     ->服务器等候请求时发生超时。

     HTTP Status 409（冲突）

     ->服务器在完成请求时发生冲突。 服务器必须在响应中包含有关冲突的信息。

     HTTP Status 410（已删除）

     -> 如果请求的资源已永久删除，服务器就会返回此响应。

     HTTP Status 411（需要有效长度）

     ->服务器不接受不含有效内容长度标头字段的请求。

     HTTP Status 412（未满足前提条件）

     ->服务器未满足请求者在请求中设置的其中一个前提条件。

     HTTP Status 413（请求实体过大）

     ->服务器无法处理请求，因为请求实体过大，超出服务器的处理能力。

     HTTP Status 414（请求的 URI 过长） 请求的 URI（通常为网址）过长，服务器无法处理。

     HTTP Status 415（不支持的媒体类型）

     ->请求的格式不受请求页面的支持。

     HTTP Status 416（请求范围不符合要求）

     ->如果页面无法提供请求的范围，则服务器会返回此状态代码。

     HTTP Status 417（未满足期望值）

     ->服务器未满足”期望”请求标头字段的要求。

     ———————————————————————————————————

     HTTP Status 5xx（服务器错误）

     ->这些状态代码表示服务器在尝试处理请求时发生内部错误。 这些错误可能是服务器本身的错误，而不是请求出错。

     代码详细及说明:

     HTTP Status 500（服务器内部错误）

     ->服务器遇到错误，无法完成请求。

     HTTP Status 501（尚未实施）

     ->服务器不具备完成请求的功能。 例如，服务器无法识别请求方法时可能会返回此代码。

     HTTP Status 502（错误网关）

     ->服务器作为网关或代理，从上游服务器收到无效响应。

     HTTP Status 503（服务不可用）

     -> 服务器目前无法使用（由于超载或停机维护）。 通常，这只是暂时状态。

     HTTP Status 504（网关超时）

     ->服务器作为网关或代理，但是没有及时从上游服务器收到请求。

     HTTP Status 505（HTTP 版本不受支持）

     -> 服务器不支持请求中所用的 HTTP 协议版本。
     */
    protected void processRequestError(int statusCode) {
        if (statusCode == 404) {
            Toast.makeText(mContextWeakReference.get(), "未找到请求资源,请查看网络连接地址是否设置正确.", Toast.LENGTH_LONG).show();
        } else if (statusCode == 500) {
            Toast.makeText(mContextWeakReference.get(), "服务器出现错误.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(mContextWeakReference.get(), String.format("未处理异常抛出,连接终止,错误代码:%d", statusCode), Toast.LENGTH_LONG).show();
        }
    }
}
