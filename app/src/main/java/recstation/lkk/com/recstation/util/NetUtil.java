package recstation.lkk.com.recstation.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class NetUtil {
	public static boolean isNetConnected(Context context) {
		boolean isNetConnected;
		// 获得网络连接服务
		ConnectivityManager connManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connManager.getActiveNetworkInfo();
		if (info != null && info.isAvailable()) {
			isNetConnected = true;
		} else {
			isNetConnected = false;
		}
		return isNetConnected;
	}


	public static RequestBody toRequestBody(String value) {
		RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), value);
		return requestBody;
	}


}