package ze.example.com.e_school.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.util.List;
import ze.example.com.e_school.R;
import ze.example.com.e_school.activity.StudentDetailActivity;
import ze.example.com.e_school.constant.Student;



public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {
    private List<Student> mStudentList;
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView studentName;
        //TextView studentID;
        TextView studentClass;
        Button reviseButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            studentName = itemView.findViewById(R.id.rv_student_name);
            studentClass = itemView.findViewById(R.id.rv_student_Class);
            //studentID = itemView.findViewById(R.id.rv_student_ID);
            reviseButton = itemView.findViewById(R.id.rv_buttom);
        }
    }

    public StudentAdapter(List<Student> list,Context context) {
        this.context = context;
        mStudentList = list;
    }
    /***
     *
     * **/
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.student_item,viewGroup,false);
        final ViewHolder holder = new ViewHolder(view);
//        holder.reviseButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //打开学生详情页面 并且将Student数据传过去
//
//            }
//        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        final int a = i;
        Student student = mStudentList.get(i);
        //viewHolder.studentID.setText(student.getmID());
        viewHolder.studentClass.setText(student.getmClass());
        viewHolder.studentName.setText(student.getmName());
        viewHolder.reviseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),StudentDetailActivity.class);
                intent.putExtra("studentName",mStudentList.get(a).getmName());
                intent.putExtra("studentClass",mStudentList.get(a).getmClass());
                intent.putExtra("studentID",mStudentList.get(a).getmID());
                intent.putExtra("position",String.valueOf(a));
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mStudentList.size();
    }

}
