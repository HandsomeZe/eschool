package ze.example.com.e_school.asynctask;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.cazaea.sweetalert.SweetAlertDialog;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class PostAsyncTask extends AsyncTask {
    private String param;
    Handler mHandler;
    public PostAsyncTask(String param,Handler handler){
        this.param=param;
        this.mHandler=handler;
    }
    @Override
    protected Object doInBackground(Object[] objects) {
        Log.w("戴","posttask doinBackground");
        HttpURLConnection connection = null;
        StringBuilder respeose = new StringBuilder();
        try {
            //创建一个url实例对象
            URL url =new URL((String) objects[0]);
            //获取connection 实例对象
            connection = (HttpURLConnection) url.openConnection();
            //用HttpURLconnection的实例对象Connection设置请求方式  "GET"  OR  "POST"
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(2000);
            connection.setReadTimeout(2000);
            Log.w("戴",url.toString());
            //输入流之前写入post参数

            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.writeBytes(this.param);
            //获取输入流
            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line=reader.readLine())!=null){
                respeose.append(line);
            }
        } catch (MalformedURLException e) {
            Log.w("戴1",e.toString());
        } catch (ProtocolException e) {
            Log.w("戴2",e.toString());
        } catch (IOException e) {
            Log.w("戴3",e.toString());
        }
        return respeose.toString();
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
