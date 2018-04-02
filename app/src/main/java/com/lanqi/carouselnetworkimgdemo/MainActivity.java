package com.lanqi.carouselnetworkimgdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 轮播图图片来源  网络请求
 * 图片地址  http://pic.nipic.com/2008-07-11/20087119630716_2.jpg
 */

public class MainActivity extends FragmentActivity {
    private Activity mActivity;
    private GalleryPagerAdapter galleryAdapter;
    private AutoLoopViewPager mPager;
    private CirclePageIndicator mIndicator;
    // 2018/3/31      //图片列表，需要几张放几张就可以了
    private List<String> imageList = new ArrayList<String>(Arrays.asList(
            "http://pic.nipic.com/2008-07-11/20087119630716_2.jpg",
            "http://pic.nipic.com/2008-07-11/20087119630716_.jpg",
            "http://pic.nipic.com/2008-07-11/20087119630716_2.jpg"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity = this;
        initView();

    }

    private void initView() {
        mPager = findViewById(R.id.pager);
        mIndicator = findViewById(R.id.indicator);

        galleryAdapter = new GalleryPagerAdapter();
        mPager.setAdapter(galleryAdapter);
        mIndicator.setViewPager(mPager);
        mIndicator.setPadding(5, 5, 10, 5);
    }

    //轮播图适配器
    public class GalleryPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            final ImageView item = new ImageView(mActivity);
            Picasso.with(mActivity).load(imageList.get(position))
                    .placeholder(R.drawable.default_image)
                    .into(item, new Callback() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onError() {
                            item.setBackgroundResource(R.drawable.default_image);
                        }
                    });

            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(-1, -1);
            item.setLayoutParams(params);
            item.setScaleType(ImageView.ScaleType.FIT_XY);
            container.addView(item);

            final int pos = position;
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mActivity, ImageGalleryActivity.class);
                    //将图片 url 传递到2级页面。
                    intent.putStringArrayListExtra("images", (ArrayList<String>) imageList);
                    intent.putExtra("position", pos);
                    startActivity(intent);
                }
            });

            return item;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //开启播放
        mPager.startAutoScroll();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //停止播放
        mPager.stopAutoScroll();
    }


}
