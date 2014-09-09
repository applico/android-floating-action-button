package applico.android_floating_action_button.activities;

import android.animation.StateListAnimator;
import android.app.Activity;
import android.graphics.drawable.AnimatedStateListDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import applico.android_floating_action_button.R;
import applico.android_floating_action_button.views.CustomImageView;
import applico.android_floating_action_button.views.FabView;


public class ExampleActivity extends Activity implements View.OnClickListener {

    private static final String LOG_TAG = ExampleActivity.class.getSimpleName();

    private FabView mFabView;
    private CustomImageView mTestView;
    private boolean mChecked = false;
    private AnimatedStateListDrawable mDrawable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
        mFabView = (FabView)findViewById(R.id.fab_view);
        mFabView.setOnClickListener(this);
        mDrawable = (AnimatedStateListDrawable)mFabView.getCDrawable();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.example, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        Log.e(LOG_TAG,"Clicked");
        if(mFabView.isSelected())
        {
            mFabView.setSelected(true);
            mDrawable.jumpToCurrentState();
            mFabView.setSelected(false);
        }
        else{
            mFabView.setSelected(false);
            mDrawable.jumpToCurrentState();
            mFabView.setSelected(true);
        }


    }
}
