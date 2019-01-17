package recstation.lkk.com.recstation.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;


/**
 * 本地数据加密及解密
 * 
 */
public class SharedPreferencesUtils {

	public static SharedPreferencesUtils instance = null;
	public SharedPreferences settings = null;
	public static String SharedPreferences_URL = "LKK_NEW_SETTING_INFOS";
	public static SharedPreferencesUtils getInstence() {
		if (instance == null) {
			instance = new SharedPreferencesUtils();
		}
		return instance;
	}

	public void putStringData(Context ctx, String key, String value) {
		settings = ctx.getSharedPreferences(SharedPreferences_URL, 0);
		if (TextUtils.isEmpty(value)) {
			settings.edit().putString(key, "").commit();
		} else {

			settings.edit().putString(key, value).commit();
		}
	}

	public void putBooleanData(Context ctx, String key, boolean value) {
		settings = ctx.getSharedPreferences(SharedPreferences_URL, 0);
		settings.edit().putBoolean(key, value).commit();
	}

	public String getStringData(Context ctx, String key, String defaultValue) {
		settings = ctx.getSharedPreferences(SharedPreferences_URL, 0);
		String data = settings.getString(key, "");
		if (TextUtils.isEmpty(data)) {
			data = defaultValue;
		}
		return data;
	}

	public boolean getBooleanData(Context ctx, String key, boolean defaultValue) {
		settings = ctx.getSharedPreferences(SharedPreferences_URL, 0);
		boolean data = settings.getBoolean(key, defaultValue);
		return data;
	}

	public long getLongData(Context ctx, String key, long defaultValue) {
		settings = ctx.getSharedPreferences(SharedPreferences_URL, 0);
		long data = settings.getLong(key, defaultValue);
		return data;
	}

	public void putLongData(Context ctx, String key, long value) {
		settings = ctx.getSharedPreferences(SharedPreferences_URL, 0);
		settings.edit().putLong(key, value).commit();
	}

	public float getFloatData(Context ctx, String key, float defaultValue) {
		settings = ctx.getSharedPreferences(SharedPreferences_URL, 0);
		float data = settings.getFloat(key, defaultValue);
		return data;
	}

	public void putFloatData(Context ctx, String key, float value) {
		settings = ctx.getSharedPreferences(SharedPreferences_URL, 0);
		settings.edit().putFloat(key, value).commit();
	}

	public int getIntData(Context ctx, String key, int defaultValue) {
		settings = ctx.getSharedPreferences(SharedPreferences_URL, 0);
		int data = settings.getInt(key, defaultValue);
		return data;
	}

	public void putIntData(Context ctx, String key, int value) {
		settings = ctx.getSharedPreferences(SharedPreferences_URL, 0);
		settings.edit().putInt(key, value).commit();
	}

	public void removeData(Context ctx, String key) {
		settings = ctx.getSharedPreferences(SharedPreferences_URL, 0);
		settings.edit().remove(key);

	}


	public  boolean putBitmap(Context context, String key, Bitmap bitmap) {
		settings = context.getSharedPreferences(SharedPreferences_URL,
				Context.MODE_PRIVATE);

		paraCheck(settings, key);
		if (bitmap == null || bitmap.isRecycled()) {
			return false;
		} else {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
			String imageBase64 = new String(Base64.encode(baos.toByteArray(),
					Base64.DEFAULT));
			SharedPreferences.Editor e = settings.edit();
			e.putString(key, imageBase64);
			return e.commit();
		}
	}

	public Bitmap getBitmap(Context context, String key,
                            Bitmap defaultValue) {
		settings = context.getSharedPreferences(SharedPreferences_URL,
				Context.MODE_PRIVATE);

		paraCheck(settings, key);
		String imageBase64 = settings.getString(key, "");
		if (TextUtils.isEmpty(imageBase64)) {
			return defaultValue;
		}

		byte[] base64Bytes = Base64.decode(imageBase64.getBytes(),
				Base64.DEFAULT);
		ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
		Bitmap ret = BitmapFactory.decodeStream(bais);
		if (ret != null) {
			return ret;
		} else {
			return defaultValue;
		}
	}

	private static void paraCheck(SharedPreferences sp, String key) {
		if (sp == null) {
			throw new IllegalArgumentException();
		}
		if (TextUtils.isEmpty(key)) {
			throw new IllegalArgumentException();
		}
	}



}
