package com.kuaiyou.rainview.manager;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Size;
import android.view.View;
import android.view.ViewGroup;

import com.kuaiyou.rainview.model.ImageReader;
import com.kuaiyou.rainview.model.ImageReaderDefalut;
import com.kuaiyou.rainview.model.ImageReaderNet;
import com.kuaiyou.rainview.widget.RainDrops;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RainManager {
    private static String TAG = "RainManager";

    private ViewGroup mViewGroup;

    private ImageReader mImageReader;

    private Context mContext;

    private List mRainList;

    private RainInterface mRainCallback;

    public interface RainInterface {
        void onRainStart();

        void onRaining();

        void onRainClick();

        void onRainEnd();
    }

    public RainManager(Context context, ViewGroup viewGroup, int type) {
        mViewGroup = viewGroup;
        mContext = context;
        ImageReaderInit(type);
    }

    public void setRainCallback(RainInterface rainCallback) {
        this.mRainCallback = rainCallback;
    }

    public void rain() {
        mRainList = acquireRainList(100);
        linktoUI();
    }

    private void linktoUI() {
        for (Object rainDrops : mRainList) {
            mViewGroup.addView((View) rainDrops);
        }
    }

    private void ImageReaderInit(final int type) {
        switch (type) {
            case RainInfo.RAIN_TYPE_DEFAULT:
                mImageReader = new ImageReaderDefalut(mContext, null);
                break;
            case RainInfo.RAIN_TYPE_NET:
                mImageReader = new ImageReaderNet(mContext, null);
                break;
            default:
                Log.d(TAG, "no rain type");
                break;
        }
    }

    /**
     * 可以放到Layout里面动态生成，减少初始化的消耗
     * @param count 生成的雨点个数
     * @return
     */
    private List acquireRainList(int count) {
        List list = new ArrayList<RainDrops>();
        RainInfo rainInfoPointer = null;
        for (int i = 0; i < count; i++) {
            RainInfo rainInfo = new RainInfo(new Size(mImageReader.getImageWidth(),
                    mImageReader.getImageHeight()), i);
            rainInfo.setSpeed(randomCoordinate(5)+5);
            rainInfo.setRainInterface(mRainCallback);
            if (rainInfoPointer != null) {
                rainInfo.setPrevious(rainInfoPointer);
                rainInfoPointer = rainInfo;
            } else {
                rainInfo.setHead(true);
                rainInfoPointer = rainInfo;
            }
            dispersRain(rainInfo, DisplayWidth());
            list.add(new RainDrops(mContext, mImageReader, rainInfo));
        }
        return list;
    }

    private void dispersRain(RainInfo rainInfo, int displayWidth) {
        if (rainInfo.getHead()) {
            rainInfo.setTop(rainInfo.getTop() - mImageReader.getImageHeight());
            Log.d(TAG, "ImageWidth area: " + mImageReader.getImageWidth());
            int point = randomCoordinate(displayWidth - mImageReader.getImageWidth());
            rainInfo.setRainSection(point, point + mImageReader.getImageWidth());
            rainInfo.setLeft(point);
            if (point - 0 >= mImageReader.getImageWidth()) {
                int[] area = new int[2];
                area[0] = 0;
                area[1] = point;
                Log.d(TAG, "area: " + area[0] + "area:" + area[1]);
                rainInfo.addSatisfactionArea(area);
            }
            if (displayWidth - point - mImageReader.getImageWidth() > mImageReader.getImageWidth()) {
                int[] area = new int[2];
                area[0] = point + mImageReader.getImageWidth();
                area[1] = displayWidth;
                Log.d(TAG, "area1: " + area[0] + "area1:" + area[1]);
                rainInfo.addSatisfactionArea(area);
            }
        } else {
            rainInfo.setTop(rainInfo.getTop() - mImageReader.getImageHeight() - rainInfo.getId() * 500);
            int point = DetermineScopes(rainInfo);
            if (point == 0) {
                point = randomCoordinate(displayWidth);
                rainInfo.setRainSection(point, point + mImageReader.getImageWidth());
                rainInfo.setLeft(point);
            } else {
                rainInfo.setRainSection(point, point + mImageReader.getImageWidth());
                rainInfo.setLeft(point);
            }
        }
    }

    private int calcRainGroupsMaxCount() {
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels / mImageReader.getImageHeight();
    }

    private int DisplayWidth() {
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    /**
     * @param rainInfo 每一个雨点的信息
     * @return
     */
    private int DetermineScopes(RainInfo rainInfo) {
        Log.d(TAG, "DetermineScopes: ");
        int Scopes = 0;
        RainInfo previous = rainInfo.getPrevious();
        List list = previous.satisfactionArea;
        if (list.size() == 0) {
            int point = randomCoordinate(DisplayWidth() - mImageReader.getImageWidth());
            Log.d(TAG,"head point: " + point);
            rainInfo.setRainSection(point, point + mImageReader.getImageWidth());
            rainInfo.setLeft(point);
            if (point - 0 >= mImageReader.getImageWidth()) {
                int[] area = new int[2];
                area[0] = 0;
                area[1] = point;
                rainInfo.addSatisfactionArea(area);
            }
            if (DisplayWidth() - point - mImageReader.getImageWidth() > mImageReader.getImageWidth()) {
                int[] area = new int[2];
                area[0] = point + mImageReader.getImageWidth();
                area[1] = DisplayWidth();
                rainInfo.addSatisfactionArea(area);
            }
            return point;
        }
        Random random = new Random();
        int randomArea = random.nextInt(list.size());
        int[] area = (int[]) list.get(randomArea);
        list.remove(randomArea);
        int point = random.nextInt(area[1] - area[0]-mImageReader.getImageWidth()) + area[0];
        Scopes = point;
        Log.d(TAG,"point: " + point);
        rainInfo.addSatisfactionArea(list);
        if (point - 0 >= mImageReader.getImageWidth() + area[0]) {
            int[] area1 = new int[2];
            area1[0] = 0;
            area1[1] = point;
            rainInfo.addSatisfactionArea(area1);
        }
        if (area[1] - point - mImageReader.getImageWidth() > mImageReader.getImageWidth()) {
            int[] area1 = new int[2];
            area1[0] = point + mImageReader.getImageWidth();
            area1[1] = area[1];
            rainInfo.addSatisfactionArea(area1);
        }
        return Scopes;
    }

    private int randomCoordinate(int range) {
        Random r = new Random();
        return r.nextInt(range);
    }
}
