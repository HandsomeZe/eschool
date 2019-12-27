package ze.example.com.e_school.fragment;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import ze.example.com.e_school.R;
import ze.example.com.e_school.activity.MainActivity;
import ze.example.com.e_school.constant.Constant;
/****
 *    添加fragment   未测试
 * */
public class AddFragment extends BaseFragment {
    EditText stuName;
    EditText stuClass;
    EditText stuID;
    Handler  mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    String a = msg.obj.toString();
                    if (msg.obj.toString().trim().equals("200")){
                        Toast.makeText(getContext(),"added successfully",Toast.LENGTH_SHORT).show();
                    }else if (msg.obj.toString().trim().equals("300")){
                        Toast.makeText(getContext(),"failed",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getContext(),"Duplicate student ID",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 2:
                    break;
            }
        }
    };
    private MainActivity mActivity;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_fragment,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        stuName = getView().findViewById(R.id.et_add_name);
        stuClass = getView().findViewById(R.id.et_add_class);
        stuID = getView().findViewById(R.id.et_add_ID);
        init();
    }

    //FloatingActionButton的初始化
    void init(){
        com.getbase.floatingactionbutton.FloatingActionButton actionA = new com.getbase.floatingactionbutton.FloatingActionButton(getContext());
        actionA.setTitle("Hide/Show Action above");
        actionA.setIcon(R.mipmap.cancel);
        actionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,"Recall?",Snackbar.LENGTH_SHORT).setAction("Do it", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(view.getContext( ),"have recalled",Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });

        com.getbase.floatingactionbutton.FloatingActionButton actionB = new com.getbase.floatingactionbutton.FloatingActionButton(getContext());
        actionB.setTitle("Hide/Show Action above");
        actionB.setIcon(R.mipmap.commit);
        actionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,"Are you sure?",Snackbar.LENGTH_SHORT).setAction("Do it", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String param = "?studentName="
                                +stuName.getText().toString().trim()+"&studentID="
                                +stuID.getText().toString().trim()
                                +"&studentClass="
                                +stuClass.getText().toString().trim();
                        //http请求
                        //okhttp 设置post param值
                        OkHttpClient client = new OkHttpClient();
                        FormBody.Builder mBuild = new FormBody.Builder();
                        mBuild.add("studentID",stuID.getText().toString().trim())
                                .add("studentClass",stuClass.getText().toString().trim())
                        .add("studentName",stuName.getText().toString().trim());
                        RequestBody requestBodyPost = mBuild.build();
                        //创建Request对象
                        Request request = new Request.Builder()
                                .url(Constant.URL_Add)
                                .post(requestBodyPost)
                                .build();
                        //创建 call对象 参数是request请求对象
                        Call call = client.newCall(request);
                        //请求加入调度  重写回调方法
                        call.enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Log.w("戴",e.toString());
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                final String resCod = Objects.requireNonNull(response.body()).string();
                                final String resCod2 = String.valueOf(response.code());
                                Log.w("戴","resCode = "+resCod);
                                Log.w("戴","resCod2 = "+resCod2);
                                Message message = new Message();
                                message.obj = resCod;
                                if (resCod2.equals("200")){
                                    message.what = 1;
                                }else {
                                    message.what = 2;
                                }
                                mHandler.sendMessage(message);
                            }

                        });

                    }
                }).show();
            }
        });
        FloatingActionsMenu floatMenu=getView().findViewById(R.id.floatmenu);
        floatMenu.addButton(actionA);
        floatMenu.addButton(actionB);

    }


}