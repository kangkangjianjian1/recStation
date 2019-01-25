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
public class ProductBean extends BaseModel {

    private static final long serialVersionUID = 1L;

    private String MALLITEMS_ID; //回收类型
    private String POINTS; //积分
    private String ORDER_ID; //顺序
    private String PRICE; //价格
    private String PICTUREPATH; //图片路径
    private String ITEM_NAME; //标题
    private String BZ; //类属于


    public String getMALLITEMS_ID() {
        return MALLITEMS_ID;
    }

    public void setMALLITEMS_ID(String MALLITEMS_ID) {
        this.MALLITEMS_ID = MALLITEMS_ID;
    }

    public String getPOINTS() {
        return POINTS;
    }

    public void setPOINTS(String POINTS) {
        this.POINTS = POINTS;
    }

    public String getORDER_ID() {
        return ORDER_ID;
    }

    public void setORDER_ID(String ORDER_ID) {
        this.ORDER_ID = ORDER_ID;
    }

    public String getPRICE() {
        return PRICE;
    }

    public void setPRICE(String PRICE) {
        this.PRICE = PRICE;
    }

    public String getPICTUREPATH() {
        return PICTUREPATH;
    }

    public void setPICTUREPATH(String PICTUREPATH) {
        this.PICTUREPATH = PICTUREPATH;
    }

    public String getITEM_NAME() {
        return ITEM_NAME;
    }

    public void setITEM_NAME(String ITEM_NAME) {
        this.ITEM_NAME = ITEM_NAME;
    }

    public String getBZ() {
        return BZ;
    }

    public void setBZ(String BZ) {
        this.BZ = BZ;
    }

    @Override
    protected boolean isCorrect() {//根据自己的需求决定，也可以直接 return true
        return id > 0;// && StringUtil.isNotEmpty(phone, true);
    }

}
