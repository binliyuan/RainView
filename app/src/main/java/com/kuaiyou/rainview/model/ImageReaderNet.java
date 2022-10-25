package com.kuaiyou.rainview.model;

import android.content.Context;

public class ImageReaderNet extends ImageReader{


    public ImageReaderNet(Context context, String path) {
        super(context, path);
    }

    @Override
    protected void initImage() {

    }

    @Override
    public float getImageScale() {
        return 0;
    }

    @Override
    public int getImageHeight() {
        return 0;
    }

    @Override
    public int getImageWidth() {
        return 0;
    }

    @Override
    public float getImageDensity() {
        return 0;
    }
}
