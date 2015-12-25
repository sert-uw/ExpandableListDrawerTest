package com.example.sert.navigationdrawersample;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        setNavigationItems();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void setNavigationItems() {
        // 親ノードのリスト
        List<Map<String, String>> parentList = new ArrayList<>();
        // 全体の子ノードのリスト
        List<List<Map<String, String>>> allChildList = new ArrayList<>();

        String[] parentArray = getResources().getStringArray(R.array.nav_parent);

        // 親ノードに表示する内容を生成
        for (String title : parentArray) {
            Map<String, String> parentData = new HashMap<>();
            parentData.put("title", title);
            parentList.add(parentData);
        }

        // 子ノードに表示する文字を生成
        for (int i = 0; i < parentList.size(); i++) {
            List<Map<String, String>> childList = new ArrayList<>();
            int resId = getResources().getIdentifier("nav_child" + i, "array", getPackageName());
            String[] childArray = getResources().getStringArray(resId);

            // 各子ノード用データ格納
            for (String title : childArray) {
                Map<String, String> childData = new HashMap<>();
                childData.put("title", title);
                childList.add(childData);
            }
            // 全体の子ノードリストに各小ノードリストのデータを格納
            allChildList.add(childList);
        }

        SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(
                this, parentList, android.R.layout.simple_expandable_list_item_1,
                new String[] { "title" }, new int[] { android.R.id.text1 },
                allChildList, android.R.layout.simple_expandable_list_item_2,
                new String[] { "title" }, new int[] { android.R.id.text1 });

        ExpandableListView expandableListView
                = (ExpandableListView) findViewById(R.id.expandableListView);

        //生成した情報をセット
        expandableListView.setAdapter(adapter);

        // リスト項目がクリックされた時の処理
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View view,
                                        int groupPosition, int childPosition, long id) {
                ExpandableListAdapter adapter = parent
                        .getExpandableListAdapter();

                // クリックされた場所の内容情報を取得
                Map<String, String> item = (Map<String, String>) adapter
                        .getChild(groupPosition, childPosition);

                // トーストとして表示
                Toast.makeText(
                        getApplicationContext(),
                        "child clicked " + item.get("title"), Toast.LENGTH_LONG)
                        .show();

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);

                return false;
            }
        });
    }
}
