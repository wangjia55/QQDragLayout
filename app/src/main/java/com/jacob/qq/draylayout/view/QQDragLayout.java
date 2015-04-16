package com.jacob.qq.draylayout.view;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.jacob.qq.draylayout.R;

/**
 * Created by jacob-wj on 2015/4/16.
 */
public class QQDragLayout extends RelativeLayout {
    public static final int AUTO_MIN_SETTLE_SPEED = 800;

    private ViewDragHelper mViewDragHelper;

    private int mDragRange;

    private int mDragLeftBorder;

    private RelativeLayout mRelativeMenu;

    private RelativeLayout mRelativeMain;

    private State mState;

    private int mScreenWidth;


    public QQDragLayout(Context context) {
        this(context, null);
    }

    public QQDragLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QQDragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mState = State.close;
    }

    enum State {
        open,
        close,
        drag
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mRelativeMain = (RelativeLayout) findViewById(R.id.relative_main);
        mRelativeMenu = (RelativeLayout) findViewById(R.id.relative_left);
        mViewDragHelper = ViewDragHelper.create(this, 1f, new ViewDragCallBack());

    }

    private class ViewDragCallBack extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View view, int i) {
            return R.id.relative_main == view.getId();
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            mDragLeftBorder = left;
            super.onViewPositionChanged(changedView, left, top, dx, dy);
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            int paddingLeft = child.getPaddingLeft();
            if (left < paddingLeft) {
                return paddingLeft;
            }

            if (left > mDragRange) {
                return mDragRange;
            }
            return left;
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return mDragRange;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            Log.e("TAG","xvel:"+xvel+"+++yvel:"+yvel);
            if (mDragLeftBorder == 0){
                mState = State.close;
                return;
            }

            if (mDragLeftBorder == mDragRange){
                mState = State.open;
                return;
            }

            int  checkRange = mDragRange;
            boolean settleToOpen = false;
            if (xvel > AUTO_MIN_SETTLE_SPEED){
                settleToOpen = true;
            }else if(xvel <-AUTO_MIN_SETTLE_SPEED){
                settleToOpen = false;
            }else if(mDragLeftBorder > checkRange/2){
                settleToOpen = true;
            }else if(mDragLeftBorder < checkRange/2){
                settleToOpen = false;
            }

            int destX = settleToOpen? checkRange:0;

            if (mViewDragHelper.settleCapturedViewAt(destX,0)){
                ViewCompat.postInvalidateOnAnimation(QQDragLayout.this);
            }


        }

        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mScreenWidth = r;
        mDragRange = (int) (mScreenWidth * 0.75);
    }

    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
        super.computeScroll();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mViewDragHelper.cancel();
                break;
        }

        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }
}
