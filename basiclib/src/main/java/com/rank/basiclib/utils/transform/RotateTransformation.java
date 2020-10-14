package com.rank.basiclib.utils.transform;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/12/14
 *     desc  :
 * </pre>
 */
public class RotateTransformation extends BitmapTransformation {
    private static final String ID = "com.rank.basiclib.utils.transform.RotateTransformation";
    private static final byte[] ID_BYTES = ID.getBytes();

    private float rotateRotationAngle;

    public RotateTransformation(float rotateRotationAngle) {

        this.rotateRotationAngle = rotateRotationAngle;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        Matrix matrix = new Matrix();
        if (toTransform.getWidth() > toTransform.getHeight()) {
            matrix.postRotate(rotateRotationAngle);
        }
        return Bitmap.createBitmap(toTransform, 0, 0, toTransform.getWidth(), toTransform.getHeight(), matrix, true);
    }


    @Override
    public boolean equals(Object o) {
        return o instanceof RotateTransformation;
    }

    @Override
    public int hashCode() {
        return ID.hashCode();
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);
    }
}