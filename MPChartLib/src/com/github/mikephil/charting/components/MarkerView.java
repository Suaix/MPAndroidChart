
package com.github.mikephil.charting.components;

import android.content.Context;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.data.Entry;

/**
 * View that can be displayed when selecting values in the chart. Extend this
 * class to provide custom layouts for your markers.
 * 
 * @author Philipp Jahoda
 */
public abstract class MarkerView extends RelativeLayout {

    /**
     * The mark_code of MarkerView when the MarkerView is too left
     */
    private static final int TOO_LEFT = 1;
    /**
     * The mark_code of MarkerView when the MarkerView is too right
     */
    private static final int TOO_RIGHT = 2;
    /**
     * The mark_code of MarkerView when the MarkerView isn't too left or right
     */
    private static final int JUST_MIDDEL = 3;
    /**
     * The default mark_code of the MarkerView
     */
    private int status = JUST_MIDDEL;
    /**
     * the width of phone screen
     */
    private float screenWidth;
    /**
     * the height of phone screen
     */
    private float screenHeight;
    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     * 
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */
    public MarkerView(Context context, int layoutResource) {
        super(context);
        setupLayoutResource(layoutResource);
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
    }

    /**
     * Sets the layout resource for a custom MarkerView.
     * 
     * @param layoutResource
     */
    private void setupLayoutResource(int layoutResource) {

        View inflated = LayoutInflater.from(getContext()).inflate(layoutResource, this);

        inflated.setLayoutParams(new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));
        inflated.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));

        // measure(getWidth(), getHeight());
        inflated.layout(0, 0, inflated.getMeasuredWidth(), inflated.getMeasuredHeight());
    }

    /**
     * Draws the MarkerView on the given position on the screen with the given
     * Canvas object.
     * 
     * @param canvas
     * @param posx
     * @param posy
     */
    public void draw(Canvas canvas, float posx, float posy) {

        // take offsets into consideration
        posx += getXOffset();
        posy += getYOffset();
        if(posx > screenWidth-this.getWidth()){
            posx = posx - this.getWidth()/2;
            if(status != TOO_RIGHT){
                setOutOfRightScreenMarker();
                status = TOO_RIGHT;
            }
        } else if (posx < 0){
            posx = posx + this.getWidth()/2;
            if(status != TOO_LEFT){
                setOutOfLeftScreenMarker();
                status = TOO_LEFT;
            }
        } else if(posx > 0 && posx < screenWidth - this.getWidth()){
            if (status != JUST_MIDDEL){
                setDefaultScreenMarker();
                status = JUST_MIDDEL;
            }
        }

        // translate to the correct position and draw
        canvas.translate(posx, posy);
        draw(canvas);
        canvas.translate(-posx, -posy);
    }

    /**
     * This method enables a specified custom MarkerView to update it's content
     * everytime the MarkerView is redrawn.
     * 
     * @param e The Entry the MarkerView belongs to. This can also be any
     *            subclass of Entry, like BarEntry or CandleEntry, simply cast
     *            it at runtime.
     * @param dataSetIndex the index of the DataSet the selected value is in
     */
    public abstract void refreshContent(Entry e, int dataSetIndex);

    /**
     * Use this to return the desired offset you wish the MarkerView to have on
     * the x-axis. By returning -(getWidth() / 2) you will center the MarkerView
     * horizontally.
     * 
     * @return
     */
    public abstract int getXOffset();

    /**
     * Use this to return the desired position offset you wish the MarkerView to
     * have on the y-axis. By returning -getHeight() you will cause the
     * MarkerView to be above the selected value.
     * 
     * @return
     */
    public abstract int getYOffset();

    /**
     * set the background of markerview when the markerview out of right_screen
     */
    public abstract void setOutOfRightScreenMarker();

    /**
     * set the background of markerview when the markerview out of left_screen
     */
    public abstract void setOutOfLeftScreenMarker();

    /**
     * set the default background of markerview
     */
    public abstract void setDefaultScreenMarker();
}
