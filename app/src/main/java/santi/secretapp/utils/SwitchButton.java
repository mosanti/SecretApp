package santi.secretapp.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Switch;

import santi.secretapp.R;

/**
 * Created by santi.mo on 16-9-28.
 */
public class SwitchButton extends Switch{

    private static boolean mBroadcasting;
    private boolean mChecked = true;

    private OnCheckedChangeListener mOnCheckedChangeListener;

    private OnCheckedChangeListener mOnCheckedChangeWidgetListener;

    public SwitchButton(Context context) {
        super(context);
    }

    public SwitchButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SwitchButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        setTrackResource(R.drawable.switch_track);
        setThumbResource(R.drawable.switch_btn);
    }

    private static void setBroadcasting(boolean broadcasting) {
        mBroadcasting = broadcasting;
    }

    @Override
    public void setChecked(boolean checked) {
        super.setChecked(checked);

        if( mChecked != checked) {
            mChecked = checked;

            // Avoid infinite recursions if setChecked() is called from a
            // listener
            if (mBroadcasting) {
                return;
            }

            mBroadcasting = true;

            if(mOnCheckedChangeListener != null) {
                mOnCheckedChangeListener.onCheckedChanged(SwitchButton.this,mChecked);
            }
            if(mOnCheckedChangeWidgetListener != null) {
                mOnCheckedChangeWidgetListener.onCheckedChanged(SwitchButton.this,mChecked);
            }

            mBroadcasting = false;
        }
    }

    @Override
    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        mOnCheckedChangeListener = listener;
    }
}
