package com.qianmo.xiaomiblue.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

/**
 * Created by wangjitao on 2017/3/6 0006.
 * E-Mail：543441727@qq.com
 * <p>
 * 模糊图片工具类
 */

public class BlurBitmapUtil {

    /**
     * Renderscript 的简单的使用步骤
     * 下面简单说一下使用的步骤，这也是官方文档中的说明：
     * 首先需要通过 Context 创建一个 Renderscript ；
     * 其次通过创建的 Renderscript 来创建一个自己需要的脚本( ScriptIntrinsic )，比如这里需要模糊，那就是 ScriptIntrinsicBlur ；
     * 然后至少创建一个 Allocation 类来创建、分配内存空间；
     * 接着就是对图像进行一些处理，比如说模糊处理；
     * 处理完成后，需要刚才的 Allocation 类来填充分配好的内存空间；
     * 最后可以选择性的对一些资源进行回收。
     */

    //图片等比缩放
    private static final float BITMAP_SCALE = 0.4f;

    /**
     * 模糊图片工具类
     *
     * @param context
     * @param image
     * @param blurRadius
     * @return
     */
    public static Bitmap blurBitmap(Context context, Bitmap image, float blurRadius) {
        //设置渲染模糊程度，25f是最大的模糊化度
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            //计算等比缩小的长宽度
            int width = Math.round(image.getWidth() * BITMAP_SCALE);
            int height = Math.round(image.getHeight() * BITMAP_SCALE);

            //将缩小后的图片作为预渲染的图片
            Bitmap inputBitmap = Bitmap.createScaledBitmap(image, width, height, false);
            //创建一张渲染后的输出图片
            Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

            //创建RenderScript内核对象
            RenderScript rs = RenderScript.create(context);
            //创建模糊效果的RenderScript工具对象
            ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

            //由于RenderScript并没有使用VM分配内存，所以需要使用Allocation来创建和分配内存空间
            //创建Allocation对象的时候其实内存使用的，需要使用copyto将数据填充
            Allocation allocationInput = Allocation.createFromBitmap(rs, inputBitmap);
            Allocation allocationOutput = Allocation.createFromBitmap(rs, outputBitmap);


            blurScript.setRadius(blurRadius);

            //设置blurscript对象的输入内存
            blurScript.setInput(allocationInput);
            //将输出数据保存到输入内存中
            blurScript.forEach(allocationOutput);

            //将数据填充到allocation中
            allocationOutput.copyTo(outputBitmap);
            return outputBitmap;
        } else {
            return null;
        }

    }
}