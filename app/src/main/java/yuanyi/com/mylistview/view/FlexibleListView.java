package yuanyi.com.mylistview.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ListView;

import yuanyi.com.mylistview.base.BaseApp;

/**
 * Created by admin on 2016/6/6.
 */
public class FlexibleListView extends ListView {
    private View mActionBar;        // 需要渐变透明度的ActionBar
    private View mHeaderView;       // listView的HeaderView
    private int mMaxScrollHeight;   // headerView的高度
    private float growthFactor = 3.0F;      // 增长因子
    private Drawable mActionBarBackground;  // actionBar 的背景

    public FlexibleListView(Context context) {
        super(context);
    }

    public FlexibleListView(Context context, AttributeSet attrs) {
        super(context, attrs);;
    }

    public FlexibleListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * 复写父类方法, 目的是为了得到listView的第一个HeaderView
     * 同时获得HeaderView的高度
     * */
    @Override
    public void addHeaderView(View v, Object data, boolean isSelectable) {
        super.addHeaderView(v, data, isSelectable);
        if (mHeaderView == null) {
            mHeaderView = v;
            mMaxScrollHeight = mHeaderView.getLayoutParams().height;
            BaseApp.log("mMaxScrollHeight --> " + mMaxScrollHeight);
        }
    }

    /**
     * 滑动listView时调用, 用onScrollListener中的onScroll方法实现也行
     * 滑动时根据HeaderView在屏幕中位置设置actionBar的透明度
     * */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mActionBarBackground != null) {
            mActionBarBackground.setAlpha(evaluateAlpha(Math.abs(mHeaderView.getTop())));
        }
    }

    /**
     * 复写父类的方法， 目的是为了在ListView滑动到了顶部在继续滑动时
     * 做一些事情的处理
     * */
    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        if (mHeaderView != null) {
            if (isTouchEvent && deltaY < 0) {
                mHeaderView.getLayoutParams().height += Math.abs(deltaY/growthFactor);
                mHeaderView.requestLayout();
            }
        }
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mHeaderView != null) {
            int action = ev.getAction();
            if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
                resetHeaderViewHeight();
            }
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 复原HeaderView的状态
     * */
    private void resetHeaderViewHeight() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(1);
        valueAnimator.setDuration(700);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float f = animation.getAnimatedFraction();
                mHeaderView.getLayoutParams().height -= f * (mHeaderView.getLayoutParams().height - mMaxScrollHeight);
                mHeaderView.requestLayout();

            }
        });
        valueAnimator.setInterpolator(new OvershootInterpolator());
        valueAnimator.start();
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
    }

    /**
     * 根据指定值计算透明度
     * */
    private int evaluateAlpha(int t) {
        // 完全在屏幕外
        if (t >= mMaxScrollHeight) {
            return 255;
        }

        return (int) (255 * t / (float) mMaxScrollHeight);
    }

    /**
     * 与此ListView关联的ActionBar
     * */
    public void bindActionBar(View actionBar) {
        if (actionBar != null) {
            mActionBar = actionBar;
            mActionBarBackground = mActionBar.getBackground();
            if (mActionBarBackground == null) {
                mActionBarBackground = new ColorDrawable(Color.TRANSPARENT);
            }
            mActionBarBackground.setAlpha(0);
            if(Build.VERSION.SDK_INT >= 16) {
                mActionBar.setBackground(mActionBarBackground);
            } else {
                mActionBar.setBackgroundDrawable(mActionBarBackground);
            }
        }
    }
}
