package com.lb.okbarcode;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

import uk.org.okapibarcode.backend.QrCode;
import uk.org.okapibarcode.backend.Symbol;
import uk.org.okapibarcode.output.Java2DRenderer;

public class MainActivity extends Activity {
    private Java2DRenderer renderer;
    private QrCode qrCode;
    Bitmap bitmap;
    private ImageView mImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = (ImageView) findViewById(R.id.image);
        renderer = new Java2DRenderer(15, Color.WHITE, Color.BLACK);
        qrCode = new QrCode();
        qrCode.setEccMode(QrCode.EccMode.H);
        qrCode.setDataType(Symbol.DataType.ECI);
        qrCode.setPreferredVersion(7);
        qrCode.setContent("测试Testabcd1234567");
        bitmap = renderer.render(qrCode);
        mImageView.setImageBitmap(bitmap);
    }
}
