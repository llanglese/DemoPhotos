package demo.com.android.photos;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * Created by keynes-os on 2018/10/9.
 */

public class MyPileView extends ViewGroup {

    protected float vertivalSpace;//垂直间隙
    protected float pileWidth=0;//重叠宽度

    public MyPileView(Context context) {
        this(context, null, 0);
    }

    public MyPileView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyPileView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
    }

    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyPileLayout);
        vertivalSpace = ta.getDimension(R.styleable.MyPileLayout_MyPileLayout_vertivalSpace, dp2px(4));
        pileWidth = ta.getDimension(R.styleable.MyPileLayout_MyPileLayout_pileWidth, dp2px(10));
        ta.recycle();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        //AT_MOST
        int width = 0;
        int height = 0;
        int rawWidth = 100;//当前行总宽度
        int rawHeight = 0;// 当前行高

        int rowIndex = 0;//当前行位置
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if(child.getVisibility() == GONE){
                if(i == count - 1){
                    //最后一个child
                    height += rawHeight;
                    width = Math.max(width, rawWidth);
                }
                continue;
            }

            //调用measureChildWithMargins 而不是measureChild
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

            int childWidth = child.getMeasuredWidth()  + lp.leftMargin + lp.rightMargin;
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            rawWidth += childWidth;
            if(rowIndex > 0){
                rawWidth -= pileWidth;
            }
            rawHeight = Math.max(rawHeight, childHeight);

            if(i == count - 1){
                width = Math.max(rawWidth, width);
                height += rawHeight;
            }

            rowIndex++;
        }

        setMeasuredDimension(
                widthSpecMode == MeasureSpec.EXACTLY ? widthSpecSize : width + getPaddingLeft() + getPaddingRight(),
                heightSpecMode == MeasureSpec.EXACTLY ? heightSpecSize : height + getPaddingTop() + getPaddingBottom()
        );
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.e("YYYYYY", "onLayout");
        int viewWidth = r - l;
        int leftOffset = getPaddingLeft();
        int topOffset = getPaddingTop();
        int rowMaxHeight = 0;
        int rowIndex = 0;//当前行位置
        View childView;
        for( int w = 0, count = getChildCount(); w < count; w++ ) {
            Log.e("YYYYYY", "count = " + count);
            if (w >= count - 4) {
                childView = getChildAt(w);
                if (childView.getVisibility() == GONE) continue;

                MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();
//                // 如果加上当前子View的宽度后超过了ViewGroup的宽度，就换行
                int occupyWidth = lp.leftMargin + childView.getMeasuredWidth()/2 + lp.rightMargin;

                int left = leftOffset + lp.leftMargin;
                int top = topOffset + lp.topMargin;
                int right = leftOffset + lp.leftMargin + childView.getMeasuredWidth();
                int bottom = topOffset + lp.topMargin + childView.getMeasuredHeight();
                childView.layout(left, top, right, bottom);
                if(count > 3) {
                    if (w == count - 1) {
                        translateAnimationNew(childView, lp.leftMargin + occupyWidth, -occupyWidth);
                    } else if (w >= count - 3) {
                        translateAnimation(childView, lp.leftMargin, -occupyWidth );
                    } else if (w == count - 4) {
                        alphaAnimation(childView);
                    }
                }
                // 横向偏移
                leftOffset += occupyWidth;
                // 试图更新本行最高View的高度
                int occupyHeight = lp.topMargin + childView.getMeasuredHeight() + lp.bottomMargin;
                if (rowIndex != count - 1) {
                    leftOffset -= pileWidth;
                }
                rowMaxHeight = Math.max(rowMaxHeight, occupyHeight);
                rowIndex++;
            }
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    public float dp2px(float dpValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
    }

    private void translateAnimation(View view, float fromX, float toX) {
        float with = view.getWidth();
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.ABSOLUTE, fromX,
                Animation.ABSOLUTE, toX, Animation.ABSOLUTE, 0.0f, Animation.ABSOLUTE, 0f);
        translateAnimation.setDuration(800);
        view.startAnimation(translateAnimation);
    }

    private void translateAnimationNew(View view, float fromX, float toX) {
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.ABSOLUTE, fromX + 20,
                Animation.ABSOLUTE, toX, Animation.ABSOLUTE, 0.0f, Animation.ABSOLUTE, 0f);
        translateAnimation.setDuration(800);
        view.startAnimation(translateAnimation);
    }

    private void alphaAnimation(final View view) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0f);
        alphaAnimation.setDuration(800);
        view.startAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                removeView(getChildAt(0));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
