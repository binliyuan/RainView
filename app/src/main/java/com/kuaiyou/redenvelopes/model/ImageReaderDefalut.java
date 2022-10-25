package com.kuaiyou.redenvelopes.model;

import android.content.Context;
import android.graphics.BitmapFactory;

import com.kuaiyou.redenvelopes.R;

public class ImageReaderDefalut extends ImageReader{

    public ImageReaderDefalut(Context context, String path) {
        super(context, path);
    }

    @Override
    protected void initImage() {
        mStandardBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.img_red_packet_1);
    }

    @Override
    public float getImageScale() {
        return mStandardBitmap.getHeight() / mStandardBitmap.getWidth();
    }

    @Override
    public int getImageHeight() {
        return mStandardBitmap.getHeight();
    }

    @Override
    public int getImageWidth() {
        return mStandardBitmap.getHeight();
    }

    @Override
    public float getImageDensity() {
        return mStandardBitmap.getDensity();
    }
}
