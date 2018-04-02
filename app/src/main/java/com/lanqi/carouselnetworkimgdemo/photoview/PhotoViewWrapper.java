package com.lanqi.carouselnetworkimgdemo.photoview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.lanqi.carouselnetworkimgdemo.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


public class PhotoViewWrapper extends RelativeLayout {
    private static final String TAG = "lq";
    protected View loadingDialog;

    protected PhotoView photoView;

    protected Context mContext;

    public PhotoViewWrapper(Context ctx) {
        super(ctx);
        mContext = ctx;
        init();
    }

    public PhotoViewWrapper(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
        mContext = ctx;
        init();
    }

    public PhotoView getImageView() {
        return photoView;
    }

    protected void init() {
        photoView = new PhotoView(mContext);
        photoView.enable();
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        photoView.setLayoutParams(params);
        this.addView(photoView);
        photoView.setVisibility(GONE);

        loadingDialog = LayoutInflater.from(mContext).inflate(R.layout.photo_view_zoom_progress, null);
        loadingDialog.setLayoutParams(params);
        this.addView(loadingDialog);
    }

    public void setImgId(int imgId) {
        if (imgId != 0) {
            photoView.setImageResource(imgId);
            loadingDialog.setVisibility(View.GONE);
            photoView.setVisibility(VISIBLE);
        }
    }

    public void setUrl(String imageUrl) {
        Log.i(TAG, "setUrl: == "+imageUrl);
        Picasso.with(mContext).load(imageUrl)
                .placeholder(R.drawable.default_image)
                .into(photoView, new Callback() {
                    @Override
                    public void onSuccess() {
                        loadingDialog.setVisibility(View.GONE);
                        photoView.setVisibility(VISIBLE);
                    }

                    @Override
                    public void onError() {
                        loadingDialog.setVisibility(View.GONE);
                        photoView.setVisibility(VISIBLE);
                        photoView.setBackgroundResource(R.drawable.default_image);
                    }
                });
    }

}