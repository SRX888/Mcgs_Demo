# 屏幕旋转流程



**Android 屏幕旋转的机制默认情况下通过重力感应器（G-sensor）来实现的，带有G-sensor的Android设备上可通过API获取到设备的运动加速度，应用程序通过一些假设和算法运算，可以从加速度计算出设备的方向，通过计算出来的值。PhoneWindownManager 通过注册一个ContentObserver来监听用户设置的变化，当用户设置变化后调用updateSettings（）函数，做设置变化的更新和做相应的动作。WindowManagerService完成屏幕旋转的功能，首先更新屏幕方向，然后具体实施屏幕旋转，最后通知AMS configuration变更。**


**上面的所说的都是基于Android设备的具有G-sensor的模块，通过监听G-sensor数据的变化实现屏幕的旋转。但是对于没有G-sensor的模块的设备或者通过想要通过按键来触发屏幕的旋转，怎么实现呢？**

**不管是通过G-sensor设备还是通过按键或者其他的方式来触发屏幕旋转的流程都一样的，只是提供数据的方式有区别而已。**

## 一、实现机制

其实现机制为：当Configuration改变后，ActivityManagerService将会发送"配置改变"的广播，会要求ActivityThread重新启动当前focus的Activity，这是默认情况。如果想很好地支持屏幕旋转，则建议在res中建立layout-land和layout-port两个文件夹，把横屏和竖屏的布局文件放入对应的layout文件夹中。如果不申明android:configChanges=""，按照Activity的生命周期，都会去执行一次onCreate()方法，而onCreate()方法通常会在显示之前做一些初始化工作。这样就有可能造成重复的初始化，必然降低程序效率，而且更有可能因为重复的初始化而导致数据的丢失。


## 二、A64屏幕旋转实现


目前的A64 Android 设备就是没有G-sensor的模块，具体的实现的就是通过按键或者一些操作模拟G-sensor数据，再把数据传给WindowManagerService，最后通过WindowManagerService判断的接收的数据控制屏幕旋转。

现在从Android APP（单个应用的旋转，只有在APP界面内旋转）和系统级别的旋转（一旦选择，不管重启还是其他行为都保持该旋转的模式），分别从0,90,180,270 四个角度进行旋转。


### 2.1  A64 APP 实现屏幕旋转

#### APP 四个角度的旋转：

在APP界面通过按键来实现APP的旋转
有2种方式控制屏幕方向：

##### （1）修改AndroidManifest.xml

```
1 在AndroidManifest.xml的activity中加入:
横屏：
android:screenOrientation=”landscape”
竖屏：
android:screenOrientation=”portrait”

通过AndroidManifest.xml的配置实现，但是这个有极限性。
```


##### (2)setRequestedOrientation函数

手动调用setRequestedOrientation之后，假如会引发横竖屏切换（即请求的横竖屏要求与当前的横竖屏情况不一致，就会引发切换），那么会立即调用onConfigurationChanged函数；假如不会引发横竖屏切换（请求前后一致），那么也就不会调用到onConfigurationChanged函数。
这个手动调用setRequestedOrientation的地方也可以在Activity中的任何地方，即也可以在onConfigurationChanged中调用，但是一旦指定为横屏或竖屏完成这个变换之后，后面不论屏幕如何进行怎么翻转变化，都不会再触发横竖屏切换了，也即等同于在manifest中设置了android:screenOrientation属性为横屏或竖屏。如果要恢复为响应横竖屏随物理传感器设备变换，那么就需要手动调用类似如下代码进行恢复：


```
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rotation_0)
    Button rotation0;
    @BindView(R.id.rotation_1)
    Button rotation1;
    @BindView(R.id.rotation_2)
    Button rotation2;
    @BindView(R.id.rotation_3)
    Button rotation3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.rotation_0, R.id.rotation_1, R.id.rotation_2, R.id.rotation_3})
    public void onViewClicked(View view) {
        int newOrientation = 255;
        switch (view.getId()) {
            case R.id.rotation_0:
                newOrientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
                break;
            case R.id.rotation_1:
                newOrientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
                break;
            case R.id.rotation_2:
                newOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                break;
            case R.id.rotation_3:
                newOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                break;
        }
            setRequestedOrientation(newOrientation);
    }
}
```



Activity进行默认旋转时候的处理，当屏幕进行旋转的时候会按照横屏的分辨率进行重绘，当然你也可以不进行任何处理难看就难看呗：），理想状态的处理就是建立两套同名的Layout，当屏幕旋转时系统会自动帮我们加载横屏的Layout。在强制重绘的时候旋转过程中可能会出现短暂黑屏（重绘是造成的）

APP 旋转方案的请参考github链接

DEMO链接：https://github.com/shenruixiang10086/A64RotationDemo


### 2.2 系统级别的旋转

目前A64 中采用的方法就是系统级别的旋转，就是通过设置可以屏幕分别从0,90,180,270 四个角度进行旋转。一旦设置了系统在任何界面就采用当前的角度，不管设备重启，断电都不影响。在设置的界面分别有0,90,180,270四个角度的设置及一个恢复默认配置的选项。该功能实现的设备可以实现0,90,180,270四个角度的旋转。恢复出厂默认配置用于用户设置其他角度不知道如何设置默认配置的时候可以使用。该功能在系统中Mcgs-demo app中应用中屏幕旋转可以设置。

对于用户来说只要能掌握APP级别的屏幕旋转即可，对于系统级别的旋转不用cace,涉及系统侧面的修改。