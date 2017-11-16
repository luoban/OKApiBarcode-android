/*
 * Copyright 2014-2015 Robin Stuart, Robert Elliott, Daniel Gredler
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.org.okapibarcode.output;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

import uk.org.okapibarcode.backend.Hexagon;
import uk.org.okapibarcode.backend.Symbol;

/**
 * Renders symbologies using the Java 2D API.
 */
public class Java2DRenderer implements SymbolRenderer {
    /**
     * The magnification factor to apply.
     */
    private final double magnification;

    /**
     * The paper (background) color.
     */
    private final int paper;

    /**
     * The ink (foreground) color.
     */
    private final int ink;

    private Paint mPaint;

    /**
     * Creates a new Java 2D renderer.
     *
     * @param magnification the magnification factor to apply
     * @param paper         the paper (background) color
     * @param ink           the ink (foreground) color
     */
    public Java2DRenderer( double magnification, int paper, int ink) {
        this.magnification = magnification;
        this.paper = paper;
        this.ink = ink;
        mPaint = new Paint();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Bitmap render(Symbol symbol) {
        Bitmap bmp = Bitmap.createBitmap((int)(symbol.getWidth() * magnification),
                (int) (symbol.getHeight() * magnification), Bitmap.Config.ALPHA_8);
        Canvas canvas = new Canvas(bmp);
        int marginX = (int) (symbol.getQuietZoneHorizontal() * magnification);
        int marginY = (int) (symbol.getQuietZoneVertical() * magnification);

        Map<TextAttribute, Object> attributes = new HashMap<>();
        attributes.put(TextAttribute.TRACKING, 0);
        mPaint.setColor(ink);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(3);
        for (RectF rect : symbol.rectangles) {
            float x = (float) ((rect.left * magnification) + marginX);
            float y = (float) ((rect.top * magnification) + marginY);
            float r = (float) ((rect.right + rect.left) * magnification);//right 填充实际上是width,需要处理
            float b = (float) ((rect.bottom + rect.top) * magnification);//bottom 填充实际上height,需要处理
            canvas.drawRect(new RectF(x,y,r,b), mPaint);
        }


        for (Hexagon hexagon : symbol.hexagons) {
            Path polygon = new Path();
            polygon.moveTo((int) ((hexagon.pointX[0] * magnification) + marginX),
                    (int) ((hexagon.pointY[0] * magnification) + marginY));
            for (int j = 1; j < 6; j++) {
                polygon.lineTo((int) ((hexagon.pointX[j] * magnification) + marginX),
                        (int) ((hexagon.pointY[j] * magnification) + marginY));
            }
            canvas.drawPath(polygon,mPaint);
        }


        for (int i = 0; i < symbol.target.size(); i++) {
            RectF ellipse = symbol.target.get(i);
            float x = (float) ((ellipse.left * magnification) + marginX);
            float y = (float) ((ellipse.top * magnification) + marginY);
            float w = (float) ((ellipse.width() * magnification) + marginX);
            float h = (float) ((ellipse.height() * magnification) + marginY);
            if ((i & 1) == 0) {
                mPaint.setColor(ink);
            } else {
                mPaint.setColor(paper);
            }
            canvas.drawOval(new RectF(x, y, w, h), mPaint);
        }
        return bmp;
    }
}
