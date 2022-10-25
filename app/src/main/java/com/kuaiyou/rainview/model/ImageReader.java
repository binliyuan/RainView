package com.kuaiyou.rainview.model;

import android.content.Context;
import android.graphics.Bitmap;

public abstract class ImageReader {

    protected Context mContext;
    protected String ImagePath;
    protected Bitmap mStandardBitmap;

    protected abstract void initImage();

    public abstract float getImageScale();

    public abstract int getImageHeight();

    public abstract int getImageWidth();

    public abstract float getImageDensity();

    public ImageReader(Context context, String path) {
        this.mContext = context;
        this.ImagePath = path;
        initImage();
    }

    public Bitmap getmStandardBitmap() {
        return mStandardBitmap;
    }
}
