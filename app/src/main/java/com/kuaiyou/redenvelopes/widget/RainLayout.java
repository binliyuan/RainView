package com.kuaiyou.redenvelopes.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.kuaiyou.redenvelopes.manager.RainInfo;

public class RainLayout extends RelativeLayout {

    private static String TAG = "RainLayout";

    public RainLayout(Context context) {
        super(context);
        setBackgroundColor(Color.RED);
    }

    @Override
    public void addView(View child) {
        Log.d(TAG, "addView: ");
        super.addView(child);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        for (int i = 0; i < getChildCount(); i++) {
            RainInfo rainInfo = getChildAt(i) instanceof RainDrops ?
                    ((RainDrops) getChildAt(i)).getRainInfo() : null;
            if (rainInfo != null) {
                rainInfo.setTop(rainInfo.getTop() + 1 * rainInfo.getSpeed());
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        Log.d(TAG, "onDraw: ");
        super.onDraw(canvas);
        // 更新该viewGroub之下的界面
        requestLayout();
        postInvalidate();
        //
    }
}
