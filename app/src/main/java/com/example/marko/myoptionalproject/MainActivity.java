package com.example.marko.myoptionalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.marko.myoptionalproject.activity.ViewPagerActivity;
import com.example.marko.myoptionalproject.base.BaseActivity;


import butterknife.BindView;

public class MainActivity extends BaseActivity {


    @BindView(R.id.function_list)
    ListView functionList;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ArrayAdapter<String> funcAdapter = new ArrayAdapter<String>(this, R.layout.function_list_item,
                getResources().getStringArray(R.array.functions));
        functionList.setAdapter(funcAdapter);
    }

    @Override
    protected void initEvent() {
        functionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent intent = new Intent(getApplication(), ViewPagerActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
