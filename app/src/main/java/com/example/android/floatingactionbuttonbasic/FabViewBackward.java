package com.example.android.floatingactionbuttonbasic;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.ImageView;
import com.example.android.common.logger.Log;


/**
 * This class is intended to imitate the Material Design Fab Button for earlier releases
 * It uses simpler animation and does not have any elevation or outline provider
 */
public class FabViewBackward extends ImageView implements Checkable, FabView.OnCheckedChangeListener {
    private static String LOG_TAG = FabViewBackward.class.getSimpleName();
    /**
     * A boolean that tells if the FAB is checked or not.
     */
    protected boolean mChecked = false;
    /**
     * A listener to communicate that the FAB has changed its state.
     */
    private FabView.OnCheckedChangeListener mOnCheckedChangeListener;

    //Initialize the object variables
    private Paint mCirclePaint;
    private Paint mStrokePaint;
    private RectF mCircleArc;
    private int mCircleRadius;

    private int mCircleSelectColor;
    private int mCircleDefColor;
    private int mCircleStrokeColor;
    private int mCircleStrokeWidth;
    private int mDrawableHeight;
    private int mDrawableWidth;
    private int mOffsetLeft;
    private int mOffsetTop;
    private Drawable mDrawable;


    //Default values
    private static final int DEFAULT_RADIUS = 0;

    private static final int DEFAULT_STROKE_COLOR = Color.BLUE;
    private static final int DEFAULT_STROKE_WIDTH = 2;

    //Default FAB sizes according to the material design
    //docs: http://www.google.com/design/spec/components/buttons.html#buttons-main-buttons

    private int DEFAULT_FAB_SIZE = 96;
    private int DEFAULT_FAB_MINI_SIZE = 40;

    private static final int FAB_SIZE_NORMAL = 0;
    private static final int FAB_SIZE_MINI= 1;

    public FabViewBackward(Context context, AttributeSet attrs) {
        super(context, attrs);
        //Read all the attributes
        init(attrs);
    }

    public FabViewBackward(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(1, 1);
        canvas.drawArc(mCircleArc, 0, 360, true, mCirclePaint);
        canvas.drawArc(mCircleArc, 0, 360, true, mStrokePaint);
        if (mDrawable != null) {
            //left top right bottom
            mDrawable.setBounds(mOffsetLeft,mOffsetTop,mDrawableWidth+mOffsetLeft,
                    mDrawableHeight + mOffsetTop);
            mDrawable.draw(canvas);
        }

    }

