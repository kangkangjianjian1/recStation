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

/**
 * 用户类
 *
 * @author Lemon
 */
public class RecPerson extends BaseModel {

    private static final long serialVersionUID = 1L;

    private String USERNAME; //用户名'
    private String MOBILE; //手机
    private String LEGALNAME; //法人
    private String REGISTERADDRESS; //注册地址
    private String PRICE; //价格
    private String TYPE; //类别
    private String PICTUREPATH; //图片路径

    private String PUBLICACCOUNT; //公司账户
    private String PROVINCE; //省
    private String CITY; //市
    private String AREA; //乡镇
    private String LON; //经度
    private String LAT; //纬度

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public String getMOBILE() {
        return MOBILE;
    }

    public void setMOBILE(String MOBILE) {
        this.MOBILE = MOBILE;
    }

    public String getLEGALNAME() {
        return LEGALNAME;
    }

    public void setLEGALNAME(String LEGALNAME) {
        this.LEGALNAME = LEGALNAME;
    }

    public String getREGISTERADDRESS() {
        return REGISTERADDRESS;
    }

    public void setREGISTERADDRESS(String REGISTERADDRESS) {
        this.REGISTERADDRESS = REGISTERADDRESS;
    }

    public String getPRICE() {
        return PRICE;
    }

    public void setPRICE(String PRICE) {
        this.PRICE = PRICE;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public String getPICTUREPATH() {
        return PICTUREPATH;
    }

    public void setPICTUREPATH(String PICTUREPATH) {
        this.PICTUREPATH = PICTUREPATH;
    }

    public String getPUBLICACCOUNT() {
        return PUBLICACCOUNT;
    }

    public void setPUBLICACCOUNT(String PUBLICACCOUNT) {
        this.PUBLICACCOUNT = PUBLICACCOUNT;
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

    public String getLON() {
        return LON;
    }

    public void setLON(String LON) {
        this.LON = LON;
    }

    public String getLAT() {
        return LAT;
    }

    public void setLAT(String LAT) {
        this.LAT = LAT;
    }

    @Override
    protected boolean isCorrect() {//根据自己的需求决定，也可以直接 return true
        return StringUtil.isNotEmpty(USERNAME, true);
    }

}
