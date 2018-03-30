package com.linkage.tongji;

import android.os.Bundle;
import android.text.TextUtils;

import com.joanzapata.pdfview.PDFView;
import com.linkage.lib.SwipeBackLayout;

import java.io.File;

/**
 * Created by cunguoyao on 2018/3/30.
 */

public class PdfReaderActivity extends BaseActivity {

    private PDFView pdfView;
    private String pdfPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_reader);
//        getSwipeBackLayout().setSwipeMode(SwipeBackLayout.FULL_SCREEN_LEFT);
        setSwipeBackEnable(true);
        pdfView = (PDFView) findViewById(R.id.pdfview);
        pdfPath = getIntent().getStringExtra("pdf_path");
        if(TextUtils.isEmpty(pdfPath)){
            finish();
            return;
        }
        File file = new File(pdfPath);
        if(file.isFile() && file.exists()) {
            //加载assets下的文件
            pdfView.fromFile(file)
                    //.fromFile("")指定加载某个文件
                    //指定加载某一页
              /*.pages(0, 1,2, 3, 4, 5)*/
                    .defaultPage(1)
                    .showMinimap(false)
                    .enableSwipe(true)
             /* .onDraw(onDraw)
                .onLoad(onLoadCompleteListener)
                .onPageChange(onPageChangeListener)*/
                    .load();
        }else {
            finish();
        }
    }
}
