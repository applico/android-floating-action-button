package applico.android_floating_action_button.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import applico.android_floating_action_button.R;

/**
 * This class is a glorified CircleView class that we can use to create FAB buttons
 * in our applications. Its intended to have full attribute and getter/setter support
 */
public class FabView extends View {
    private static String LOG_TAG = FabView.class.getSimpleName();

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
    private Outline mOutline;

    private float mScreenDensityFloat;
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
    private static final int FAB_SIZE_MINI= 1;



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

        mCirclePaint.setColor(mCircleFillColor);
        canvas.drawArc(mCircleArc, mStartAngle, mEndAngle, true, mCirclePaint);
        canvas.drawArc(mCircleArc, mStartAngle, mEndAngle, true, mStrokePaint);
        if (mDrawable != null) {
            //left top right bottom
            mDrawable.setBounds(mOffsetLeft,mOffsetTop,mDrawableWidth+mOffsetLeft,
                    mDrawableHeight + mOffsetTop);
            mDrawable.draw(canvas);
        }
        this.setOutline(mOutline);


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
        //Convert the fab sizes to DP
        mScreenDensity = (int)getResources().getDisplayMetrics().density;
        mScreenDensityFloat = getResources().getDisplayMetrics().density;

        DEFAULT_FAB_SIZE = DEFAULT_FAB_SIZE * (int)mScreenDensity;
        DEFAULT_FAB_MINI_SIZE = DEFAULT_FAB_MINI_SIZE * (int)mScreenDensity;

        TypedArray attrsArray = getContext().obtainStyledAttributes(attrs, R.styleable.fab);
        mCircleRadius = attrsArray.getInteger(R.styleable.fab_cRadius, DEFAULT_RADIUS);
        mCircleFillColor = attrsArray.getColor(R.styleable.fab_cFillColor, DEFAULT_FILL_COLOR);
        mCircleStrokeColor = attrsArray.getColor(R.styleable.fab_cStrokeColor, DEFAULT_STROKE_COLOR);
        mCircleStrokeWidth = attrsArray.getInteger(R.styleable.fab_cStrokeWidth, DEFAULT_STROKE_WIDTH);
        mFabSize = attrsArray.getInteger(R.styleable.fab_cSize, DEFAULT_RADIUS);
        mDrawable = attrsArray.getDrawable(R.styleable.fab_cDrawable);
        mDrawableHeight = mDrawable.getIntrinsicHeight();
        mDrawableWidth = mDrawable.getIntrinsicWidth();
        mStartAngle = DEFAULT_START_ANGLE;
        mEndAngle = DEFAULT_END_ANGLE;

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


        //Set the outline for the elevation attribute
        mOutline = new Outline();
        mOutline.setOval(0,0,(mCircleRadius * 2),(mCircleRadius *2));

        attrsArray.recycle();
        this.bringToFront();
    }

    /**
     * Paint used for the view.
     * @return
     */
    public Paint getPaint()
    {
        return mCirclePaint;
    }

    /**
     * Set the paint of the circleview
     * @param p
     */
    public void setPaint(Paint p)
    {
        mCirclePaint = p;
    }

    /**
     * Get the stroke paint
     * @return
     */
    public Paint getStrokePaint()
    {
        return mStrokePaint;
    }

    /**
     * Set the stroke paint on the view
     * @param p
     */
    public void setStrokePaint(Paint p)
    {
        mStrokePaint = p;
    }

    /**
     * set the Circle Arc for the view
     * @param arc
     */
    public void setCircleArc(RectF arc)
    {
        mCircleArc = arc;
    }

    /**
     * Get the circle arc
     * @return
     */
    public RectF getCircleArc()
    {
        return mCircleArc;
    }

    /**
     * Set the circle radius on the view..have fun creating Pacman
     * @param r
     */

    public void setCircleRadius(int r)
    {
        mCircleRadius = r;
    }

    /**
     * Get the radius of the view
     * @return
     */
    public int getCircleRadius()
    {
        return mCircleRadius;
    }

    /**
     * Set the fill color
     * @param color
     */
    public void setFillColor(int color)
    {
        mCircleFillColor = color;
    }

    /**
     * Get the fill color
     * @return
     */
    public int getFillColor()
    {
        return mCircleFillColor;
    }

    /**
     * Get the stroke color
     * @return
     */
    public int getStrokeColor()
    {
        return mCircleStrokeColor;
    }

    /**
     * Set the stroke color
     * @param color
     */
    public void setStrokeColor(int color)
    {
        mCircleStrokeColor = color;
    }

    /**
     * Get the stroke width
     * @return
     */
    public int getStrokeWidth()
    {
        return mCircleStrokeWidth;
    }

    /**
     * Set the stroke width
     * @param width
     */
    public void setStrokeWidth(int width)
    {
        mCircleStrokeWidth = width;
    }

    /**
     * Gets the example drawable attribute value.
     * @return The example drawable attribute value.
     */
    public Drawable getDrawable() {

        return mDrawable;
    }

    /**
     * Sets the view's drawable attribute value. In the example view, this drawable is
     * drawn above the text.
     * @param drawable attribute value to use.
     */
    public void setDrawable(Drawable drawable) {

        mDrawable = drawable;
    }


}
