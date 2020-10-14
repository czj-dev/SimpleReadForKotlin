package com.acar.share;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;

import java.io.File;

/**
 * Created by jks27 on 2018/5/24.
 */
public class ShareUtils {

    public static final String WE_CHAT_CIRCLE = "pengyouquan";
    public static final String WE_CHAT = "weixin";
    public static final String SINA = "weibo";
    public static final int BACKGROUND_COLOR = Color.parseColor("#fafafa");
    private Dialog dialog;

    private @interface ShareType {
    }

    private static volatile ShareUtils sInstance;

    private ShareBoardConfig shareBoardConfig;
    private static final String SHARE_TITLE_TEXT = "分享至";
    private static final String SHARE_CANCEL_TEXT = "取消";

    private ShareUtils() {
        initShareConfig();
    }


    public static ShareUtils getInstance() {
        if (sInstance == null) {
            synchronized (ShareUtils.class) {
                if (sInstance == null) {
                    sInstance = new ShareUtils();
                }
            }
        }
        return sInstance;
    }

    public void init(Application application) {
        UMConfigure.setLogEnabled(BuildConfig.DEBUG);
//        UMConfigure.init(application, "", "umeng", UMConfigure.DEVICE_TYPE_PHONE, null);
//        PlatformConfig.setWeixin("", "");
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
    }

    private void initShareConfig() {
        shareBoardConfig = new ShareBoardConfig();
        shareBoardConfig.setShareboardBackgroundColor(BACKGROUND_COLOR);
        shareBoardConfig.setCancelButtonBackground(BACKGROUND_COLOR);
        shareBoardConfig.setTitleText(SHARE_TITLE_TEXT);
        shareBoardConfig.setTitleTextColor(Color.parseColor("#EA0507"));
        shareBoardConfig.setCancelButtonTextColor(Color.parseColor("#D2D2D2"));
        shareBoardConfig.setCancelButtonText(SHARE_CANCEL_TEXT);
        shareBoardConfig.setIndicatorVisibility(false);
        shareBoardConfig.setMenuItemBackgroundShape(2, 5);

    }

    public void shareWeb(Activity content, @ShareType String type, String url, UMShareListener listener) {
        shareWeb(content, type, null, null, null, url, listener);
    }

    public void shareWeb(Activity context, @ShareType String type, String title, String content, String imageUrl, String url, UMShareListener listener) {
        popshare(context, url, type,title,content,imageUrl, listener);

    }

    public void popshare(final Activity activity, final String url, String type,String title,String content,String imageUrl, final UMShareListener listener) {
        /*if (dialog == null) {
            dialog = new Dialog(activity);
            View view = LayoutInflater.from(activity).inflate(R.layout.layout_dialog, null);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(view);
            settingDialog(activity, url, type, listener, view,title,content,imageUrl);
        }*/
        dialog = new Dialog(activity);
        View view = LayoutInflater.from(activity).inflate(R.layout.layout_dialog, null);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        settingDialog(activity, url, type, listener, view,title,content,imageUrl);
        dialog.show();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        window.setGravity(Gravity.BOTTOM);
        window.getDecorView().setPadding(0, 0, 0, 0);

    }

    private void settingDialog(final Activity activity, final String url, String type, final UMShareListener listener, View view, final String title, final String content, final String imageUrl) {
        view.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.iv_pengyouquan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share(activity, url, listener, SHARE_MEDIA.WEIXIN_CIRCLE,title,content,imageUrl);
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.iv_weixin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share(activity, url, listener, SHARE_MEDIA.WEIXIN,title,content,imageUrl);
                dialog.dismiss();
            }
        });

    }

    public void shareNativeImage(Activity activity, SHARE_MEDIA channel, File image,UMShareListener listener) {
        UMImage umImage=new UMImage(activity,image);
        new ShareAction(activity).withMedia(umImage)
                .setPlatform(channel)
                .setCallback(listener)
                .share();
    }


    private void share(Activity activity, String url, UMShareListener listener, SHARE_MEDIA channel,String title,String content,String imageUrl) {
        UMWeb web = new UMWeb(url);
        if (title!=null){
            web.setTitle(title);
        }
        if (content!=null){
            web.setDescription(content);
        }
        if (imageUrl!=null){
            web.setThumb(new UMImage(activity, imageUrl));
        }
        new ShareAction(activity)
                .withMedia(web)
                .setPlatform(channel)
                .setCallback(listener)
                .share();
    }

}
