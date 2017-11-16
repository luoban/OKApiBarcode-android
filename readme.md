#OkapiBarcode-Android

### Fork frm https://github.com/woo-j/OkapiBarcode

1. 只测试了二维码
2. 编码在android上存在点问题
3. 有空再弄

### Usage:

```Java
renderer = new Java2DRenderer(15, Color.WHITE, Color.BLACK);
qrCode = new QrCode();
qrCode.setEccMode(QrCode.EccMode.H);
qrCode.setDataType(Symbol.DataType.ECI);
qrCode.setPreferredVersion(7);
qrCode.setContent("测试Testabcd1234567");
bitmap = renderer.render(qrCode);
```

