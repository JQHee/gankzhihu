package com.peakmain.gankzhihu.ui.activity;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.peakmain.baselibrary.banner.BannerAdapter;
import com.peakmain.baselibrary.banner.BannerView;
import com.peakmain.baselibrary.navigationbar.DefaultNavigationBar;
import com.peakmain.gankzhihu.R;
import com.peakmain.gankzhihu.adapter.ChannelAdapter;
import com.peakmain.gankzhihu.base.BaseActivity;
import com.peakmain.gankzhihu.bean.pointplay.Channel;

import butterknife.BindView;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/26 0026 上午 11:02
 * mail : 2726449200@qq.com
 * describe ：点播
 */
@Route(path = "/activity/PointPlayActivity")
public class PointPlayActivity extends BaseActivity {
    @BindView(R.id.banner_view)
    BannerView mBannerView;
    @BindView(R.id.gv_channel)
    GridView mGridView;
    //描述
    private int[] mDes = new int[]{
            R.string.a_name,
            R.string.b_name,
            R.string.c_name,
            R.string.d_name,
    };
    //图片集合
    private int[] mImg = new int[]{
            R.drawable.a,
            R.drawable.b,
            R.drawable.c,
            R.drawable.d,
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_point_play;
    }

    @Override
    protected void initInjector() {
        ARouter.getInstance().inject(this);
    }

    @Override
    protected void initView() {
        initToolBar();
        initBanner();
        initData();
    }

    private void initData() {
        mGridView.setAdapter(new ChannelAdapter(this));
        mGridView.setOnItemClickListener((parent, view, position, id) -> {
            switch (position) {
                case 6:
                    //跳转直播
                    ARouter.getInstance().build("/activity/LiveActivity")
                            .navigation();
                    break;
                default:
                    //跳转对应频道
                    ARouter.getInstance().build("/activity/detailListActivity")
                            .withInt("ChannelPosition",position+1)
                            .navigation();
                    break;
            }
        });
    }

    /**
     * 初始化顶部的轮播图
     */
    private void initBanner() {
        mBannerView.setAdapter(new BannerAdapter() {
            @Override
            public View getView(int position, View convertView) {
                if (convertView == null) {
                    convertView = new ImageView(PointPlayActivity.this);
                }
                ((ImageView) convertView).setScaleType(ImageView.ScaleType.CENTER_CROP);

                Glide.with(PointPlayActivity.this).load(mImg[position]).into((ImageView) convertView);
                return convertView;
            }

            @Override
            public int getCount() {
                return mImg.length;
            }

            @Override
            public String getBannerDesc(int position) {
                return getResources().getString(mDes[position]);
            }
        });
    }

    /**
     * 初始化toolbar
     */
    private void initToolBar() {
        ViewGroup parent = findViewById(R.id.activity_point);
        DefaultNavigationBar navigationBar = new DefaultNavigationBar
                .Builder(this, parent)
                .setLeftText("点播")
                .create();
        Toolbar toolbar = navigationBar.findViewById(com.peakmain.baselibrary.R.id.view_root);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
