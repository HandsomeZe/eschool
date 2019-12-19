package ze.example.com.e_school.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ze.example.com.e_school.R;
import ze.example.com.e_school.SlideRecyclerView;
import ze.example.com.e_school.adapter.StudentAdapter;
import ze.example.com.e_school.constant.Student;

public class QueryResultActivyty extends AppCompatActivity {
    //创建适配器时传入的学生集合
    private List<Student> studentList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.queryresult_activity);
        Intent intent = getIntent();
        String mJSON = intent.getStringExtra("result");
        Log.w("戴","JSON数据为"+mJSON);
        //test
        parseJSONData(mJSON);
        SlideRecyclerView recyclerView = (SlideRecyclerView) findViewById(R.id.recycle_view);
       // RecyclerView recyclerView = findViewById(R.id.recycle_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        StudentAdapter adapter =new StudentAdapter(studentList,this);
        recyclerView.setAdapter(adapter);

    }


    private void parseJSONData(String jsonData){
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0 ; i < jsonArray.length();i++){
                JSONObject object = jsonArray.getJSONObject(i);
                Student student = new Student(object.getString("id"),object.getString("name"),object.getString("class"));
                studentList.add(student);
            }
        } catch (JSONException e) {
            Log.w("戴",e.toString());
        }
    }
}
