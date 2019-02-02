package recstation.lkk.com.recstation;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

import recstation.lkk.com.recstation.util.Logger;
import zuo.biao.library.base.BaseActivity;

public class MapActivity extends BaseActivity implements View.OnClickListener {
    private MapView mMapView = null;
    BaiduMap mBaiduMap;
    LocationClient mLocationClient;
    /**
     * 启动这个Activity的Intent
     *
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, MapActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        initView();
        initData();
        initEvent();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void initView() {
        mMapView = findView(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
//普通地图 ,mBaiduMap是地图控制器对象
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setMyLocationEnabled(true);
        MyLocationConfiguration.LocationMode mCurrentMode = MyLocationConfiguration.LocationMode.FOLLOWING;
        BitmapDescriptor customMarker= BitmapDescriptorFactory.fromResource(R.drawable.circle_yellow);

        MyLocationConfiguration myLocationConfiguration =new MyLocationConfiguration(mCurrentMode,true,customMarker);
        mBaiduMap.setMyLocationConfiguration(myLocationConfiguration);

//定义Maker坐标点
        LatLng point1 = new LatLng(36.642726, 114.54848);
        LatLng point2 = new LatLng(36.647726, 114.55848);
        LatLng point3 = new LatLng(36.602726, 114.54848);
        LatLng point4 = new LatLng(36.612726, 114.51848);
//构建Marker图标36.612726
//    114.53848
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.shangcheng);
//构建MarkerOption，用于在地图上添加Marker
//用来构造InfoWindow的Button
        Button button1 = new Button(getApplicationContext());
        button1.setBackgroundResource(R.drawable.alpha3);
        button1.setText("回收站点信息和电话:13833399331");

        InfoWindow mInfoWindow1 = new InfoWindow(button1, point1, -100);

        mBaiduMap.showInfoWindow(mInfoWindow1);

//构造InfoWindow
//point 描述的位置点
//-100 InfoWindow相对于point在y轴的偏移量

        OverlayOptions option1 = new MarkerOptions()
                .position(point1)
                .title("回收站点1")
                .icon(bitmap);
        OverlayOptions option2 = new MarkerOptions()
                .position(point2)
                .title("回收站点2")
                .icon(bitmap);
        OverlayOptions option3 = new MarkerOptions()
                .position(point3)
                .title("回收站点3")
                .icon(bitmap);
        OverlayOptions option4 = new MarkerOptions()
                .position(point4)
                .title("回收站点4")
                .icon(bitmap);



//在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option1);
        mBaiduMap.addOverlay(option2);
        mBaiduMap.addOverlay(option3);
        mBaiduMap.addOverlay(option4);
        //使InfoWindow生效

    }

    @Override
    public void initData() {
//定位初始化
        mLocationClient = new LocationClient(this);

//通过LocationClientOption设置LocationClient相关参数
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);

//设置locationClientOption
        mLocationClient.setLocOption(option);

//注册LocationListener监听器
        MyLocationListener myLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myLocationListener);
//开启地图定位图层
        mLocationClient.start();
    }

    @Override
    public void initEvent() {
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            //marker被点击时回调的方法
            //若响应点击事件，返回true，否则返回false
            //默认返回false
            @Override
            public boolean onMarkerClick(Marker marker) {
                showShortToast(marker.getTitle());
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;

    }
    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //mapView 销毁后不在处理新接收的位置
            if (location == null || mMapView == null){
                return;
            }
           Logger.e("nihao", location.getLatitude()+"");
           Logger.e("nihao", location.getLongitude()+"");
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection()).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
        }
    }
}
