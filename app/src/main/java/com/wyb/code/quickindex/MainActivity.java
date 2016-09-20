package com.wyb.code.quickindex;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mLv;
    private TextView tvMsg;
    private static List<Friends> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        mLv = (ListView) findViewById(R.id.lv);
        QuickIndexBar mQuickBar = (QuickIndexBar) findViewById(R.id.quick_bar);
        tvMsg = (TextView) findViewById(R.id.tv_msg);

        setData();

        //调用排序的方法，需要集合中的泛型对象实现Comparable接口
        //从而就会自动完成排序
        Collections.sort(mList);

        MyAdapter adapter = new MyAdapter(this,mList);
        mLv.setAdapter(adapter);

        assert mQuickBar != null;
        mQuickBar.setOnGetTouchedLetter(new QuickIndexBar.OnGetTouchedLetterListener() {
            @Override
            public void onGetTouchedLetter(String letter) {
                //显示中间的TextView
                showText(letter);

                //设置listview滚动到当前点击的letter处
                for (int i = 0; i < mList.size();i++){
                    String currentLetter = String.valueOf(mList.get(i).getPinyin().charAt(0));
                    if (TextUtils.equals(letter,currentLetter)){
                        mLv.setSelection(i);
                        break;
                    }
                }
            }
        });
    }

    private Handler mHandler = new Handler();
    //显示中间TextView内容
    private void showText(String letter) {
        tvMsg.setVisibility(View.VISIBLE);
        tvMsg.setText(letter);

        //移除之前所有的Runnable和Message对象
        mHandler.removeCallbacksAndMessages(null);

        //显示两秒消失
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tvMsg.setVisibility(View.GONE);
            }
        },2000);
    }

    //填充数据源
    private void setData() {
        mList = new ArrayList<>();
        mList.add(new Friends("马三炮"));
        mList.add(new Friends("丑八怪"));
        mList.add(new Friends("黄大妈"));
        mList.add(new Friends("大仲马"));
        mList.add(new Friends("疯子"));
        mList.add(new Friends("国家安全局"));
        mList.add(new Friends("安道全"));
        mList.add(new Friends("孔老二"));
        mList.add(new Friends("阿牛"));
        mList.add(new Friends("黄小明"));
        mList.add(new Friends("吉林"));
        mList.add(new Friends("毕竟"));
        mList.add(new Friends("赵四"));
        mList.add(new Friends("胖子"));
        mList.add(new Friends("蒋光头"));
        mList.add(new Friends("摁地上"));
        mList.add(new Friends("龙四"));
        mList.add(new Friends("牛渣渣"));
        mList.add(new Friends("全家福"));
        mList.add(new Friends("人民币"));
        mList.add(new Friends("威武霸气"));
        mList.add(new Friends("偶像1"));
        mList.add(new Friends("特斯拉"));
        mList.add(new Friends("山炮"));
        mList.add(new Friends("王八"));
    }
}
