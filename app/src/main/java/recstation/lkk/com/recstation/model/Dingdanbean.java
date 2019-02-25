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

package recstation.lkk.com.recstation.model;

import zuo.biao.library.base.BaseModel;
import zuo.biao.library.util.StringUtil;

/**用户类
 * @author Lemon
 */
public class Dingdanbean extends BaseModel {

	private static final long serialVersionUID = 1L;

	private String AREA; //县
	private String ADDRESS; //详细地址
	private String RETRIEVETYPE_ID; //类型
	private String SUSERNAME; //商家代号
	private String RETRIEVEORDER_ID; //订单
	private String YDATE; //日期
	private String CREATETIME; //创建时间
	private String STATUS; //状态
	private String YTIME; //时间
	private String MOBILE; //电话
	private String APPRAISE; //标题
	private String PROVINCE; //省
	private String CITY; //省
	private String PICTUREPATH; //图片路径
	private String RETRIEVETYPE_NAME; //类型名
	private String MERCHANTORDER_ID;
	private String RETRIEVETYPE_NAMES;
	private String USERNAME;

	public String getMERCHANTORDER_ID() {
		return MERCHANTORDER_ID;
	}

	public void setMERCHANTORDER_ID(String MERCHANTORDER_ID) {
		this.MERCHANTORDER_ID = MERCHANTORDER_ID;
	}

	public String getRETRIEVETYPE_NAMES() {
		return RETRIEVETYPE_NAMES;
	}

	public void setRETRIEVETYPE_NAMES(String RETRIEVETYPE_NAMES) {
		this.RETRIEVETYPE_NAMES = RETRIEVETYPE_NAMES;
	}

	public String getUSERNAME() {
		return USERNAME;
	}

	public void setUSERNAME(String USERNAME) {
		this.USERNAME = USERNAME;
	}

	public String getCITY() {
		return CITY;
	}

	public void setCITY(String CITY) {
		this.CITY = CITY;
	}

	public String getAREA() {
		return AREA;
	}

	public void setAREA(String AREA) {
		this.AREA = AREA;
	}

	public String getADDRESS() {
		return ADDRESS;
	}

	public void setADDRESS(String ADDRESS) {
		this.ADDRESS = ADDRESS;
	}

	public String getRETRIEVETYPE_ID() {
		return RETRIEVETYPE_ID;
	}

	public void setRETRIEVETYPE_ID(String RETRIEVETYPE_ID) {
		this.RETRIEVETYPE_ID = RETRIEVETYPE_ID;
	}

	public String getSUSERNAME() {
		return SUSERNAME;
	}

	public void setSUSERNAME(String SUSERNAME) {
		this.SUSERNAME = SUSERNAME;
	}

	public String getRETRIEVEORDER_ID() {
		return RETRIEVEORDER_ID;
	}

	public void setRETRIEVEORDER_ID(String RETRIEVEORDER_ID) {
		this.RETRIEVEORDER_ID = RETRIEVEORDER_ID;
	}

	public String getYDATE() {
		return YDATE;
	}

	public void setYDATE(String YDATE) {
		this.YDATE = YDATE;
	}

	public String getCREATETIME() {
		return CREATETIME;
	}

	public void setCREATETIME(String CREATETIME) {
		this.CREATETIME = CREATETIME;
	}

	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String STATUS) {
		this.STATUS = STATUS;
	}

	public String getYTIME() {
		return YTIME;
	}

	public void setYTIME(String YTIME) {
		this.YTIME = YTIME;
	}

	public String getMOBILE() {
		return MOBILE;
	}

	public void setMOBILE(String MOBILE) {
		this.MOBILE = MOBILE;
	}

	public String getAPPRAISE() {
		return APPRAISE;
	}

	public void setAPPRAISE(String APPRAISE) {
		this.APPRAISE = APPRAISE;
	}

	public String getPROVINCE() {
		return PROVINCE;
	}

	public void setPROVINCE(String PROVINCE) {
		this.PROVINCE = PROVINCE;
	}

	public String getPICTUREPATH() {
		return PICTUREPATH;
	}

	public void setPICTUREPATH(String PICTUREPATH) {
		this.PICTUREPATH = PICTUREPATH;
	}

	public String getRETRIEVETYPE_NAME() {
		return RETRIEVETYPE_NAME;
	}

	public void setRETRIEVETYPE_NAME(String RETRIEVETYPE_NAME) {
		this.RETRIEVETYPE_NAME = RETRIEVETYPE_NAME;
	}

	@Override
	protected boolean isCorrect() {//根据自己的需求决定，也可以直接 return true
		return  StringUtil.isNotEmpty(RETRIEVEORDER_ID, true);
	}

}
