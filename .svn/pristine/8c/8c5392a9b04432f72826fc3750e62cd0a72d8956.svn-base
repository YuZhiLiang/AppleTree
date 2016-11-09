package com.sy.appletree.login_register;


import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.sy.appletree.R;
import com.sy.appletree.base.BaseActivity;
import com.sy.appletree.utils.Const;

/**
 * 选择城市
 */

public class ChooseCityActivity extends BaseActivity implements View.OnClickListener {

    private Button mLocation;
    private ImageView mShuaXin;
//    private Button mButton_btn;
    private LinearLayout mLayout_history;

    public LocationClient mLocationClient = null;
    public BDLocationListener mBDLocationListener;
    private int mIntExtra;//页面从哪开启的参数标识

    @Override
    protected void findViews() {
        mIntExtra = getIntent().getIntExtra(Const.PLACETOCITY,0);
        setTitleText("选择城市", true);
        //右侧图标不可见
        setBaseRightIcon(1, false);
        //城市搜索可见
        setCitySearchVillable(true);
        mLocation= (Button) findViewById(R.id.city_location);
        mShuaXin= (ImageView) findViewById(R.id.shuanxin);
//        mButton_btn= (Button) findViewById(R.id.city_btn);
        mLayout_history= (LinearLayout) findViewById(R.id.city_history);
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        initLocation();
        mBDLocationListener=new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation){// 网络定位结果
                    mShuaXin.setVisibility(View.GONE);//取消刷新按钮
                    Toast.makeText(ChooseCityActivity.this,"网络定位成功",Toast.LENGTH_SHORT).show();
                    setBaseNowCityText(bdLocation.getCity());
                    mLocation.setText(bdLocation.getCity());
                }else if (bdLocation.getLocType() == BDLocation.TypeOffLineLocation){
                    mShuaXin.setVisibility(View.GONE);//取消刷新按钮
                    setBaseNowCityText(bdLocation.getCity());
                    mLocation.setText(bdLocation.getCity());
                    Toast.makeText(ChooseCityActivity.this,"离线定位成功，离线定位结果也是有效的",Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(ChooseCityActivity.this,"定位失败",Toast.LENGTH_SHORT).show();
                    mShuaXin.setVisibility(View.VISIBLE);
                }

            }
        };

        mLocationClient.registerLocationListener(mBDLocationListener);    //注册监听函数
        mShuaXin.setOnClickListener(this);
    }


    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span=1000;
        option.setScanSpan(0);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(false);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    @Override
    protected void getData() {

    }

    @Override
    protected void setListener() {
        //城市搜索按钮
        setBaseCitySearchListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ChooseCityActivity.this, "点击搜索", Toast.LENGTH_SHORT).show();
            }
        });

        //点击定位城市
        mLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIntExtra==1){//表示是重新选择城市的
                    Intent intent=new Intent();
                    intent.putExtra("city",mLocation.getText().toString());
                    setResult(Const.PCTAG,intent);
                    finish();
                }else {//登录注册普通流程
                    Intent intent=new Intent(ChooseCityActivity.this,ChoosePlaceActivity.class);
                    intent.putExtra("city",mLocation.getText().toString());
                    startActivity(intent);
                }

            }
        });
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_choose_city;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLocationClient.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.unRegisterLocationListener(mBDLocationListener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.shuanxin:
                mLocationClient.stop();
                mLocationClient.start();
                mShuaXin.startAnimation(AnimationUtils.loadAnimation(ChooseCityActivity.this, R.anim.xuanzhuan));
            break;
            default:
                break;
        }
    }
}
