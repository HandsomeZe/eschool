package ze.example.com.e_school.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import ze.example.com.e_school.R;
import ze.example.com.e_school.activity.QueryResultActivyty;
import ze.example.com.e_school.constant.Constant;

public class InquireFragment extends BaseFragment {
    Context context ;
    private EditText queryValue;
    private AutoCompleteTextView limit;
    private Button button;
    private String[] array = {"Name","ID","Class"};
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Log.w("戴","返回到handle的数据"+msg.obj.toString());
                    //跳转到结果页面
                    if (msg.obj.toString().trim().equals("[]")){
                        Toast.makeText(getContext(),"没有查询到数据",Toast.LENGTH_SHORT).show();
                    }else {
                        Intent intent = new Intent(getContext(),QueryResultActivyty.class);
                        intent.putExtra("result",msg.obj.toString());
                        getContext().startActivity(intent);
                    }

                    break;
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.inquire_fragment,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        queryValue = getView().findViewById(R.id.edit_query_value);
        limit = getView().findViewById(R.id.quire_limit);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line,array);
        limit.setAdapter(adapter);
        limit.setThreshold(0);
        limit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                AutoCompleteTextView view1 = (AutoCompleteTextView) view;
                if (b){
                    view1.showDropDown();
                }
            }
        });
        limit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limit.showDropDown();
            }
        });
        button = getView().findViewById(R.id.submit_query);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String queryString="null";
                if (limit.getText().toString().trim().equals("Class")){
                    queryString="?studentClass="+queryValue.getText().toString().trim();
                }else if(limit.getText().toString().trim().equals("ID")){
                    queryString="?studentID="+queryValue.getText().toString().trim();
                }else if(limit.getText().toString().trim().equals("Name")){
                    queryString="?studentName="+queryValue.getText().toString().trim();
                }
                Log.w("戴","url请求地址为:"+Constant.URL_Query+queryString);

                //http请求
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(Constant.URL_Query+queryString)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }
                    //response 只能使用一次
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()){
                            Log.w("戴",response.code()+"");
                            String result = response.body().string();
                            Log.w("戴","返回数据"+result);


                            Message message = new Message();
                            message.what=1;
                            message.obj = result;
                            handler.sendMessage(message);
                        }
                    }
                });

            }
        });
    }
}