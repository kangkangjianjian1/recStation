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

package recstation.lkk.com.recstation.adapter;

import android.app.Activity;
import android.view.ViewGroup;

import recstation.lkk.com.recstation.model.Dingdanbean;
import recstation.lkk.com.recstation.model.User;
import recstation.lkk.com.recstation.view.DingdanView;
import recstation.lkk.com.recstation.view.UserView;
import zuo.biao.library.base.BaseAdapter;

/**用户adapter
 * @author lkk
 */
public class DingdanAdapter extends BaseAdapter<Dingdanbean, DingdanView> {
	//	private static final String TAG = "DingdanAdapter";

	public DingdanAdapter(Activity context) {
		super(context);
	}

	@Override
	public DingdanView createView(int position, ViewGroup parent) {
		return new DingdanView(context, parent);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}