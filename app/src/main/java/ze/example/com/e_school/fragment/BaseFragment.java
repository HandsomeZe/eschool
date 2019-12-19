package ze.example.com.e_school.fragment;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import ze.example.com.e_school.Myapplication;

public class BaseFragment extends Fragment {
    private Activity activity;
    public Context getContext(){
        if (activity==null){
            return Myapplication.getInstance();
        }
        return activity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
    }
}

