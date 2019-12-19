package ze.example.com.e_school.asynctask;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import java.net.HttpURLConnection;

public class AddAsyncTask extends AsyncTask {
    private String param;
    Handler mHandler;

    @Override
    protected Object doInBackground(Object[] objects) {
        //尝试使用okhttp










        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        Message msg = mHandler.obtainMessage();
        if (o!=null){
            msg.what = 1;
            msg.obj = o;
        }else {
            msg.what = 2;
        }
        mHandler.sendMessage(msg);
    }
}
