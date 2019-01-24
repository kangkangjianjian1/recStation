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

/**
 * 用户类
 *
 * @author Lemon
 */
public class Notice extends BaseModel {

    private static final long serialVersionUID = 1L;

    private String LINKURL; //标题
    private String BODY; //标题
    private String STATUS; //标题
    private String CTIME; //标题
    private String NOTICE_ID; //标题

    public String getTITILE() {
        return TITILE;
    }

    public void setTITILE(String TITILE) {
        this.TITILE = TITILE;
    }

    private String TITILE; //摘要TITILE

    public String getLINKURL() {
        return LINKURL;
    }

    public void setLINKURL(String LINKURL) {
        this.LINKURL = LINKURL;
    }

    public String getBODY() {
        return BODY;
    }

    public void setBODY(String BODY) {
        this.BODY = BODY;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getCTIME() {
        return CTIME;
    }

    public void setCTIME(String CTIME) {
        this.CTIME = CTIME;
    }

    public String getNOTICE_ID() {
        return NOTICE_ID;
    }

    public void setNOTICE_ID(String NOTICE_ID) {
        this.NOTICE_ID = NOTICE_ID;
    }




    @Override
    protected boolean isCorrect() {//根据自己的需求决定，也可以直接 return true
        return id > 0;// && StringUtil.isNotEmpty(phone, true);
    }

}
