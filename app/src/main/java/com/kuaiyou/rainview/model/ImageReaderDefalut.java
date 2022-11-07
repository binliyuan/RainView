package com.kuaiyou.rainview.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;

import com.kuaiyou.rainview.R;
import com.kuaiyou.rainview.model.gif.GifDecoder;
import com.kuaiyou.rainview.model.gif.GifHeader;
import com.kuaiyou.rainview.model.gif.GifHeaderParser;
import com.kuaiyou.rainview.model.gif.StandardGifDecoder;
import com.kuaiyou.rainview.model.gif.TestUtil;

import java.io.IOException;
import java.io.InputStream;

public class ImageReaderDefalut extends ImageReader{

    public ImageReaderDefalut(Context context, String path) {
        super(context, path);
    }

    @Override
    protected void initImage() {
//        mStandardBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.img_red_packet_1);
//        mStandardBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.snowflake);
        try {
            mStandardBitmap = getGifBitmap();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public float getImageScale() {
        return mStandardBitmap[0].getHeight() / mStandardBitmap[0].getWidth();
    }

    /**
     * @return
     */
    @Override
    public int getImageHeight() {
        return judgeImage(mStandardBitmap[0].getHeight());
    }

    private int judgeImage(int px) {
        if (px > 1000)
            return judgeImage(px /10);
        return px;
    }

    @Override
    public int getImageWidth() {
        return judgeImage(mStandardBitmap[0].getWidth());
    }

    @Override
    public float getImageDensity() {
        return mStandardBitmap[0].getDensity();
    }

    private Bitmap[] getGifBitmap() throws IOException {
        InputStream is = mContext.getResources().getAssets()
                .open("partial_gif_decode.gif");
        MockProvider provider = new MockProvider();
        byte[] data = TestUtil.isToBytes(is);
        GifHeaderParser headerParser = new GifHeaderParser();
        headerParser.setData(data);
        GifHeader header = headerParser.parseHeader();
        GifDecoder decoder = new StandardGifDecoder(provider);
        decoder.setData(header, data);
        Bitmap bitmap[] = new Bitmap[decoder.getFrameCount()];
        for (int i = 0; i < bitmap.length; i++) {
            decoder.advance();
            bitmap[i] = decoder.getNextFrame();
        }
        return bitmap;
    }

    private static class MockProvider implements GifDecoder.BitmapProvider {

        @NonNull
        @Override
        public Bitmap obtain(int width, int height, Bitmap.Config config) {
            Bitmap result = Bitmap.createBitmap(width, height, config);
            return result;
        }

        @Override
        public void release(@NonNull Bitmap bitmap) {
            // Do nothing.
        }

        @NonNull
        @Override
        public byte[] obtainByteArray(int size) {
            return new byte[size];
        }

        @Override
        public void release(@NonNull byte[] bytes) {
            // Do nothing.
        }

        @NonNull
        @Override
        public int[] obtainIntArray(int size) {
            return new int[size];
        }

        @Override
        public void release(@NonNull int[] array) {
            // Do Nothing
        }

    }
}
