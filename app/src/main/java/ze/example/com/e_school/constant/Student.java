package ze.example.com.e_school.constant;

public class Student {
    String mID;
    String mName;
    String mClass;
    public Student(String id, String name, String mclass){
        this.mID=id;
        this.mName=name;
        this.mClass = mclass;
    }

    public String getmClass() {
        return mClass;
    }

    public String getmID() {
        return mID;
    }

    public String getmName() {
        return mName;
    }

    @Override
    public String toString() {
        String result = "StudentID:"+mID+"StudentName:"+mName+"StudentClass:"+mClass;
        return result;
    }
}
