package ze.example.com.e_school.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.cazaea.sweetalert.SweetAlertDialog;
import com.rengwuxian.materialedittext.MaterialEditText;

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
import ze.example.com.e_school.constant.Constant;
import ze.example.com.e_school.constant.Student;

public class StudentDetailActivity extends AppCompatActivity {
    private MaterialEditText mName;
    private MaterialEditText mClass;
    private MaterialEditText mID;
    private BootstrapButton revise;
    private BootstrapButton delete;
    String studentID="";
    String studentName="";
    String studentClass="";
    String position = "";

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Toast.makeText(StudentDetailActivity.this,"Deleted successfully",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(StudentDetailActivity.this,QueryResultActivyty.class);
                    intent.putExtra("position",position);
                    //setResult(200,intent);
                    //startActivityForResult(intent,200);
                    finish();
                    break;
                case 2:
                    Toast.makeText(StudentDetailActivity.this,"Database connection failed",Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(StudentDetailActivity.this,"Revised sucessfully",Toast.LENGTH_SHORT).show();
                    finish();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_detail_activity);
        //获取数据
        Intent intent = getIntent();
        studentID = intent.getStringExtra("studentID");
        studentName = intent.getStringExtra("studentName");
        studentClass = intent.getStringExtra("studentClass");
        position = intent.getStringExtra("position");


        Log.w("戴","studentName = "+studentName+"studentID = "+studentID+"position = "+position);
        init();
    }

    private void init() {
        mName = findViewById(R.id.detail_name);
        mClass = findViewById(R.id.detail_class);
        mID = findViewById(R.id.detail_id);
        revise = findViewById(R.id.detail_revise);
        delete = findViewById(R.id.detail_delete);
        //传入初始数
        mName.setText(studentName);
        mClass.setText(studentClass);
        mID.setText(studentID);
        mName.setFocusableInTouchMode(false);
        mClass.setFocusableInTouchMode(false);
        mID.setFocusableInTouchMode(false);





        revise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.w("戴","in revise Click");
                //修改  1.判断Edit是否可编辑 2.可编辑便判断是否与Edit是否与原值相同 不同则修改操作 3. 不可编辑就令Edit可编辑
                //返回RecyclerView数据怎么更新呢(1.再进行一次http请求 2.直接修改json  3.多写几条数据演示的时候不被发现
                if (mName.isFocusableInTouchMode()&mClass.isFocusableInTouchMode()){
                    //http请求
                    new SweetAlertDialog(StudentDetailActivity.this,SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Are you sure?")
                            .setConfirmText("yes")
                            .setContentText("Won't be able to cover this data!")
                            .setCancelText("no")
                            .showCancelButton(true)
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    System.out.println("doing in cancelClick");
                                    sweetAlertDialog.cancel();
                                }
                            })
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {

                                    OkHttpClient client = new OkHttpClient();
                                    FormBody.Builder mBuild = new FormBody.Builder();

                                    if (!mName.getText().toString().trim().equals(studentName)){
                                        mBuild.add("studentName",mName.getText().toString().trim());
                                    }
                                    if (!mClass.getText().toString().trim().equals(studentClass)){
                                        mBuild.add("studentClass",mClass.getText().toString().trim());
                                    }

                                    //http请求 同时锁

                                    mBuild.add("studentID",mID.getText().toString().trim())
                                            ;
                                    RequestBody requestBodyPost = mBuild.build();
                                    //创建Request对象
                                    Request request = new Request.Builder()
                                            .url(Constant.URL_Revise)
                                            .post(requestBodyPost)
                                            .build();
                                    Call call = client.newCall(request);
                                    call.enqueue(new Callback() {
                                        @Override
                                        public void onFailure(Call call, IOException e) {
                                            Log.w("戴",e.toString());
                                        }

                                        @Override
                                        public void onResponse(Call call, Response response) throws IOException {
                                            String body = Objects.requireNonNull(response.body()).string();
                                            String code = String.valueOf(response.code());
                                            Message message = new Message();
                                            message.obj = body;
                                            if (!code.equals("200")){
                                                message.what=2;
                                            }else {
                                                message.what=3;
                                            }
                                            handler.sendMessage(message);

                                        }
                                    });



                                    mName.setFocusableInTouchMode(false);
                                    mClass.setFocusableInTouchMode(false);
                                    mName.setFocusable(false);
                                    mClass.setFocusable(false);
                                    sweetAlertDialog.cancel();
                                }
                            })
                            .show();
                }else {
                    mName.setFocusableInTouchMode(true);
                    mClass.setFocusableInTouchMode(true);
                }
            }
        });



        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //弹窗提示是否确认 确认则操作
                new SweetAlertDialog(StudentDetailActivity.this,SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setConfirmText("yes")
                        .setContentText("Won't be able to delete this data!")
                        .setCancelText("no")
                        .showCancelButton(true)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                //确定删除 根据ID
                                sweetAlertDialog.cancel();

                                //Http请求
                                Log.w("戴","okhttpdoing");
                                OkHttpClient client = new OkHttpClient();
                                FormBody.Builder mBuild = new FormBody.Builder();
                                mBuild.add("studentID",mID.getText().toString().trim());
                                RequestBody requestBodyPost = mBuild.build();
                                //创建Request对象
                                Request request = new Request.Builder()
                                        .url(Constant.URL_Delete)
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
                                        final String resCod = response.body().string();
                                        final String resCod2 = String.valueOf(response.code());
                                        Log.w("戴","resCode = "+resCod);
                                        Log.w("戴","resCod2 = "+resCod2);
                                        Message message = new Message();
                                        message.obj = resCod;
                                        if (!resCod2.equals("200")){
                                            //数据库连接失败
                                            message.what =2;
                                        }else {
                                            message.what =1;
                                        }
                                        handler.sendMessage(message);
                                    }

                                });
                            }
                        })
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                //无事发生
                                sweetAlertDialog.cancel();
                            }
                        })
                        .show();
            }
        });
    }
}
