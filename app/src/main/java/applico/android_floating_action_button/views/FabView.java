package applico.android_floating_action_button.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.AnimatedStateListDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;

import applico.android_floating_action_button.R;

/**
 * This class is intended to imitate the Material Design Fab Button.  Google indicated that they
 * will not be
 * providing this view as part of a support package or the impending "L" release.
 *
 * @author Matt Powers
 */
public class FabView extends ImageView {

    //Initailize the object variables
    private Paint mCirclePaint;

    private Paint mStrokePaint;

    private RectF mCircleArc;

    private int mCircleRadius;

    private int mStartAngle;

    private int mEndAngle;

    private int mCircleFillColor;

    private int mCircleStrokeColor;

    private int mCircleStrokeWidth;

    private int mFabSize;

    private int mScreenDensity;

    private int mDrawableHeight;

    private int mDrawableWidth;

    private int mOffsetLeft;

    private int mOffsetTop;

    private Drawable mDrawable;


    //Default values
    //TODO change this to pull from the dimensions file.
    private static final int DEFAULT_RADIUS = 0;

    private static final int DEFAULT_FILL_COLOR = Color.BLACK;

    private static final int DEFAULT_STROKE_COLOR = Color.BLUE;

    private static final int DEFAULT_START_ANGLE = 0;

    private static final int DEFAULT_END_ANGLE = 360;

    private static final int DEFAULT_STROKE_WIDTH = 2;

    //Default FAB sizes according to the material design
    //docs: http://www.google.com/design/spec/components/buttons.html#buttons-main-buttons

    private int DEFAULT_FAB_SIZE = 56;

    private int DEFAULT_FAB_MINI_SIZE = 40;

    private static final int FAB_SIZE_NORMAL = 0;

    private static final int FAB_SIZE_MINI = 1;

    private ViewOutlineProvider provider;

    private RectF rectF;

    //TODO - support the context only constructor
    public FabView(Context context) {

        super(context);
    }

    public FabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //Read all the attributes
        init(attrs);

        //Initialize the stroke and paint objects
        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setColor(mCircleFillColor);
        mStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setStrokeWidth(mCircleStrokeWidth);
        mStrokePaint.setColor(mCircleStrokeColor);
    }

    //TODO - support the context attribute set and defstyle constructor
    public FabView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(1, 1);
        canvas.drawArc(mCircleArc, mStartAngle, mEndAngle, true, mCirclePaint);
        canvas.drawArc(mCircleArc, mStartAngle, mEndAngle, true, mStrokePaint);
        if (mDrawable != null) {
            //left top right bottom
            mDrawable.setBounds(mOffsetLeft, mOffsetTop, mDrawableWidth + mOffsetLeft,
                    mDrawableHeight + mOffsetTop);
            mDrawable.draw(canvas);
        }

        this.setOutlineProvider(provider);
        setClipToOutline(true);
    }

    /**
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredWidth = measureWidth(widthMeasureSpec);

        //We are using this method to provide a default size based on the layout size
        if (mCircleRadius == 0) {
            mCircleRadius = measuredWidth / 2;
            int tempRadiusHeight = measureHeight(heightMeasureSpec) / 2;
            if (tempRadiusHeight < mCircleRadius) {
                mCircleRadius = tempRadiusHeight;
            }
        }

        mCircleArc = rectF;
        int measuredHeight = measureHeight(heightMeasureSpec);
        setMeasuredDimension(measuredWidth, measuredHeight);

    }

    /**
     * measure the height of the screen
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
     */
    private int measureWidth(int measureSpec) {
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
     */
    public void init(AttributeSet attrs) {
        //Convert the fab sizes to DP
        mScreenDensity = (int) getResources().getDisplayMetrics().density;

        DEFAULT_FAB_SIZE = DEFAULT_FAB_SIZE * mScreenDensity;
        DEFAULT_FAB_MINI_SIZE = DEFAULT_FAB_MINI_SIZE * mScreenDensity;

        TypedArray attrsArray = getContext().obtainStyledAttributes(attrs, R.styleable.fab);
        mCircleRadius = attrsArray.getInteger(R.styleable.fab_cRadius, DEFAULT_RADIUS);
        mCircleFillColor = attrsArray.getColor(R.styleable.fab_cFillColor, DEFAULT_FILL_COLOR);
        mCircleStrokeColor = attrsArray
                .getColor(R.styleable.fab_cStrokeColor, DEFAULT_STROKE_COLOR);
        mCircleStrokeWidth = attrsArray
                .getInteger(R.styleable.fab_cStrokeWidth, DEFAULT_STROKE_WIDTH);
        mFabSize = attrsArray.getInteger(R.styleable.fab_cSize, DEFAULT_RADIUS);
        mDrawable = attrsArray.getDrawable(R.styleable.fab_cDrawable);

        mStartAngle = DEFAULT_START_ANGLE;
        mEndAngle = DEFAULT_END_ANGLE;

        if (mDrawable != null) {
            mDrawableHeight = mDrawable.getIntrinsicHeight();
            mDrawableWidth = mDrawable.getIntrinsicWidth();
        } else {
            mDrawableHeight = 0;
            mDrawableWidth = 0;
        }

        if (mCircleRadius == DEFAULT_RADIUS) {
            if (mFabSize == FAB_SIZE_NORMAL) {
                mCircleRadius = (DEFAULT_FAB_SIZE / 2);
            } else if (mFabSize == FAB_SIZE_MINI) {
                mCircleRadius = DEFAULT_FAB_MINI_SIZE / 2;
            }
        }

        //Compute the offset for the drawable
        mOffsetLeft = mCircleRadius - (mDrawableWidth / 2);
        mOffsetTop = mCircleRadius - (mDrawableWidth / 2);

        //Set the outline for the elevation attribute
        attrsArray.recycle();
        final int circleDiameter = mCircleRadius * 2 - mCircleStrokeWidth;
        rectF = new RectF(0, 0, circleDiameter, circleDiameter);
        provider = new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setOval(0, 0, circleDiameter, circleDiameter);
            }
        };
        this.bringToFront();
        super.setImageDrawable(mDrawable);
    }

    /**
     * Gets the example drawable attribute value.
     *
     * @return The example drawable attribute value.
     */
    public Drawable getCDrawable() {

        return mDrawable;
    }

    /**
     * Sets the view's drawable attribute value. In the example view, this drawable is
     * drawn above the text.
     *
     * @param drawable attribute value to use.
     */
    public void setCDrawable(AnimatedStateListDrawable drawable) {
        mDrawable = drawable;
        //invalidate();
        super.setImageDrawable(drawable);

    }

}
