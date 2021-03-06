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

/**用户类
 * @author Lemon
 */
public class Adress extends BaseModel {

	private static final long serialVersionUID = 1L;

	private String USERADDRESS_ID; //id
	private String USERNAME; //用户名
	private String NAME; //地址用户
	private String PROVINCE; //省
	private String MOBILE; //电话
	private String CITY; //市
	private String AREA; //区
	private String ADDRESS; //地址
	private String DEFT; //是否默认

	public String getUSERADDRESS_ID() {
		return USERADDRESS_ID;
	}

	public void setUSERADDRESS_ID(String USERADDRESS_ID) {
		this.USERADDRESS_ID = USERADDRESS_ID;
	}

	public String getUSERNAME() {
		return USERNAME;
	}

	public void setUSERNAME(String USERNAME) {
		this.USERNAME = USERNAME;
	}

	public String getNAME() {
		return NAME;
	}

	public void setNAME(String NAME) {
		this.NAME = NAME;
	}

	public String getPROVINCE() {
		return PROVINCE;
	}

	public void setPROVINCE(String PROVINCE) {
		this.PROVINCE = PROVINCE;
	}

	public String getMOBILE() {
		return MOBILE;
	}

	public void setMOBILE(String MOBILE) {
		this.MOBILE = MOBILE;
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

	public String getDEFT() {
		return DEFT;
	}

	public void setDEFT(String DEFT) {
		this.DEFT = DEFT;
	}

	@Override
	protected boolean isCorrect() {//根据自己的需求决定，也可以直接 return true
		return !"".equals(NAME);// && StringUtil.isNotEmpty(phone, true);
	}

}
