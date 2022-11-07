package com.kuaiyou.rainview.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.kuaiyou.rainview.manager.RainInfo;
import com.kuaiyou.rainview.manager.RainManager;
import com.kuaiyou.rainview.model.ImageReader;

import java.util.Timer;
import java.util.TimerTask;

public class RainDrops extends View {
    private static String TAG = "RainDrops";

    private Bitmap mImage[];

    private int mImagelength;

    private int mImagePox;

    private RainInfo mRainInfo;

    private GifDealyThread gifDealyThread;

    public RainInfo getRainInfo() {
        return mRainInfo;
    }

    private int top;

    private RelativeLayout.LayoutParams layoutParams;

    public RainDrops(Context context, ImageReader imageReader, RainInfo rainInfo) {
        super(context);
        mImage = imageReader.getmStandardBitmap();
        mImagelength = mImage.length;
        gifDealyThread = new GifDealyThread(mImagelength);
        gifDealyThread.start();
        mRainInfo = rainInfo;
//        setBackgroundColor(Color.GREEN);
        layoutParams = new RelativeLayout.LayoutParams(mRainInfo.getSize().getWidth(), mRainInfo.getSize().getHeight());
        setLayoutParams(layoutParams);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        layoutParams.leftMargin = mRainInfo.getLeft();
        layoutParams.topMargin = mRainInfo.getTop();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
//        Log.d(TAG, "onLayout: ");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect mRect = new Rect();
        mRect.set(0, 0, mRainInfo.getSize().getWidth(), mRainInfo.getSize().getHeight());
        mImagePox = gifDealyThread.getImagePox();
//        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        Log.d(TAG, "onDraw: this" + this.hashCode() + "mImagePox: " + mImagePox + "mImagelength: " + mImagelength);

        canvas.drawBitmap(mImage[mImagePox], null, mRect, null);

        Log.d(TAG, "onDraw: this" + this.hashCode() + "mImagePox: " + mImagePox);
        requestLayout();
        postInvalidate();
//        Log.d(TAG, "onDraw: ");
    }

    public void Destroy() {
        if (gifDealyThread != null) {
            gifDealyThread.stop();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mRainInfo.getRainInterface().onRainClick();
        return super.onTouchEvent(event);
    }

    class GifDealyThread extends Thread {
        private int mImagePox;

        private int mImageLength;

        public GifDealyThread(int mImageLength) {
            super();
            this.mImageLength = mImageLength;
        }

        @Override
        public void run() {
            super.run();
            new Timer("timer - ").schedule(new TimerTask() {
                @Override
                public void run() {
                    mImagePox++;
                    if (mImagePox >= mImagelength - 1)
                        mImagePox = 0;
                    Log.d(TAG, "run: this" + this.hashCode() + "mImagePox: " + mImagePox);
                }
            }, 100, 100);
        }

        public int getImagePox() {
            return mImagePox;
        }

        public void setImagePox(int mImagePox) {
            Log.d(TAG, "setImagePox: this" + this.hashCode() + "mImagePox: " + mImagePox);
            this.mImagePox = mImagePox;
        }
    }
}
