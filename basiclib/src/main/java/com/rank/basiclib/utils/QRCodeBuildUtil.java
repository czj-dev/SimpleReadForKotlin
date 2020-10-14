package com.rank.basiclib.utils;

import android.graphics.*;
import android.text.TextUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * <pre>
 *     author: Windows XP
 *     time  : 2019/4/3
 *     desc  :
 * </pre>
 */
public class QRCodeBuildUtil {
    public static Bitmap createBarCode(String content, int width, int height) {
        return createBarCode(content, width, height, 0);
    }

    public static Bitmap createBarCode(String content, int width, int height, int textSize) {
        if (TextUtils.isEmpty(content)) {
            return null;
        }
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.MARGIN, 0);

        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.CODE_128, width, height, hints);
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * width + x] = 0xff000000;
                    } else {
                        pixels[y * width + x] = 0xffffffff;
                    }
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            if (textSize > 0) {
                bitmap = showContent(bitmap, content, textSize);
            }
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public static Bitmap createQRCode(String content, int width, int height, int logo) {
        if (TextUtils.isEmpty(content)) {
            return null;
        }
        try {
            Bitmap bitmapLogo = BitmapFactory.decodeResource(Utils.getApp().getResources(),logo);
            Bitmap scaleLogo = getScaleLogo(bitmapLogo, width, height);

            int offsetX = width / 2;
            int offsetY = height / 2;

            int scaleWidth = 0;
            int scaleHeight = 0;
            if (scaleLogo != null) {
                scaleWidth = scaleLogo.getWidth();
                scaleHeight = scaleLogo.getHeight();
                offsetX = (width - scaleWidth) / 2;
                offsetY = (height - scaleHeight) / 2;
            }
            Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            //容错级别
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            //设置空白边距的宽度
            hints.put(EncodeHintType.MARGIN, 0);
            BitMatrix bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (x >= offsetX && x < offsetX + scaleWidth && y >= offsetY && y < offsetY + scaleHeight) {
                        int pixel = 0;
                        if (scaleLogo != null) {
                            pixel = scaleLogo.getPixel(x - offsetX, y - offsetY);
                        }
                        if (pixel == 0) {
                            if (bitMatrix.get(x, y)) {
                                pixel = 0xff000000;
                            } else {
                                pixel = 0xffffffff;
                            }
                        }
                        pixels[y * width + x] = pixel;
                    } else {
                        if (bitMatrix.get(x, y)) {
                            pixels[y * width + x] = 0xff000000;
                        } else {
                            pixels[y * width + x] = 0xffffffff;
                        }
                    }
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(width, height,
                    Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Bitmap getScaleLogo(Bitmap logo, int width, int height) {
        if (logo == null) return null;
        Matrix matrix = new Matrix();
        float scaleFactor = Math.min(width * 1.0f / 5 / logo.getWidth(), height * 1.0f / 5 / logo.getHeight());
        matrix.postScale(scaleFactor, scaleFactor);
        return Bitmap.createBitmap(logo, 0, 0, logo.getWidth(), logo.getHeight(), matrix, true);
    }
    /**
     * 显示条形的内容
     *
     * @param barcodeBitmap 已生成的条形码的位图
     * @param content       条形码包含的内容
     * @param textSize      字体大小，单位sp
     * @return 返回生成的新条形码位图
     */
    private static Bitmap showContent(Bitmap barcodeBitmap, String content, int textSize) {
        if (TextUtils.isEmpty(content) || null == barcodeBitmap) {
            return null;
        }
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(DensityUtil.sp2px(textSize));
        paint.setTextAlign(Paint.Align.CENTER);
        int textWidth = (int) paint.measureText(content);
        Paint.FontMetrics fm = paint.getFontMetrics();
        int textHeight = (int) (fm.bottom - fm.top);
        float scaleRateX = barcodeBitmap.getWidth() * 1.0f / textWidth;
        if (scaleRateX < 1) {
            paint.setTextScaleX(scaleRateX);
        }
        int baseLine = barcodeBitmap.getHeight() + textHeight;
        Bitmap bitmap = Bitmap.createBitmap(barcodeBitmap.getWidth(), barcodeBitmap.getHeight() + 2 * textHeight, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas();
        canvas.drawColor(Color.WHITE);
        canvas.setBitmap(bitmap);
        canvas.drawBitmap(barcodeBitmap, 0, 0, null);
        canvas.drawText(content, barcodeBitmap.getWidth() >> 1, baseLine, paint);
        canvas.save();
        canvas.restore();
        return bitmap;
    }

}
