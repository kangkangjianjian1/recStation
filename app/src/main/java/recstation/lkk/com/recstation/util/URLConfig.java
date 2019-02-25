package recstation.lkk.com.recstation.util;



public class URLConfig {

	public static String HEAD_URL = "http://47.92.55.233:8080/";
	public static String LOGIN_URL = HEAD_URL + "hdrra/appuser/applogin";
	public static String GETSMS_URL = HEAD_URL + "hdrra/appsms/sendsms";
	public static String REGISTER_URL = HEAD_URL + "hdrra/appuser/registerAppUser";
	public static String SETDPWD_URL = HEAD_URL + "hdrra/appuser/resetPWD";
	public static String CHECKSMS_URL = HEAD_URL + "hdrra/appsms/checksms";
	public static String INDEX_URL = HEAD_URL + "hdrra/appmain/index";
	public static String PRODUCT_URL = HEAD_URL + "hdrra/appmain/mallitems";
	public static String RECPERSON_URL = HEAD_URL + "hdrra/appmerchant/list";
	public static String DINGDAN_URL = HEAD_URL + "hdrra/apporder/list";
	public static String EDITDINGDAN_URL = HEAD_URL + "hdrra/apporder/edit";
	public static String ADDADDRESS_URL = HEAD_URL + "hdrra/appuseraddr/save";
	public static String DELADDRESS_URL = HEAD_URL + "hdrra/appuseraddr/delete";
	public static String FINDADDRESS_URL = HEAD_URL + "hdrra/appuseraddr/findByUsername";
	public static String EDITADDRESS_URL = HEAD_URL + "hdrra/appuseraddr/edit";
	public static String SETDEFAULTADDRESS_URL = HEAD_URL + "hdrra/appuseraddr/editDeft";
	public static String EDITUSERNAME_URL = HEAD_URL + "hdrra/appuser/editName";
	public static String EDITUSERPIC_URL = HEAD_URL + "hdrra/appuser/editPath";
	public static String BUSSINESS_INDECATE_URL = HEAD_URL + "hdrra/appmerchant/save";
	public static String YUYUE_URL = HEAD_URL + "hdrra/apporder/retrieveorder";
	public static String SHOUGOU_FABU_URL = HEAD_URL + "hdrra/appmerchantorder/save";
	public static String SHOUGOU_QUERY_URL = HEAD_URL + "hdrra/appmerchantorder/list";
	public static String SHOUGOU_DEL_URL = HEAD_URL + "hdrra/appmerchantorder/delete";
	public static String ADVICE_URL = HEAD_URL + "hdrra/appopinion/save";

	public static final boolean IS_DEBUG = true;




	public static int TYPETOLIST = 1;



}
