package com.example.android.floatingactionbuttonbasic;

import android.content.Context;
import android.content.res.TypedArray;
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
    private Drawable mDrawable;

    public FabViewBackward(Context context, AttributeSet attrs) {
        super(context, attrs);
        //Read all the attributes
        init(attrs);
    }

    public FabViewBackward(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    /**
     * initialize all the custom attributes
     * Canvas/drawing attributes got dropped, set in the xml layout
     * @param attrs
     */
    public void init(AttributeSet attrs) {

        TypedArray attrsArray = getContext().obtainStyledAttributes(attrs, R.styleable.fab);
        mDrawable = attrsArray.getDrawable(R.styleable.fab_cDrawable);
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

        if(mChecked) {
            mDrawable = getResources().getDrawable(R.drawable.backward_check);
            super.setImageDrawable(mDrawable);
            Log.i(LOG_TAG, "checked");
        } else {
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
    }

    @Override
    public void onCheckedChanged(FabView fabView, boolean isChecked) {
        if (mOnCheckedChangeListener != null) {
            mOnCheckedChangeListener.onCheckedChanged(fabView, isChecked);
        }
    }
}