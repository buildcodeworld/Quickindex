package com.wyb.code.quickindex;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by ${kissfoot} on 2016/9/19.
 */
public class QuickIndexBar extends View {

    private Paint mPaint;
    //每个格子的宽和高
    private int cellWidth = 0;
    private int cellHeight = 0;
    //触摸到的字母的索引
    private int touchLetterIndex = -1;

    //字母表
    private String[] letters = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "G", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    //接口回调，获得触摸到的字母
    public interface OnGetTouchedLetterListener {
        void onGetTouchedLetter(String letter);
    }

    private OnGetTouchedLetterListener mListener;

    //回调方法
    public void setOnGetTouchedLetter(OnGetTouchedLetterListener listener) {
        this.mListener = listener;
    }

    public QuickIndexBar(Context context) {
        super(context);
        init();
    }


    public QuickIndexBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public QuickIndexBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //初始化参数
    private void init() {
        //生成画笔
        mPaint = new Paint();
        //设置画笔颜色，必须为6位哦
        mPaint.setColor(Color.WHITE);
        //设置字体格式
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        //文字大小
        mPaint.setTextSize(20);
        //抗锯齿
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (cellWidth == 0) {
            //获取view原始的宽度
            cellWidth = getMeasuredWidth();
        }

        if (cellHeight == 0) {
            //获取每个文字所在格子的高
            cellHeight = getMeasuredHeight()/letters.length;
        }

        for (int i = 0; i < letters.length; i++) {
            //mPaint.measureText(letters[i]);绘制的文本的宽度，measureText()方法只能返回绘制的文字的宽度，不能返回高度
            //文字的X点坐标
            float x = cellWidth / 2 - mPaint.measureText(letters[i]) / 2;
            //创建一个矩形
            Rect bounds = new Rect();
            //获取由调用者分配的最小的矩形文本范围
            mPaint.getTextBounds(letters[i], 0, letters[i].length(), bounds);
            //文字的Y点坐标
            float y = cellHeight / 2 + bounds.height() / 2 + i * cellHeight;

            //设置当前文字颜色
            mPaint.setColor(i == touchLetterIndex ? Color.parseColor("#FFCE43") : Color.parseColor("#FFFFFF"));
            //设置当前文字大小
            mPaint.setTextSize(i == touchLetterIndex ? 24 : 20);

            //根据坐标绘制出每个文字
            canvas.drawText(letters[i], x, y, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //获取屏幕坐标系的Y点的值
        //float y=event.getRawY();
        //获取触摸View坐标系时的Y点的值
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float currentIndex = y / cellHeight;
                if (isTheSameIndex(currentIndex)) {
                    break;
                }
                //得到当前触摸到的字母
                touchLetterIndex = (int) currentIndex;
                //回调触摸到的字母
                if (touchLetterIndex >= 0 && touchLetterIndex < letters.length) {
                    String letter = letters[touchLetterIndex];
                    if (mListener != null){
                        mListener.onGetTouchedLetter(letter);
                    }
                }

                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touchLetterIndex = -1;
                break;
        }

        //刷新视图
        invalidate();
        return true;
    }

    //判断是否与当前索引值一致，以此解决重复触摸字幕的bug
    private boolean isTheSameIndex(float currentIndex) {
        return touchLetterIndex == currentIndex;
    }
}
