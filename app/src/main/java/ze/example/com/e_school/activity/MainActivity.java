package ze.example.com.e_school.activity;

import android.content.Context;
import android.os.IBinder;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionsMenu;

import ze.example.com.e_school.R;
import ze.example.com.e_school.fragment.AddFragment;
import ze.example.com.e_school.fragment.DeleteFragment;
import ze.example.com.e_school.fragment.InquireFragment;
import ze.example.com.e_school.fragment.ReviseFragment;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private Fragment addFragment;
    private Fragment deleteFragment;
    private Fragment reviseFragment;
    private Fragment inquireFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        init();
        //初始化
        addFragment=new AddFragment();
        deleteFragment=new DeleteFragment();
        reviseFragment=new ReviseFragment();
        inquireFragment=new InquireFragment();
        replaceFragment(reviseFragment);
        final NavigationView navView = findViewById(R.id.nav_view);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.menu);
        }
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.add:
                        replaceFragment(addFragment);
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.detail:
                        replaceFragment(reviseFragment);
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.inquire:
                        replaceFragment(inquireFragment);
                        mDrawerLayout.closeDrawers();
                        break;
                }

                return true;
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return true;
    }

    void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }



    //FloatingActionButton的初始化
    void init(){
        com.getbase.floatingactionbutton.FloatingActionButton actionA = new com.getbase.floatingactionbutton.FloatingActionButton(getBaseContext());
        actionA.setTitle("Hide/Show Action above");
        actionA.setIcon(R.mipmap.cancel);
        actionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,"确认撤回?",Snackbar.LENGTH_SHORT).setAction("Do it", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this,"已撤回",Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });

        com.getbase.floatingactionbutton.FloatingActionButton actionB = new com.getbase.floatingactionbutton.FloatingActionButton(getBaseContext());
        actionB.setTitle("Hide/Show Action above");
        actionB.setIcon(R.mipmap.commit);
        actionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,"确认保存?",Snackbar.LENGTH_SHORT).setAction("Do it", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this,"提交成功",Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });
        FloatingActionsMenu floatMenu=findViewById(R.id.floatmenu);
        floatMenu.addButton(actionA);
        floatMenu.addButton(actionB);

    }

    /**
     * 点击非编辑区域收起键盘
     * 获取点击事件
     */
    @CallSuper
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() ==  MotionEvent.ACTION_DOWN ) {
            View view = getCurrentFocus();
            if (isShouldHideKeyBord(view, ev)) {
                hideSoftInput(view.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }
    /**
     * 判定当前是否需要隐藏
     */
    protected boolean isShouldHideKeyBord(View v, MotionEvent ev) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
            return !(ev.getX() > left && ev.getX() < right && ev.getY() > top && ev.getY() < bottom);
            //return !(ev.getY() > top && ev.getY() < bottom);
        }
        return false;
    }

    /**
     * 隐藏软键盘
     */
    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
//    @Override
//    public boolean onKeyDown(int keyCode,KeyEvent event){
//        if(keyCode==KeyEvent.KEYCODE_BACK)
//            return true;
//        return super.onKeyDown(keyCode, event);
//    }//屏蔽返回键
}
