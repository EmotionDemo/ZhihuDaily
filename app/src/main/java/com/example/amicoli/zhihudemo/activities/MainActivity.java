package com.example.amicoli.zhihudemo.activities;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.amicoli.zhihudemo.R;
import com.example.amicoli.zhihudemo.fragments.Fragment_main;
import com.example.amicoli.zhihudemo.fragments.Fragment_me;
import com.example.amicoli.zhihudemo.fragments.Fragment_message;
import com.example.amicoli.zhihudemo.utils.RippleUtils;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationItemView;
    private Fragment fragment_main = new Fragment_main();
    private Fragment fragment_message = new Fragment_message();
    private Fragment fragment_me = new Fragment_me();
    private Fragment mfrom = fragment_main;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTranslucentStatus();
        initBottomBar();
        addDefaultFragment();
    }

    private void initTranslucentStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }

    /**
     * frament 切换
     * */
    public void swtichContent(Fragment from,Fragment to){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(! to.isAdded()){
            fragmentTransaction.hide(from).add(R.id.fra_content,to).commit();
            Log.d("Current status","is not added");
        }else {
            fragmentTransaction.hide(from).show(to).commit();
        }
    }
    /**
     * 添加默认fragment
     * */
    public void addDefaultFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fra_content,fragment_main);
        fragmentTransaction.commit();
    }
    public void onSaveInstanceState(Bundle outState){
        //注释防止view重叠
        //super.onSaveInstanceState(outState,outPersistentState);
    }
    private void initBottomBar() {
        bottomNavigationItemView = (BottomNavigationView) findViewById(R.id.my_bottombar);
        bottomNavigationItemView.setBackground(RippleUtils.getRippleDrawable(this,bottomNavigationItemView));
        bottomNavigationItemView.setBackgroundColor(this.getResources().getColor(R.color.white));
        bottomNavigationItemView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.main_page:
                    swtichContent(mfrom,fragment_main);
                        mfrom = fragment_main;
                        break;
                    case R.id.message_page:
                        swtichContent(mfrom,fragment_message);
                        mfrom = fragment_message;
                        break;
                    case R.id.me_page:
                        swtichContent(mfrom,fragment_me);
                        mfrom = fragment_me;
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }
}
