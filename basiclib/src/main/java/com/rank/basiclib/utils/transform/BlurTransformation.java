package com.rank.basiclib.utils.transform;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RSRuntimeException;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;

import java.security.MessageDigest;

import androidx.annotation.NonNull;

/**
 * <pre>
 *     author: Windows XP
 *     time  : 2019/4/24
 *     desc  :
 * </pre>
 */
public class BlurTransformation extends DIYBitmapTransformation {
    private final String TAG = "com.rank.basiclib.utils.transform.BlurTransformation";
    private static int MAX_RADIUS = 25;
    private static int DEFAULT_DOWN_SAMPLING = 4;

    private int radius;
    private int sampling;

    public BlurTransformation() {
        this(MAX_RADIUS, DEFAULT_DOWN_SAMPLING);
    }

    public BlurTransformation(int radius) {
        this(radius, DEFAULT_DOWN_SAMPLING);
    }

    public BlurTransformation(int radius, int sampling) {
        this.radius = radius;
        this.sampling = sampling;
    }

    @Override
    protected Bitmap transform(@NonNull Context context, @NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        int width = toTransform.getWidth();
        int height = toTransform.getHeight();
        int scaledWidth = width / sampling;
        int scaledHeight = height / sampling;
        Bitmap bitmap = pool.get(scaledWidth, scaledHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.scale(1 / (float) sampling, 1 / (float) sampling);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(toTransform, 0, 0, paint);
        return blurByRenderScript(context, bitmap, radius);
    }

    private Bitmap blurByRenderScript(Context context, Bitmap bitmap, int radius) throws RSRuntimeException {
        RenderScript rs = null;
        Allocation input = null;
        Allocation output = null;
        ScriptIntrinsicBlur blur = null;
        try {
            rs = RenderScript.create(context);
            rs.setMessageHandler(new RenderScript.RSMessageHandler());
            input = Allocation.createFromBitmap(rs, bitmap, Allocation.MipmapControl.MIPMAP_NONE,
                    Allocation.USAGE_SCRIPT);
            output = Allocation.createTyped(rs, input.getType());
            blur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

            blur.setInput(input);
            blur.setRadius(radius);
            blur.forEach(output);
            output.copyTo(bitmap);
        } finally {
            if (rs != null) {
                rs.destroy();
            }
            if (input != null) {
                input.destroy();
            }
            if (output != null) {
                output.destroy();
            }
            if (blur != null) {
                blur.destroy();
            }
        }
        return bitmap;
    }


    @Override
    public String toString() {
        return "BlurTransformation(radius=" + radius + ", sampling=" + sampling + ")";
    }


    /**
     * 如果每次截图命名都一致就只能一直变化，如果改成截图命名不一致这里就可以只用TAG来保持缓存
     */
//    @Override
//    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
//        messageDigest.update((TAG + System.currentTimeMillis()).getBytes(CHARSET));
//    }
    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(TAG.getBytes(CHARSET));
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof BlurTransformation;
    }

    @Override
    public int hashCode() {
        return TAG.hashCode();
    }
}
