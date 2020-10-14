package com.rank.basiclib.utils.transform;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import androidx.annotation.Nullable;

public class RepeatOneCoundGifTransformation implements RequestListener<GifDrawable> {
    @Override
    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
        return false;
    }

    @Override
    public boolean onResourceReady(GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
        resource.setLoopCount(1);
        return false;
    }
}
