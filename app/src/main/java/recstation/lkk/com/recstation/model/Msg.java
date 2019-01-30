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
public class Msg extends BaseModel {

	private static final long serialVersionUID = 1L;

	private String USERNAME; //用户名
	private String RETRIEVEORDER_ID; //ID
	private String RETRIEVETYPE_NAME; //回收类型
	private String PROVINCET; //省
	private String CITY; //市区
	private String AREA; //县
	private String ADDRESS; //地址
	private String MOBILE; //电话
	private String YDATE; //日期
	private String YTIME; //时间

	private String msgType; //类型
	private String title; //标题
	private String preMsg; //摘要
	private String time; //时间
	private String proMsg; //详细内容

	/**默认构造方法，JSON等解析时必须要有
	 */
	public Msg() {
		//default
	}
	public Msg(long id) {
		this();
		this.id = id;
	}
	public Msg(long id, String title) {
		this(id);
		this.title = title;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPreMsg() {
		return preMsg;
	}

	public void setPreMsg(String preMsg) {
		this.preMsg = preMsg;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getProMsg() {
		return proMsg;
	}

	public void setProMsg(String proMsg) {
		this.proMsg = proMsg;
	}

	/**
	 * 以下getter和setter可以自动生成
	 * <br>  eclipse: 右键菜单 > Source > Generate Getters and Setters
	 * <br>  android studio: 右键菜单 > Generate > Getter and Setter
	 */


	@Override
	protected boolean isCorrect() {//根据自己的需求决定，也可以直接 return true
		return id > 0;// && StringUtil.isNotEmpty(phone, true);
	}

}
