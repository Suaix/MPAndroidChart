
package com.xxmassdeveloper.mpchartexample.custom;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.utils.Utils;
import com.xxmassdeveloper.mpchartexample.R;

/**
 * Custom implementation of the MarkerView.
 * 
 * @author Philipp Jahoda
 */
public class MyMarkerView extends MarkerView {

    private TextView tvContent;
    private RelativeLayout rlMarker;

    public MyMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);

        tvContent = (TextView) findViewById(R.id.tvContent);
        rlMarker = (RelativeLayout) findViewById(R.id.rl_marker_view);
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, int dataSetIndex) {

        if (e instanceof CandleEntry) {

            CandleEntry ce = (CandleEntry) e;

            tvContent.setText("This point's value is :" + Utils.formatNumber(ce.getHigh(), 0, true));
        } else {

            tvContent.setText("This point's value is :" + Utils.formatNumber(e.getVal(), 0, true));
        }
    }

    @Override
    public int getXOffset() {
        // this will center the marker-view horizontally
        return -(getWidth() / 2);
    }

    @Override
    public int getYOffset() {
        // this will cause the marker-view to be above the selected value
        return -getHeight();
    }

    @Override
    public void setOutOfRightScreenMarker() {
        rlMarker.setBackgroundResource(R.drawable.ic_marker_right);
    }

    @Override
    public void setOutOfLeftScreenMarker() {
        rlMarker.setBackgroundResource(R.drawable.ic_marker_left);
    }

    @Override
    public void setDefaultScreenMarker() {
        rlMarker.setBackgroundResource(R.drawable.ic_marker_middle);
    }
}
