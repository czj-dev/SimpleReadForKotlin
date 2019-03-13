package com.rank.service;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.DegradeService;

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/3/13
 *     desc  :
 * </pre>
 */
@Route(path = "router/degrade")
public class DegradeServiceImpl implements DegradeService {

    @Override
    public void onLost(Context context, Postcard postcard) {
    }

    @Override
    public void init(Context context) {

    }
}
