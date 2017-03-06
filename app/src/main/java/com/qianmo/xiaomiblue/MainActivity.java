package com.qianmo.xiaomiblue;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.qianmo.xiaomiblue.adapter.MyAdapter;
import com.qianmo.xiaomiblue.utils.BitmapUtil;
import com.qianmo.xiaomiblue.utils.BlurBitmapUtil;
import com.qianmo.xiaomiblue.view.BlurredView;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;


public class MainActivity extends AppCompatActivity {

    private Context mContext = MainActivity.this;

    private ImageView imageView;
    private BlurredView blurredView;
    private RecyclerView recyclerView;
    private int mScrollerY;
    private int mAlpha;

    private int currentLevel;
    private int initLevel;
    private float downY;
    private float movePercent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        initView();
    }

    private void initView() {
//        imageView = (ImageView) findViewById(R.id.imageview);
//        blurredView = (BlurredView) findViewById(R.id.blurredView);
//        initLevel = 100;
//        blurredView.setBlurredLevel(initLevel);
//        Bitmap bitmap = getBlurBitmap();
//        imageView.setImageBitmap(bitmap);

        blurredView = (BlurredView) findViewById(R.id.blurredView);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(this));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mScrollerY += dy;
                //根据滚动距离控制模糊程度 滚动距离是模糊程度的十倍
                if (Math.abs(mScrollerY) > 1000) {
                    mAlpha = 100;
                } else {
                    mAlpha = Math.abs(mScrollerY) / 10;
                }
                //设置透明度等级
                blurredView.setBlurredLevel(mAlpha);
            }
        });
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                downY = event.getY();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                float moveY = event.getY();
//                //手指滑动距离
//                float offsetY = moveY - downY;
//                int screenY = getWindowManager().getDefaultDisplay().getHeight() * 10;
//                //手指滑动距离占屏幕的百分比
//                movePercent = offsetY / screenY;
//                currentLevel = initLevel + (int) (movePercent * 100);
//                if (currentLevel < 0) {
//                    currentLevel = 0;
//                }
//                if (currentLevel > 100) {
//                    currentLevel = 100;
//                }
//
//                //设置模糊度
//                blurredView.setBlurredLevel(currentLevel);
//                //更改初始模糊等级
//                initLevel = currentLevel;
//
//                break;
//            case MotionEvent.ACTION_UP:
//                break;
//        }
//        return super.onTouchEvent(event);
//    }

    /**
     * 实现沉浸式通知栏与导航栏
     *
     * @param hasFocus 是否有焦点
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    @SuppressWarnings({"ResourceType", "deprecation"})
    public Bitmap getBlurBitmap() {
        Bitmap bitmap = BitmapUtil.drawableToBitmap(getResources().getDrawable(R.raw.pic));
        return BlurBitmapUtil.blurBitmap(this, bitmap, 20f);
    }
}
