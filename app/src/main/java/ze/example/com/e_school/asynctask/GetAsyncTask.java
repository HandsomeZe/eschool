package ze.example.com.e_school.asynctask;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class GetAsyncTask extends AsyncTask {
    private TextView tv;
    public GetAsyncTask(TextView tv){
        this.tv = tv;
    }


    /**
     * @param objects 这里的params是一个数组，即AsyncTask在激活运行是调用execute()方法传入的参数
     */
    @Override
    protected Object doInBackground(Object[] objects) {
        Log.w("戴","task doinBackground");
        HttpURLConnection connection = null;
        StringBuilder respeose = new StringBuilder();
        try {
            //创建一个url实例对象
            URL url =new URL((String) objects[0]);
            //获取connection 实例对象
            connection = (HttpURLConnection) url.openConnection();
            //用HttpURLconnection的实例对象Connection设置请求方式  "GET"  OR  "POST"
            connection.setRequestMethod("GET");
            //设置超时间
            connection.setConnectTimeout(80000);
            //设置信息收发超时时间
            connection.setReadTimeout(80000);
            Log.w("戴",url.toString());
            //获取输入流
            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line=reader.readLine())!=null){
                respeose.append(line);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return respeose.toString();
    }



    @Override
    protected void onProgressUpdate(Object[] values) {
        //ui线程中执行 更新ui(handle)
    }

    //log打印
    @Override
    protected void onPreExecute() {
        Log.w("戴","task onPreExecute");
    }

    @Override
    protected void onPostExecute(Object o) {
        Log.w("戴","task onPostExecute");
    }


}
