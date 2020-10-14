package com.rank.basiclib.utils;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Pair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :  联系人信息获取工具类
 *     time  : 2018/7/9
 *     desc  :
 * </pre>
 */
public class ContactsUtils {

    private static final int PHONES_NUMBER_INDEX = 1;
    /**
     * 联系人显示名称
     **/
    private static final int PHONES_DISPLAY_NAME_INDEX = 0;
    /**
     * 获取库Phone表字段
     **/
    private static final String[] PHONES_PROJECTION = new String[]{
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};

    /**
     * 获取联系人
     */
    public static JSONObject getConcactsJson() throws JSONException {
        JSONObject jsonObject;
        String phoneNumber;
        String contactName;
        jsonObject = new JSONObject();
        ContentResolver resolver = Utils.getApp().getContentResolver();
        // 获取手机联系人
        Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PHONES_PROJECTION, null, null, null);
        if (phoneCursor != null) {
            while (phoneCursor.moveToNext()) {
                //得到手机号码
                phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
                //得到联系人名称
                contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);

                //当手机号码为空的或者为空字段 跳过当前循环
                if (TextUtils.isEmpty(phoneNumber) && TextUtils.isEmpty(contactName))
                    continue;

                if (containsEmoji(contactName)) {
                    contactName = deleteEmoji(contactName);
                }
                jsonObject.put(contactName, phoneNumber);
            }
            phoneCursor.close();
        }
        return jsonObject;
    }

    public static Cursor query(Intent intent) {
        final Cursor cursor = Utils.getApp().getContentResolver().query(intent.getData(), null, null, null, null);
        cursor.moveToFirst();
        return cursor;
    }

    /**
     * 获取选中联系人
     *
     * @param cursor 当前游标
     * @return Pair 手机号、名称
     */
    public static Pair<String, String> getContactPhone(Cursor cursor) {
        String phoneNumber = null;
        String name = null;
        int phoneColumn = cursor
                .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
        int phoneNum = cursor.getInt(phoneColumn);
        if (phoneNum > 0) {
            // 获得联系人的ID号
            int idColumn = cursor.getColumnIndex(ContactsContract.Contacts._ID);
            String contactId = cursor.getString(idColumn);
            // 获得联系人电话的cursor
            Cursor phone = Utils.getApp().getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    PHONES_PROJECTION,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "="
                            + contactId, null, null);
            if (phone == null) {
                return null;
            }
            if (phone.moveToFirst()) {
                for (; !phone.isAfterLast(); phone.moveToNext()) {
                    int index = phone
                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    name = phone.getString(0);
                    phoneNumber = phone.getString(index).replace(" ", "");
                    if (phoneNumber.length() == 11 && isNumber(phoneNumber)) {
                        break;
                    }
                    // 获得联系人电话的cursor
                }
                if (!phone.isClosed()) {
                    phone.close();
                }
            }
        }
        if (name != null && containsEmoji(name)) {
            name = deleteEmoji(name);
        }
        return Pair.create(name, phoneNumber);
    }

    private static boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * 检测是否有emoji表情
     *
     * @param source
     * @return
     */
    private static boolean containsEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (isEmojiCharacter(codePoint)) { //如果不能匹配,则该字符是Emoji表情

                return true;
            }
        }
        return false;
    }

    private static String deleteEmoji(String str) {//aaa12
        int len = str.length();
        String replace = str;
        for (int i = 0; i < len; i++) {
            char codePoint = str.charAt(i);
            if (isEmojiCharacter(codePoint)) { //如果不能匹配,则该字符是Emoji表情
                replace = replace.replace(codePoint + "", "U");
            }
        }
        return replace;
    }

    /**
     * 判断是否是Emoji
     *
     * @param codePoint 比较的单个字符
     * @return
     */
    private static boolean isEmojiCharacter(char codePoint) {

        return (codePoint != 0x0) && (codePoint != 0x9) && (codePoint != 0xA) &&
                (codePoint != 0xD) && ((codePoint < 0x20) || (codePoint > 0xD7FF)) &&
                ((codePoint < 0xE000) || (codePoint > 0xFFFD)) && ((codePoint < 0x10000)
                || (codePoint > 0x10FFFF));
    }
}
