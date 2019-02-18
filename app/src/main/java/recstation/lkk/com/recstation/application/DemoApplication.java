package recstation.lkk.com.recstation.application;

import android.os.Environment;
import android.util.Log;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import cn.jiguang.share.android.api.JShareInterface;
import cn.jiguang.share.android.api.PlatformConfig;
import recstation.lkk.com.recstation.manager.DataManager;
import recstation.lkk.com.recstation.model.User;
import zuo.biao.library.base.BaseApplication;
import zuo.biao.library.util.StringUtil;

/**Application
 * @author Lemon
 */
public class DemoApplication extends BaseApplication {
	private static final String TAG = "DemoApplication";
	public static String ImagePath;

	private static DemoApplication context;
	public static DemoApplication getInstance() {
		return context;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		context = this;



		SDKInitializer.initialize(this);
		//自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
		//包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
		SDKInitializer.setCoordType(CoordType.BD09LL);



		JShareInterface.setDebugMode(true);
		PlatformConfig platformConfig = new PlatformConfig()
				.setWechat("wxc40e16f3ba6ebabc", "dcad950cd0633a27e353477c4ec12e7a")
				.setQQ("1106011004", "YIbPvONmBQBZUGaN")
				.setSinaWeibo("374535501", "baccd12c166f1df96736b51ffbf600a2", "https://www.jiguang.cn")
				.setFacebook("1847959632183996", "JShareDemo")
				.setTwitter("fCm4SUcgYI1wUACGxB2erX5pL", "NAhzwYCgm15FBILWqXYDKxpryiuDlEQWZ5YERnO1D89VBtZO6q")
				.setJchatPro("1847959632183996");
		/**
		 * since 1.5.0，1.5.0版本后增加API，支持在代码中设置第三方appKey等信息，当PlatformConfig为null时，或者使用JShareInterface.init(Context)时需要配置assets目录下的JGShareSDK.xml
		 **/
		//*
		JShareInterface.init(this, platformConfig);
        /*/
        JShareInterface.init(this);
       /**/




		new Thread(){
			@Override
			public void run() {

				File imageFile =  copyResurces( "jiguang_test_img.png", "test_img.png", 0);
			//	File videoFile = copyResurces( "jiguang_test.mp4", "jiguang_test.mp4", 0);
				if(imageFile != null){
					ImagePath = imageFile.getAbsolutePath();
				}

//				if(videoFile != null){
//					VideoPath = videoFile.getAbsolutePath();
//				}

				super.run();
			}
		}.start();

	}

	
	/**获取当前用户id
	 * @return
	 */
	public long getCurrentUserId() {
		currentUser = getCurrentUser();
		Log.d(TAG, "getCurrentUserId  currentUserId = " + (currentUser == null ? "null" : currentUser.getId()));
		return currentUser == null ? 0 : currentUser.getId();
	}
	/**获取当前用户phone
	 * @return
	 */
	public String getCurrentUserPhone() {
		currentUser = getCurrentUser();
		return currentUser == null ? null : currentUser.getPhone();
	}


	private static User currentUser = null;
	public User getCurrentUser() {
		if (currentUser == null) {
			currentUser = DataManager.getInstance().getCurrentUser();
		}
		return currentUser;
	}

	public void saveCurrentUser(User user) {
		if (user == null) {
			Log.e(TAG, "saveCurrentUser  currentUser == null >> return;");
			return;
		}
		if (user.getId() <= 0 && StringUtil.isNotEmpty(user.getName(), true) == false) {
			Log.e(TAG, "saveCurrentUser  user.getId() <= 0" +
					" && StringUtil.isNotEmpty(user.getName(), true) == false >> return;");
			return;
		}

		currentUser = user;
		DataManager.getInstance().saveCurrentUser(currentUser);
	}

	public void logout() {
		currentUser = null;
		DataManager.getInstance().saveCurrentUser(currentUser);
	}

	/**判断是否为当前用户
	 * @param userId
	 * @return
	 */
	public boolean isCurrentUser(long userId) {
		return DataManager.getInstance().isCurrentUser(userId);
	}

	public boolean isLoggedIn() {
		return getCurrentUserId() > 0;
	}


	private  File copyResurces(String src, String dest, int flag){
		File filesDir = null;
		try {
			if(flag == 0) {//copy to sdcard
				filesDir = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() + "/jiguang/" + dest);
				File parentDir = filesDir.getParentFile();
				if(!parentDir.exists()){
					parentDir.mkdirs();
				}
			}else{//copy to data
				filesDir = new File(getFilesDir(), dest);
			}
			if(!filesDir.exists()) {
				filesDir.createNewFile();
				InputStream open = getAssets().open(src);
				FileOutputStream fileOutputStream = new FileOutputStream(filesDir);
				byte[] buffer = new byte[4 * 1024];
				int len = 0;
				while ((len = open.read(buffer)) != -1) {
					fileOutputStream.write(buffer, 0, len);
				}
				open.close();
				fileOutputStream.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			if(flag == 0){
				filesDir = copyResurces( src,  dest, 1);
			}
		}
		return filesDir;
	}
}
