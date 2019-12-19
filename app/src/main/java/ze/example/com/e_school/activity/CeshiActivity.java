package ze.example.com.e_school.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ze.example.com.e_school.R;
import ze.example.com.e_school.asynctask.GetAsyncTask;
import ze.example.com.e_school.asynctask.PostAsyncTask;
import ze.example.com.e_school.constant.Constant;

public class CeshiActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText user;
    private EditText password;
    private Button btm;
    private TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ceshi);
        init();
    }


    private void init() {
        user = findViewById(R.id.ceshi_account);
        password = findViewById(R.id.ceshi_password);
        btm = findViewById(R.id.ceshi_btm);
        result = findViewById(R.id.ceshi_result);
        btm.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ceshi_btm:
                    query("studentClass","计应1731");
                    //注册
                break;
        }
    }


    private void register(String account, String password) {
        String registerUrlStr = Constant.URL_Register + "?account=" + account + "&password=" + password;
        new GetAsyncTask(result).execute(registerUrlStr);
    }

    private void login(String account, String password) {
        String registerUrlStr = Constant.URL_Login + "?account=" + account + "&password=" + password;
        new GetAsyncTask(result).execute(registerUrlStr);
    }

    private void query(String flag,String value){
        String queryUrlStr = Constant.URL_Query+"?"+flag +"=" +value;
        new GetAsyncTask(result).execute(queryUrlStr);
    }

}
