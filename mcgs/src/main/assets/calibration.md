# 一、屏幕校准说明

在Android4.2.2 上基本全部用的都是电容式的触摸屏了，所以Google和其他定制厂商也陆续在自己的Android版本中去掉了电阻屏校准这部分代码，所以在android4.2.2以后已经没有电阻屏校准的source code。

目前A64 设备使用的是电阻屏且Android设备的版本是Android 6.0,所以默认的Android source code没有没有屏幕校准功能的，需要移植tslib的源码或者通过APP 来生产校准数据，把校准的数据提供给触屏驱动即可。


#### 校准方法

**首先生成校准用的参数，可以使用tslib生成校准参数，也可以使用校准app生成校准参数；考虑到用户的实际使用要求降低，用户可以不用学习复杂的命令及通过一些命令使用tslib来校准。所以本次校准使用APP 方式来校准，使用App 方式来校准大大降低用户的使用难度，没有任何编程基础的用户也能轻松的完成校准。生成校准参数后，以后驱动每次接收到触摸事件就会使用校准参数进行校准。校准之后每次的触摸驱动就使用校准的之后的数据。**


# 二、A64屏幕校准方案

  ### 2.1 校准App：



**按照APP提示点击"十字光标"进行校准之后，就把校准的数据保存在/data/calibration里，最后把/data/calibration的校准参数传递给tsc2007驱动就完成屏幕的校准。**

如：

```
adb shell cat /data/calibration
11960 473 -2863332 -305 20270 -441608 65536

其中11960 473 -2863332 -305 20270 -441608 65536就是校准数据。

```

  ### 2.2  校准APP简单分析如下：
  
 ##### 在Activity  onCreate里首先获取到了当前屏幕的宽高:
 
 
 ``` 
dpy = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
X_RES = dpy.getWidth();
Y_RES = dpy.getHeight();

```
其中X_RES， Y_RES就代表了当前屏幕的宽高。
 
 ##### 初始化要触摸点的坐标的方法：
 
 
  ``` 
// TopLeft-->TopRight-->BottomRight-->BottomLeft-->Center
private boolean initScreenPoints() {
    cal.xfb[TOP_LEFT] = EDGE_GAP; // TopLeft
    cal.yfb[TOP_LEFT] = EDGE_GAP;

    cal.xfb[TOP_RIGHT] = X_RES - EDGE_GAP; // TopRight
    cal.yfb[TOP_RIGHT] = EDGE_GAP;

    cal.xfb[BOTTOM_RIGHT] = X_RES - EDGE_GAP; // BottomRight
    cal.yfb[BOTTOM_RIGHT] = Y_RES - EDGE_GAP;

    cal.xfb[BOTTOM_LEFT] = EDGE_GAP; // BottomLeft
    cal.yfb[BOTTOM_LEFT] = Y_RES - EDGE_GAP;

    cal.xfb[CENTER] = X_RES / 2; // Center
    cal.yfb[CENTER] = Y_RES / 2;

    Log.w(TAG, "cal.yfb[center] =" + cal.yfb[CENTER]);

    return true;
}

```


**初始化 5个"十字光标"触摸点,分别是 左上角--> 右上角 --> 右下角 --> 左下角 -->正中间，依次点击这5个"十字光标"触摸点就可以把校准参数保存在/data/calibration中**

 ##### 接着就开始布局了，是new的一个自定义View：

``` 

setContentView(new MyView(this));

```

##### ontouchEvent方法监听手指点击事件

``` 

 public boolean onTouchEvent(MotionEvent event) {
            float tmpx, tmpy;
            boolean ret;
            String proGetString=null;
            if (screen_pos > SAMPLE_COUNTS - 1) {
                Log.i(TAG, "get sample ok");
                return true;
            }

            if (event.getAction() == MotionEvent.ACTION_UP) {
                tmpx = event.getX();
                tmpy = event.getY();
                if(Math.abs(cal.xfb[screen_pos]-tmpx)>15&&
                        Math.abs(cal.yfb[screen_pos]-tmpy)>15){
                    return false;
                }

                cal.x[screen_pos] = (int)(event.getX()*4096.0/(float)X_RES + 0.5);
                cal.y[screen_pos] = (int)(event.getY()*4096.0/(float)Y_RES + 0.5);

                if (screen_pos == 4) {
                    ret = perform_calibration();
                    if (ret) {
                        saveCalibrationResult();
                        SystemProperties.set("sys.config.calibrate", "done");
                        finish();
                        return true;
                    } else {
                        screen_pos = 0;
                        Log.w(TAG, "Calibration failed");
                    }
                } else {
                    screen_pos++;
                    drawCalibrationCross(screen_pos);
                }
            }
            return true;
        }

```


通过ontouchEvent方法监听手指点击事件把点击的事件的产生的数据通过一列的触摸校正算法及逻辑处理把数据转化成的驱动可以使用的诗句，通过saveCalibrationResult（）方法保存起来。

这样就把校准的数据保存到了 /data/calibration中，最后把/data/calibration的校准参数传递给tsc2007驱动就完成屏幕的校准



# 三、A64屏幕校准说明

(1)首先生成校准用的参数，可以适用tslib生成校准参数，也可以使用校准app生成；使用校准app进行校准对使用者要求比较低，使用者可以不用学习复杂的命令；从用户的角度考虑使用app方式"

(2)生成校准参数后，以后驱动每次接收到触摸事件就会使用校准参数进行校准"。

(3)对于用户而言，只需要会使用App完成屏幕的校准即可。


