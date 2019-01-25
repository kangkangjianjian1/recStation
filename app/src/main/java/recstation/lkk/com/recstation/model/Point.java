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
public class Point extends BaseModel {

	private static final long serialVersionUID = 1L;


	private String pointType; //类型
	private String title; //标题
	private int num; //摘要
	private String time; //时间

	public String getPointType() {
		return pointType;
	}

	public void setPointType(String pointType) {
		this.pointType = pointType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Override
	protected boolean isCorrect() {//根据自己的需求决定，也可以直接 return true
		return  StringUtil.isNotEmpty(title, true);
	}

}
