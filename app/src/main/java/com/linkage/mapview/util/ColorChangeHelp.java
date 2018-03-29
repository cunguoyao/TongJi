package com.linkage.mapview.util;

import android.graphics.Color;
import android.util.Log;


import com.linkage.mapview.bean.MyMap;
import com.linkage.mapview.bean.Province;
import com.linkage.tongji.bean.IndexReport;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Vmmet on 2016/11/1.
 */
public class ColorChangeHelp {
        private static HashMap<String ,String> hashmap;
public static String province_datas[]={
        "河北省_-2.84_9.54_414_1090_431_1141" ,
        "上海市_-14.35_-6.65_288_965_345_1178" ,
        "江苏省_11.3_-6.26_407_1191_444_1293" ,
        "安徽省_-13.9_-9.1_411_1306_418_1332" ,
        "广西省_31.2_-33.13_250_705_256_719" ,
        "海南省_-22.71_-22.3_429_1171_469_1282" ,
        "云南省_14.46_-0.55_277_684_83_236" ,
        "陕西省_5.31_0.35_481_1322_513_1405" ,
        "甘肃省_88.44_27.21_224_681_302_923" ,
        "青海省_-13.9_-9.1_411_1306_418_1332" ,
        "新疆区_11.3_-6.26_407_1191_444_1293",

};
    public static String colorStrings[] = {"#D50D0D,#DC5754,#E98683,#F8CECF,#D3DFD5,#8DB093,#5E9361,#1C6620",
            "#D50D0D,#DC5754,#E98683,#F8CECF,#D3DFD5,#8DB093,#5E9361,#1C6620",
            "#E9EFF4,#D4E1EA,#BDD1DF,#A8C2D5,#92B3CA,#7DA4C0,#6794B5,#5185AB,#3B76A0",
            "#EAEFF5,#D5E0E9,#BFCFDE,#A6C0D8,#92B2CE,#7CA5C1,#6792B6,#4F85AE,#3E759B,#256796"};
    public static String textStrings[] = {"~-30,-30~-20,-20~-10,-10~0,0~10,10~20,20~30,30~",
            "~-30,-30~-20,-20~-10,-10~0,0~10,10~20,20~30,30~",
            "150~,200~,250~,300~,350~,400~,450~,500~,550~",
            "400~,500~,600~,700~," +
                    "800~,900~,1000~,1100~,1200~,1300~,"};
    public static String nameStrings[] = {"发电量增长率", "累计增长率","发电利用小时" ,"累计利用小时"};
    public static void changeMapColors(MyMap mymap, String type){
            if (hashmap==null){
             hashmap=new HashMap();
             for (int i=0;i<province_datas.length;i++){
                     hashmap.put(province_datas[i].split("_")[0],province_datas[i]);
             }
            }
            switch (type){
                    case "发电量增长率":
                        getMapColors(mymap,-30,10,0);
                       break;
                    case "累计增长率":
                        getMapColors(mymap,-30,10,1);
                            break;
                    case "发电利用小时":
                        getMapColors(mymap,150,50,2);
                            break;
                    case "累计利用小时":
                        getMapColors(mymap,400,100,3);
                            break;
            }

    }
    //最小值，之间间隔，type
    public static void getMapColors(MyMap mymap,float min,float average,int type){
        for (Province p:mymap.getProvinceslist()){
            if (hashmap.containsKey(p.getName())){
                if (hashmap.get(p.getName())!=null){
                    float a=Float.parseFloat( hashmap.get(p.getName()).split("_")[type+1]);
                    int b=(int)((a-min)/average+1);
                    if (b<=0){
                        b=0;
                    }else if (b>=textStrings[type].split(",").length-1){
                        b=textStrings[type].split(",").length-1;
                    }
                    Log.v("b====",p.getName()+b);
                    p.setColor(Color.parseColor(colorStrings[type].split(",")[b]));
                }
            }
        }
    }


    public static void setMapClors(MyMap mymap,ArrayList lists){
        hashmap=new HashMap();
        for (int i = 0; i < lists.size(); i++) {
            IndexReport info = (IndexReport)lists.get(i);

            hashmap.put(info.getProvinceName(),info.getColor());
        }

        for (Province p:mymap.getProvinceslist()){
            if (hashmap.containsKey(p.getName())){
                if (hashmap.get(p.getName())!=null){

                    p.setColor(Color.parseColor(hashmap.get(p.getName())));
                }
            }
        }
    }

}
