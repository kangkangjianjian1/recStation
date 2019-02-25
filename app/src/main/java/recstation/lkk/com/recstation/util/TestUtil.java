/*Copyright ©2015 TommyLemon(https://github.com/TommyLemon)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package recstation.lkk.com.recstation.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import recstation.lkk.com.recstation.LoginActivity;
import recstation.lkk.com.recstation.application.DemoApplication;
import recstation.lkk.com.recstation.model.User;
import zuo.biao.library.util.StringUtil;

import static zuo.biao.library.util.CommonUtil.showShortToast;
import static zuo.biao.library.util.CommonUtil.toActivity;


/**
 * 仅测试用，图片地址可能会失效
 *
 * @author Lemon
 */
public class TestUtil {

    public static List<User> getUserList() {
        return getUserList(0);
    }

    /**
     * @param page 页码
     * @return
     */
    public static List<User> getUserList(int page) {
        return getUserList(page, 10);
    }

    /**
     * @param page  页码
     * @param count 最大一页数量
     * @return
     */
    public static List<User> getUserList(int page, int count) {
        List<User> list = new ArrayList<User>();
        long userId;
        User user;
        int length = (count <= 0 || count > URLS.length ? URLS.length : count);
        int index;
        for (int i = 0; i < length; i++) {
            userId = i + page * length + 1;
            index = i + page * length;
            while (index >= URLS.length) {
                index -= URLS.length;
            }
            if (index < 0) {
                index = 0;
            }

            user = new User();
            user.setId(userId);
            user.setSex(i % 3);
            user.setHead(URLS[index]);
            user.setName("Name" + userId);
            user.setPhone(String.valueOf(1311736568 + (i + userId) * (page + userId)));
            user.setStarred(i % 2 == 0);
            list.add(user);
        }
        return list;
    }

    /**
     * 图片地址，仅供测试用
     */
    public static String[] URLS = {
            "http://static.oschina.net/uploads/user/1218/2437072_100.jpg?t=1461076033000",
            "http://common.cnblogs.com/images/icon_weibo_24.png",
            "http://static.oschina.net/uploads/user/585/1170143_50.jpg?t=1390226446000",
            "http://static.oschina.net/uploads/user/1174/2348263_50.png?t=1439773471000",
            "http://common.cnblogs.com/images/wechat.png",
            "https://static.oschina.net/uploads/user/1344/2689427_200.jpg?t=1484029839000",
            "http://static.oschina.net/uploads/user/998/1997902_50.jpg?t=1407806577000",
            "http://static.oschina.net/uploads/user/1/3064_50.jpg?t=1449566001000",
            "http://static.oschina.net/uploads/user/51/102723_50.jpg?t=1449212504000",
            "http://static.oschina.net/uploads/user/48/96331_50.jpg",
            "http://static.oschina.net/uploads/user/48/97721_50.jpg?t=1451544779000",
            "http://static.oschina.net/uploads/user/48/96289_50.jpg?t=1452751699000",
            "http://static.oschina.net/uploads/user/19/39085_50.jpg",
            "http://static.oschina.net/uploads/user/1332/2664107_50.jpg?t=1457405500000",
            "http://static.oschina.net/uploads/user/1385/2770216_50.jpg?t=1464405516000",
            "http://static.oschina.net/uploads/user/427/855532_50.jpg?t=1435030876000",
            "http://static.oschina.net/uploads/user/629/1258821_50.jpg?t=1378063141000",
            "http://static.oschina.net/uploads/user/1200/2400261_50.png?t=1439638750000",
            "https://static.oschina.net/uploads/user/1143/2286031_50.jpg?t=1468900004000"
    };

    public static String getPicture(int index) {
        return index < 0 || index >= URLS.length ? null : URLS[index];
    }

    //判断是否为手机号
    public static boolean isPhoneNumber(String phoneNo) {
        if (TextUtils.isEmpty(phoneNo)) {
            return false;
        }
        if (phoneNo.length() == 11) {
            for (int i = 0; i < 11; i++) {
                if (!PhoneNumberUtils.isISODigit(phoneNo.charAt(i))) {
                    return false;
                }
            }
            Pattern p = Pattern.compile("^((13[^4,\\D])" + "|(134[^9,\\D])" +
                    "|(14[5,7])" +
                    "|(15[^4,\\D])" +
                    "|(17[3,6-8])" +
                    "|(18[0-9]))\\d{8}$");
            Matcher m = p.matcher(phoneNo);
            return m.matches();
        }
        return false;
    }

    //获取ａｓｅｅｔｓ文件夹中的ｊｓｏｎ数据
    public static String getJson(Context context, String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public static void call(Activity context, String phone) {
        if (StringUtil.isNotEmpty(phone, true)) {
            Uri uri = Uri.parse("tel:" + phone.trim());
            Intent intent = new Intent(Intent.ACTION_CALL, uri);
            toActivity(context, intent);
            return;
        }
        showShortToast(context, "请先选择号码哦~");
    }


    public static String getNowTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String dateStringParse = sdf.format(date);
        Logger.e(dateStringParse, dateStringParse);
        return dateStringParse;
    }
    public static boolean IsBussiness() {
        String status = HKEapiManager.getInstances().preferences.getStringData(DemoApplication.getInstance(), "isbusiness", "null");

        if (status.equals("null")) {
            return false;
        }
        return true;
    }
    public static boolean IsLogin() {
       String loginSign = HKEapiManager.getInstances().preferences.getStringData(DemoApplication.getInstance(), "loginuser", "no");

       if (loginSign.equals("no")){
           return false;
       }

        String logintime = HKEapiManager.getInstances().preferences.getStringData(DemoApplication.getInstance(), "islogin", "1991-01-09 00:00:00");
        String nowtime = getNowTime();
        Logger.e(logintime, logintime);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = df.parse(nowtime);
            d2 = df.parse(logintime);
            long nm = 1000 * 60;
            long diff = d1.getTime() - d2.getTime();
            long min = diff / nm;
            Logger.e(min + "cccnnnmmm", min + "");
            if (min > 1440) {
                //跳转登陆
                return false;
            } else {
                return true;
            }

        } catch (ParseException e) {

            e.printStackTrace();
            return false;
        }
    }


    public static boolean IsQianDao() {
        String qiandaoSign = HKEapiManager.getInstances().preferences.getStringData(DemoApplication.getInstance(), "qiandao", "0");

        if (qiandaoSign.equals("0")){
            return false;
        }
        String nowtime = getNowTime();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = df.parse(nowtime);
            d2 = df.parse(qiandaoSign);
//            long nm = 1000 * 60;
//            long diff = d1.getTime() - d2.getTime();
//            long min = diff / nm;
//            Logger.e(min + "cccnnnmmm", min + "");
            if (!d1.equals(d2)) {
                //跳转登陆
                return false;
            } else {
                return true;
            }

        } catch (ParseException e) {

            e.printStackTrace();
            return false;
        }
    }


}
