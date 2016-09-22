package com.apporio.johnlaundry.views.laundry.lineview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by samir on 18/07/15.
 */
public class DoodleView extends View {


    private Paint pdoodle = new Paint();
    private Path  _dpath = new Path();


    public DoodleView(Context context) {
        super(context);
        init(null , 0 );
    }



    public DoodleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(null, 0);
    }

    public DoodleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(null, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DoodleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs , defStyleAttr );
    }



    private void init(AttributeSet attrs, int defStyleAttr ) {
        pdoodle.setColor(Color.BLUE);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPath(_dpath , pdoodle);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                _dpath.moveTo(touchX , touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                _dpath.lineTo(touchX , touchY);
                break;
            case MotionEvent.ACTION_UP:
                break;

         // super.onTouchEvent(event);

        }
           invalidate();
        return  true ;


    }
}
