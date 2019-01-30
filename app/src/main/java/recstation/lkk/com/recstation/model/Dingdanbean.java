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

	private String USERNAME; //用户名
	private String RETRIEVEORDER_ID; //ID
	private String RETRIEVETYPE_NAME; //回收类型
	private String PROVINCE; //省
	private String CITY; //市区
	private String AREA; //县
	private String STATUS; //状态
	private String ADDRESS; //地址
	private String MOBILE; //电话
	private String YDATE; //日期
	private String YTIME; //时间

	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String STATUS) {
		this.STATUS = STATUS;
	}

	public String getUSERNAME() {
		return USERNAME;
	}

	public void setUSERNAME(String USERNAME) {
		this.USERNAME = USERNAME;
	}

	public String getRETRIEVEORDER_ID() {
		return RETRIEVEORDER_ID;
	}

	public void setRETRIEVEORDER_ID(String RETRIEVEORDER_ID) {
		this.RETRIEVEORDER_ID = RETRIEVEORDER_ID;
	}

	public String getRETRIEVETYPE_NAME() {
		return RETRIEVETYPE_NAME;
	}

	public void setRETRIEVETYPE_NAME(String RETRIEVETYPE_NAME) {
		this.RETRIEVETYPE_NAME = RETRIEVETYPE_NAME;
	}

	public String getPROVINCE() {
		return PROVINCE;
	}

	public void setPROVINCE(String PROVINCE) {
		this.PROVINCE = PROVINCE;
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

	public String getMOBILE() {
		return MOBILE;
	}

	public void setMOBILE(String MOBILE) {
		this.MOBILE = MOBILE;
	}

	public String getYDATE() {
		return YDATE;
	}

	public void setYDATE(String YDATE) {
		this.YDATE = YDATE;
	}

	public String getYTIME() {
		return YTIME;
	}

	public void setYTIME(String YTIME) {
		this.YTIME = YTIME;
	}

	@Override
	protected boolean isCorrect() {//根据自己的需求决定，也可以直接 return true
		return StringUtil.isNotEmpty(USERNAME, true);
	}

}
