package com.kuaiyou.rainview.model;

import android.content.Context;
import android.graphics.BitmapFactory;

import com.kuaiyou.rainview.R;

public class ImageReaderDefalut extends ImageReader{

    public ImageReaderDefalut(Context context, String path) {
        super(context, path);
    }

    @Override
    protected void initImage() {
//        mStandardBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.img_red_packet_1);
        mStandardBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.snowflake);
    }

    @Override
    public float getImageScale() {
        return mStandardBitmap.getHeight() / mStandardBitmap.getWidth();
    }

    /**
     * @return
     */
    @Override
    public int getImageHeight() {
        return judgeImage(mStandardBitmap.getHeight());
    }

    private int judgeImage(int px) {
        if (px > 1000)
            return judgeImage(px /10);
        return px;
    }

    @Override
    public int getImageWidth() {
        return judgeImage(mStandardBitmap.getWidth());
    }

    @Override
    public float getImageDensity() {
        return mStandardBitmap.getDensity();
    }
}
