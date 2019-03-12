package com.rank.basiclib.utils;

import android.os.Looper;

public final class ThreadUtils {
  private ThreadUtils() {}

  public static boolean isMainThread() {
    return Looper.getMainLooper().getThread() == Thread.currentThread();
  }
}
