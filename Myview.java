package com.example.jaehyung.seniorproject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by Jaehyung on 2017-12-28.
 */

public class Myview extends View {

    int gyroX;
    int gyroY;
    int gyroZ;

    int xPos;
    int yPos;

    public void setX_gyro(int x) {
        this.gyroX = x;
    }

    public void setY_gyro(int y) {
        this.gyroY = y;
    }

    public void setZ_gyro(int z) {
        this.gyroZ = z;
    }

    public Myview(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);

      /*  paint.setStrokeWidth((float) (Math.abs(gyroZ) * 3.0));
        canvas.drawLine(200, 200, 200, 400, paint);
        canvas.drawText("Z축" + gyroZ, 200, 200, paint);

        paint.setStrokeWidth((float) (Math.abs(gyroX) * 3.0));
        canvas.drawLine(200, 400, 400, 400, paint);
        canvas.drawText("X축" + gyroX, 400, 400, paint);

        paint.setStrokeWidth((float) (Math.abs(gyroY) * 3.0));
        canvas.drawLine(100, 250, 200, 400, paint);
        canvas.drawText("Y축" + gyroY, 100, 250, paint);*/
        canvas.drawCircle(900+xPos,300-yPos,30,paint);
        if(gyroY!=0||(gyroZ+gyroX)!=0){
            //xPos+=(gyroX-gyroZ);
            xPos+=gyroX/100;
            if (xPos<-900)
                xPos=-900;
            if (xPos>900)
                xPos=900;
            yPos+=gyroY/100;
            if(yPos<-300)
                yPos=-300;
            if(yPos>300)
                yPos=300;
            invalidate();
        }
    }
}
