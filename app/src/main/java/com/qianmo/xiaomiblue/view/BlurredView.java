package com.qianmo.xiaomiblue.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.qianmo.xiaomiblue.R;
import com.qianmo.xiaomiblue.utils.BitmapUtil;
import com.qianmo.xiaomiblue.utils.BlurBitmapUtil;

import static android.R.attr.level;

/**
 * Created by wangjitao on 2017/3/6 0006.
 * E-Mail：543441727@qq.com
 */

public class BlurredView extends RelativeLayout {
    private Context mContext;

    //最大透明值
    private static final int ALPHA_MAX_VALUE = 255;
    //最大模糊度
    private static final float BLUR_RADIUS = 25f;

    //原图片
    private ImageView mOriginalImg;
    //模糊化后
    private ImageView mBlurredImg;

    private Bitmap mOriginalBitmap;
    private Bitmap mBlurredBitmap;

    //是否禁止使用高斯模糊
    private boolean isDisableBlurred;

    public BlurredView(Context context) {
        super(context);
        initView(context);
    }

    public BlurredView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        initAttr(context, attrs);
    }

    public BlurredView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initAttr(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BlurredView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
        initAttr(context, attrs);
    }

    /**
     * 当所有子View出现后 设置相关内容
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setImageView();
    }

    /**
     * 初始化View
     *
     * @param context
     */
    private void initView(Context context) {
        mContext = context;
        LayoutInflater.from(mContext).inflate(R.layout.blurredview, this);
        mOriginalImg = (ImageView) findViewById(R.id.blurredview_origin_img);
        mBlurredImg = (ImageView) findViewById(R.id.blurredview_blurred_img);
    }

    /**
     * 初始化属性
     *
     * @param context
     * @param attrs
     */
    private void initAttr(Context context, AttributeSet attrs) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BlurredView);
        Drawable drawable = typedArray.getDrawable(R.styleable.BlurredView_src);
        isDisableBlurred = typedArray.getBoolean(R.styleable.BlurredView_disableBlurred, false);
        typedArray.recycle();

        //虚化照片
        if (null != drawable) {
            mOriginalBitmap = BitmapUtil.drawableToBitmap(drawable);
            mBlurredBitmap = BlurBitmapUtil.blurBitmap(context, mOriginalBitmap, BLUR_RADIUS);
        }

        //是否可见
        if (!isDisableBlurred) {
            mBlurredImg.setVisibility(VISIBLE);
        }
    }


    /**
     * 以代码的方式添加待模糊的图片
     *
     * @param blurredBitmap 待模糊的图片
     */
    public void setBlurredImg(Bitmap blurredBitmap) {
        if (null != blurredBitmap) {
            mOriginalBitmap = blurredBitmap;
            mBlurredBitmap = BlurBitmapUtil.blurBitmap(mContext, blurredBitmap, BLUR_RADIUS);
            setImageView();
        }
    }

    /**
     * 以代码的方式添加待模糊的图片
     *
     * @param blurDrawable 待模糊的图片
     */
    public void setBlurredImg(Drawable blurDrawable) {
        if (null != blurDrawable) {
            mOriginalBitmap = BitmapUtil.drawableToBitmap(blurDrawable);
            mBlurredBitmap = BlurBitmapUtil.blurBitmap(mContext, mOriginalBitmap, BLUR_RADIUS);
            setImageView();
        }
    }

    /**
     * 填充ImageView
     */
    private void setImageView() {
        mBlurredImg.setImageBitmap(mBlurredBitmap);
        mOriginalImg.setImageBitmap(mOriginalBitmap);
    }

    /**
     * 设置模糊程度
     *
     * @param level 模糊程度, 数值在 0~100 之间.
     */
    @SuppressWarnings("deprecation")
    public void setBlurredLevel(int level) {
        //超过模糊级别范围 直接抛异常
        if (level < 0 || level > 100) {
            throw new IllegalStateException("No validate level, the value must be 0~100");
        }

        //禁用模糊直接返回
        if (isDisableBlurred) {
            return;
        }

        //设置透明度
        mOriginalImg.setAlpha((int) (ALPHA_MAX_VALUE - level * 2.55));
    }

    /**
     * 显示模糊图片
     */
    public void showBlurredView() {
        mBlurredImg.setVisibility(VISIBLE);
    }

    /**
     * 选择是否启动/禁止模糊效果
     *
     * @param isDisableBlurred 是否禁用模糊效果
     */
    @SuppressWarnings("deprecation")
    public void setBlurredable(boolean isDisableBlurred) {
        if (isDisableBlurred) {
            mOriginalImg.setAlpha(ALPHA_MAX_VALUE);
            mBlurredImg.setVisibility(INVISIBLE);
        } else {
            mBlurredImg.setVisibility(VISIBLE);
        }
    }

    /**
     * 禁用模糊效果
     */
    @SuppressWarnings("deprecation")
    public void disableBlurredView() {
        isDisableBlurred = true;
        mOriginalImg.setAlpha(ALPHA_MAX_VALUE);
        mBlurredImg.setVisibility(INVISIBLE);
    }

    /**
     * 启用模糊效果
     */
    public void enableBlurredView() {
        isDisableBlurred = false;
        mBlurredImg.setVisibility(VISIBLE);
    }
}
