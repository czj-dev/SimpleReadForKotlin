package com.rank.basiclib.utils;


import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseIntArray;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2018/6/19
 *     desc  :
 * </pre>
 */
public class PushMessageManager {
    private static final String UNREAD_NUMBER = "PushMessageManager_unread_number";

    private List<PushMessage> unReadlist = new ArrayList<>();
    /**
     * 总的未读数量
     */
    private BehaviorSubject<Integer> unReadSum = BehaviorSubject.create();
    /**
     * 根据类型放置的未读数量
     * 1：精选活动
     * 2：白条提醒
     * 3：消费提醒
     * 4：系统通知
     * 5：服务通知
     * 6：小优信用
     */
    private SparseIntArray unReadArray = new SparseIntArray();


    private static class PushMessageManagerHolder {
        static final PushMessageManager INSTANCE = new PushMessageManager();
    }


    public static PushMessageManager getInstance() {
        return PushMessageManagerHolder.INSTANCE;
    }

    private PushMessageManager() {
        final int number = Preferences.Companion.userPreferences().getInt(UNREAD_NUMBER, 0);
        unReadSum.onNext(number);
    }


    public void addMessage(PushMessage message) {
        unReadlist.add(message);
        addUnread(1, Integer.parseInt(message.getType()));
    }

    public void addUnread(int number, int type) {
        if (type == 0) {
            return;
        }
        final Integer unReadSumValue = unReadSum.getValue();
        unReadSum.onNext(unReadSumValue + number);
        unReadArray.put(type, unReadArray.get(type) + number);
    }

    public void removeUnread(int type) {
        removeUnread(0, type);
    }

    public void removeUnread(int sum, int type) {
        int number = unReadArray.get(type);
        if (sum == 0) {
            unReadArray.put(type, 0);
        } else {
            number = number - sum;
            unReadArray.put(type, sum);
        }
        unReadSum.onNext(unReadSum.getValue() - number);
    }

    public Observable<Boolean> isUnreadMessage() {
        return unReadSum.map(sum -> sum > 0);
    }

    public static class PushMessage implements Parcelable {
        private String msgId;
        private String uid;
        private String business;
        private String type;
        private String url;


        public String getMsgId() {
            return msgId;
        }

        public void setMsgId(String msgId) {
            this.msgId = msgId;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getBusiness() {
            return business;
        }

        public void setBusiness(String business) {
            this.business = business;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.msgId);
            dest.writeString(this.uid);
            dest.writeString(this.business);
            dest.writeString(this.type);
            dest.writeString(this.url);
        }

        public PushMessage() {
        }

        protected PushMessage(Parcel in) {
            this.msgId = in.readString();
            this.uid = in.readString();
            this.business = in.readString();
            this.type = in.readString();
            this.url = in.readString();
        }

        public static final Creator<PushMessage> CREATOR = new Creator<PushMessage>() {
            @Override
            public PushMessage createFromParcel(Parcel source) {
                return new PushMessage(source);
            }

            @Override
            public PushMessage[] newArray(int size) {
                return new PushMessage[size];
            }
        };
    }
}
