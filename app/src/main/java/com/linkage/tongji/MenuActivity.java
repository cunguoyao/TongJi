package com.linkage.tongji;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.linkage.adapter.MenuAdapter;
import com.linkage.shapeloading.LoadingView;
import com.linkage.tongji.bean.MenuBean;
import com.linkage.widget.MyGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cunguoyao on 2018/3/27.
 */

public class MenuActivity extends BaseActivity {

    private LoadingView loadingView;
    private MyGridView gridView;
    private MenuAdapter menuAdapter;
    private List<MenuBean> menuList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setSwipeBackEnable(true);
        loadingView = (LoadingView)findViewById(R.id.loadView);
        setTitle("菜单");
        gridView = (MyGridView)findViewById(R.id.gridView);
        fetchMenuData();
    }

    private void fetchMenuData() {
        menuList = new ArrayList<>();
        menuAdapter = new MenuAdapter(this, menuList);
        gridView.setAdapter(menuAdapter);
        loadingView.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingView.setVisibility(View.GONE);
                menuList.clear();
                MenuBean menuBean1 = new MenuBean();
                menuBean1.setId(1);
                menuBean1.setType(1);
                menuBean1.setIconUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1522209295917&di=9ebffb22e74323df1d98ac26424cd372&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F4afbfbedab64034fbd1ed115a5c379310b551dd5.jpg");
                menuBean1.setIconMsg("地市用户概况");
                MenuBean menuBean2 = new MenuBean();
                menuBean2.setId(2);
                menuBean2.setType(1);
                menuBean2.setIconUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1522209295917&di=9ebffb22e74323df1d98ac26424cd372&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F4afbfbedab64034fbd1ed115a5c379310b551dd5.jpg");
                menuBean2.setIconMsg("cp业务发展概况");
                MenuBean menuBean3 = new MenuBean();
                menuBean3.setId(3);
                menuBean3.setType(1);
                menuBean3.setIconUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1522209295917&di=9ebffb22e74323df1d98ac26424cd372&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F4afbfbedab64034fbd1ed115a5c379310b551dd5.jpg");
                menuBean3.setIconMsg("代理商用户概况");
                MenuBean menuBean4 = new MenuBean();
                menuBean4.setId(4);
                menuBean4.setType(1);
                menuBean4.setIconUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1522209295917&di=9ebffb22e74323df1d98ac26424cd372&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F4afbfbedab64034fbd1ed115a5c379310b551dd5.jpg");
                menuBean4.setIconMsg("app健康度");
                MenuBean menuBean5 = new MenuBean();
                menuBean5.setId(5);
                menuBean5.setType(1);
                menuBean5.setIconUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1522209295917&di=9ebffb22e74323df1d98ac26424cd372&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F4afbfbedab64034fbd1ed115a5c379310b551dd5.jpg");
                menuBean5.setIconMsg("详细统计报表");
                menuList.add(menuBean1);
                menuList.add(menuBean2);
                menuList.add(menuBean3);
                menuList.add(menuBean4);
                menuList.add(menuBean5);
                menuAdapter.notifyDataSetChanged();
            }
        }, 2000);
    }
}
