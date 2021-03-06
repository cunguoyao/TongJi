package com.linkage.tongji;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.linkage.lib.SwipeBackLayout;
import com.linkage.mapview.adapter.provinceAdapter;
import com.linkage.mapview.bean.MyMap;
import com.linkage.mapview.bean.MycolorArea;
import com.linkage.mapview.util.ColorChangeHelp;
import com.linkage.mapview.util.SvgUtil;
import com.linkage.mapview.view.ColorView;
import com.linkage.mapview.view.MyMapView;
import com.linkage.tongji.bean.IndexReport;
import com.linkage.tongji.bean.User;
import com.linkage.utils.LogUtils;
import com.linkage.utils.NetRequest;
import com.linkage.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = MapActivity.class.getName();

    private TextView leftBtn;
    private MyMapView mapview;
    private provinceAdapter adapter;
    private MyMap myMap;
    private Button changeType;
    private ColorView colorView;
    private int currentColor = 0;
    private ListView province_listview;
    private HashMap<String, List<MycolorArea>> colorView_hashmap;
    private List<String> list;
    private ArrayList<IndexReport> indexReports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_map);
//        getSwipeBackLayout().setSwipeMode(SwipeBackLayout.FULL_SCREEN_LEFT);
        setSwipeBackEnable(false);
        setTitle("统计");
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        indexReports = (ArrayList<IndexReport>)bundle.getSerializable("IndexReportList");
        initView();
        //设置颜色渐变条
        setColorView();
        //初始化map
        initMap();
        //初始化map各省份颜色
//        ColorChangeHelp.changeMapColors(myMap,ColorChangeHelp.nameStrings[0]);
        ColorChangeHelp.setMapClors(myMap,indexReports);
        mapview.chingeMapColors();
        //listview
        setListAdapter();
        changeType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String namestring = ColorChangeHelp.nameStrings[++currentColor % ColorChangeHelp.nameStrings.length];
                changeType.setText(namestring);
                colorView.setList(colorView_hashmap.get(namestring));
                //重置map各省份颜色
                ColorChangeHelp.changeMapColors(myMap, namestring);
                mapview.chingeMapColors();
            }
        });
        mapview.setOnChoseProvince(new MyMapView.onProvinceClickLisener() {
            @Override
            public void onChose(String provincename) {
                //地图点击省份回调接口，listview滚动到相应省份位置
                for (int i = 0; i < indexReports.size(); i++) {
                    IndexReport info=(IndexReport)indexReports.get(i);
                    if (info.getProvinceName().contains(provincename)) {
                        adapter.setPosition(i);
                        province_listview.setSelection(i);
                        break;
                    }
                }
            }
        });
        fetchUser();
    }
    private void setListAdapter() {
        list=new ArrayList<>();
        //最后三个是香港，澳门和台湾，不需要
        for (int i=0;i<ColorChangeHelp.province_datas.length;i++){
            list.add(ColorChangeHelp.province_datas[i]);
        }
        adapter = new provinceAdapter(this, indexReports);
        province_listview.setAdapter(adapter);
        province_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                IndexReport item = adapter.getItem(i);
                Intent intent = new Intent();
                intent.setClass(MapActivity.this, MenuActivity.class);
                intent.putExtra("provinceId", item.getProvinceId());
                intent.putExtra("provinceName", item.getProvinceName());
                startActivity(intent);
            }
        });
    }

    private void initView() {
        leftBtn = (TextView) findViewById(R.id.title_back);
        province_listview = (ListView) findViewById(R.id.province_listview);
        mapview = (MyMapView) findViewById(R.id.view);
        colorView = (ColorView) findViewById(R.id.colorView);
        changeType = (Button) findViewById(R.id.changeType);
        //changeType.setVisibility(View.GONE);
        changeType.setText(ColorChangeHelp.nameStrings[0]);
        leftBtn.setText("注销");
        leftBtn.setVisibility(View.VISIBLE);
        leftBtn.setCompoundDrawables(null, null, null, null);
        leftBtn.setOnClickListener(this);
    }
    /**
     * 设置颜色渐变条
     */
    private void setColorView() {
        colorView_hashmap = new HashMap<>();
        for (int i = 0; i < ColorChangeHelp.nameStrings.length; i++) {
            String colors[] = ColorChangeHelp.colorStrings[i].split(",");
            String texts[] = ColorChangeHelp.textStrings[i].split(",");
            List<MycolorArea> list = new ArrayList<>();
            for (int j = 0; j < colors.length; j++) {
                MycolorArea c = new MycolorArea();
                c.setColor(Color.parseColor(colors[j]));
                c.setText(texts[j]);
                list.add(c);
            }
            colorView_hashmap.put(ColorChangeHelp.nameStrings[i], list);
        }
        colorView.setList(colorView_hashmap.get(ColorChangeHelp.nameStrings[0]));
    }

    private void initMap() {
        //拿到SVG文件，解析成对象
        myMap = new SvgUtil(this).getProvinces();
        //设置缩放最大和最小倍数
        mapview.setMaxScale(3);
        mapview.setMinScale(1);
        //传数据
        mapview.setMap(myMap);
    }

    private void fetchUser() {
        User user = getAccount();
        LogUtils.d("---user=" + user.getUserName());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                SharedPreferencesUtils.getInstance(this, "report-client").setObject("assemble_", "");
                Intent intent = new Intent(this, LoginActivity.class);
                intent.putExtra("logout", 1);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetRequest.cancelRequest(TAG);
    }
}