    /**
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int measuredWidth = measureWidth(widthMeasureSpec);

        //We are using this method to provide a default size based on the layout size
        if(mCircleRadius == 0)
        {
            mCircleRadius = measuredWidth/2;
            int tempRadiusHeight = measureHeight(heightMeasureSpec)/2;
            if(tempRadiusHeight < mCircleRadius)
            {
                mCircleRadius = tempRadiusHeight;
            }
        }

        int circleDiameter = mCircleRadius * 2 - mCircleStrokeWidth;
        mCircleArc = new RectF(0, 0, circleDiameter, circleDiameter);
        int measuredHeight = measureHeight(heightMeasureSpec);
        setMeasuredDimension(measuredWidth, measuredHeight);

    }


    /**
     * measure the height of the screen
     * @param measureSpec
     * @return
     */
    private int measureHeight(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        int result = 0;
        if (specMode == MeasureSpec.AT_MOST) {
            result = mCircleRadius * 2;
        } else if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        }
        return result;
    }


    /**
     * measure the width of the screen
     * @param measureSpec
     * @return
     */
    private int measureWidth(int measureSpec)
    {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        int result = 0;
        if (specMode == MeasureSpec.AT_MOST) {
            result = mCircleRadius * 2;
        } else if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        }
        return result;
    }


    /**
     * initialize all the custom attributes
     * TODO - some of the paint is not supported by attributes
     * @param attrs
     */
    public void init(AttributeSet attrs) {
        //Initialize the stroke and paint objects

        //Convert the fab sizes to DP
        final int mScreenDensity = (int)getResources().getDisplayMetrics().density;

        DEFAULT_FAB_SIZE = DEFAULT_FAB_SIZE * (int)mScreenDensity;
        DEFAULT_FAB_MINI_SIZE = DEFAULT_FAB_MINI_SIZE * (int)mScreenDensity;

        TypedArray attrsArray = getContext().obtainStyledAttributes(attrs, R.styleable.fab);
        mCircleRadius = attrsArray.getInteger(R.styleable.fab_cRadius, DEFAULT_RADIUS);

        mCircleDefColor = attrsArray.getColor(R.styleable.fab_cDefColor,
                getContext().getResources().getColor(R.color.fabColor));
        mCircleSelectColor = attrsArray.getColor(R.styleable.fab_cSelectColor,
                getContext().getResources().getColor(R.color.fabSelectColor));

        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setColor(mCircleDefColor);
        mStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setStrokeWidth(mCircleStrokeWidth);
        mStrokePaint.setColor(mCircleStrokeColor);

        mCircleStrokeColor = attrsArray.getColor(R.styleable.fab_cStrokeColor, DEFAULT_STROKE_COLOR);
        mCircleStrokeWidth = attrsArray.getInteger(R.styleable.fab_cStrokeWidth, DEFAULT_STROKE_WIDTH);
        final int mFabSize = attrsArray.getInteger(R.styleable.fab_cSize, DEFAULT_RADIUS);
        mDrawable = attrsArray.getDrawable(R.styleable.fab_cDrawable);

        if(mDrawable != null) {
            mDrawableHeight = mDrawable.getIntrinsicHeight();
            mDrawableWidth = mDrawable.getIntrinsicWidth();
        } else {
            mDrawableHeight = 0;
            mDrawableWidth = 0;
        }

        if(mCircleRadius == DEFAULT_RADIUS) {

            if (mFabSize == FAB_SIZE_NORMAL) {
                mCircleRadius = (DEFAULT_FAB_SIZE / 2);
            } else if (mFabSize == FAB_SIZE_MINI) {
                mCircleRadius = DEFAULT_FAB_MINI_SIZE / 2;
            }
        }

        //Compute the offset for the drawable
        mOffsetLeft = mCircleRadius - (mDrawableWidth/2);
        mOffsetTop = mCircleRadius - (mDrawableWidth/2);

        attrsArray.recycle();

        super.setImageDrawable(mDrawable);

    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void toggle() {
        setChecked(!mChecked);
    }

    /**
     * Sets the checked/unchecked state of the FAB.
     * @param checked
     */
    public void setChecked(boolean checked) {
        // If trying to set the current state, ignore.
        if (checked == mChecked) {
            return;
        }
        mChecked = checked;
        AnimationDrawable tmp;
        if(mChecked) {
            mCirclePaint.setColor(mCircleSelectColor);
            mDrawable = getResources().getDrawable(R.drawable.backward_check);
            super.setImageDrawable(mDrawable);
            Log.i(LOG_TAG, "checked");
        } else {
            mCirclePaint.setColor(mCircleDefColor);
            mDrawable = getResources().getDrawable(R.drawable.backward_star);
            super.setImageDrawable(mDrawable);
            Log.i(LOG_TAG, "unchecked");
        }

        if (mOnCheckedChangeListener != null) {
            mOnCheckedChangeListener.onCheckedChanged(null, checked);
        }
    }

    public void setOnCheckedChangeListener(FabView.OnCheckedChangeListener listener) {
        mOnCheckedChangeListener = listener;
        //setClickable(listener != null);
    }

    @Override
    public void onCheckedChanged(FabView fabView, boolean isChecked) {
        if (mOnCheckedChangeListener != null) {
            mOnCheckedChangeListener.onCheckedChanged(fabView, isChecked);
        }
    }
}