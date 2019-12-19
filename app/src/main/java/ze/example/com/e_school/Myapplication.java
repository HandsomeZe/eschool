package ze.example.com.e_school;
import android.app.Application;
import android.content.Context;

public class Myapplication extends Application {
    private static Myapplication mInstance;
    /**
     * 获取context
     * @return
     */
    public static Context getInstance() {
        if (mInstance == null) {
            mInstance = new Myapplication();
        }
        return mInstance;
    }
}
