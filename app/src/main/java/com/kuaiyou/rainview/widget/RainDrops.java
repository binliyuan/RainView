package com.kuaiyou.rainview.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.kuaiyou.rainview.manager.RainInfo;
import com.kuaiyou.rainview.manager.RainManager;
import com.kuaiyou.rainview.model.ImageReader;

public class RainDrops extends View {
    private static String TAG = "RainDrops";

    private Bitmap mImage;

    private RainInfo mRainInfo;

    public RainInfo getRainInfo() {
        return mRainInfo;
    }

    private int top;

    private RelativeLayout.LayoutParams layoutParams;

    public RainDrops(Context context, ImageReader imageReader, RainInfo rainInfo) {
        super(context);
        mImage = imageReader.getmStandardBitmap();
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
        canvas.drawBitmap(mImage, null, mRect, null);
//        Log.d(TAG, "onDraw: ");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mRainInfo.getRainInterface().onRainClick();
        return super.onTouchEvent(event);
    }
}
