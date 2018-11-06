# Android HelloWorld开发样例

## 一、Android Studio 和SDK获取

**方式1：进入Android Developers官方网站下载；**<br>

**方式2：进入Android Studio中文社区下载；**<br>

**方式3：进入Android Dev Tools网站下载。**<br>


 ***推荐进入Android Dev***<br>
 ```
 Tools转一转，这个网站收集整理了Android开发所需的Android SDK、开发中用到的工具、Android开发教程、Android设计规范，免费的设计素材等。所有资源一站式齐全。
 ```

## 二．开始新建项目

**以下操作基于Android Studio v3.0.1版本**

***注：Android Studio以下简称AS***

#### 1.1  第一步当然是先运行AS

配置AS第一次运行环境，并且能成功编译运行一个APP，以helloworld为例。

 ```
运行成功之后，点击“Start a new Android Studio project” ，开始创建新的studio project.
 ```
 
#### 1.2 填写项目信息

点击“Start a new Android Studio project”, 会出现填写填写项目信息，接下来对这些信息做一些解释。


***1.Application name（应用名称）***
 ```
此名称将在手机桌面上显示
 ```
***2.Company domain（公司域名）***

```
如果在企业开发，则填入相应的公司域名，相对于个人来说，就比较随意了，把自己的名字当做公司名称填入就OK了。

```
 
 
***3.Project location（项目位置）***
```
选择项目的存储文位置
```

***4.Package name（包名）***
```
上面的项目都好理解，但是包名是什么意思呢？

在Android系统中，系统就是根据“包名”来区分不同的应用的，相当于一个应用的身份证号码，和身份证一样，这个名称要具有唯一性。

AS会根据“Application name”和“Company domain”自动帮我们生成一个“Package name”。如果想自定义，可以点右侧的“Edit”修改。
```

***5.Include C++ support 和Include Kotlin support***
```
如果你的项目用到C++或者Kotlin语言，则需要勾选对应选项。
```

#### 1.3  选择开发环境和最低兼容的SDK


****1.开发环境****

```
我们可以看到有：

“Phone and Tablet”（手机与平板电脑）

“Wear”（穿戴设备）

“TV”（智能电视）

“Android Auto”（车载系统）

“Things”（物联网开发）

开发环境当然选择“Phone and Tablet”了。

```

****2.最低兼容的API版本****

```
其中每一种开发环境，都可以选择最低支持到的API版本，这个选择是根据每个Android OS版本的市场活动份额来决定的，我个人习惯选择“API：23”。
```

#### 1.4．添加活动和选择活动模板


****1.Add No Activity（不添加活动）****
```
选择此选项后，则系统不会帮我们自动创建一个Activity。

在此要知道“Activity”的概念是我们在应用中看到的页面，也就是说，
我们看到的每一个页面都是一个“Activity”，我们进行的所有的操作都建立在“Activity”的基础之上。
```


****2.选择一个模板****

```
这里我们一般选择“Empty Activity”（空的活动）。
```

#### 1.5．创建一个新的空活动

****1.Activity Name（活动名字）****
```
采用每个单词首字母大写形式命名。
```

****2.Generate Layout File（自动创建对应Activity的Layout文件）****
```
这里要明白“Layout”的概念，每一个“Activity”都对应着一个“Layout”的布局文件。简单地说，我们平常所点击的按钮，使用的文本框等都是在“Layout”的xml文件中添加的，这个按钮的操作逻辑的代码则是在“Activity”的Java文件中编写的。

“Layout”和“Activity”的关系可以抽象的看做是“骨架”和“灵魂”的关系。

这里我们勾选，让AS自动帮我们的活动创建一个布局文件。
```


****3.Layout Name（布局文件名字）****
```
只能使用小写英文字母，一般采用默认即可。
```

****4.Backwards Compatiblity（程序的向后兼容性）****
```
这个选项我们现在不做过多解释，默认勾选即可。

点击完成之后就可以运行Hello World Demo.
```

#### 三．运行项目


***android helloworld 的Demo 是AS 自动生成的，开发者不需要写任何的代码。
看一下自动生成的代码：***

##### 文件夹、文件和说明

**src:**
```
包含项目中所有的.java源文件，默认情况下，它包括一个 MainActivity.java源文件对应的活动类，当应用程序通过应用图标启动时
，将运行它。
```

**gen:**

```
这包含由编译器生成的.R文件，引用了所有项目中的资源。该文件不能被修改。
bin:这个文件夹包含Android由APT构建的.apk包文件，以及运行Android应用程序所需要的其他所有东西。

```
**res/drawable-hdpi:**
```
这个目录下包括所有的为高密度屏幕设计所需的drawable对象。
```

**res/layout:**
```
这个目录存放用于定义用户界面的文件。
```

**res/values:**
```
这个目录存放各种各样的包含一系列资源的XML文件，比如字符串和颜色的定义。
```

**AndroidManifest.xml:**
```
这个是应用程序的清单文件，描述了应用程序的基础特性，定义它的各种组件
```

**3.1  主要活动文件**
```
主要活动代码在MainActivity.java的Java文件中。这是实际的应用程序文件，
将被转化为Dalvik可执行文件并运行。

```

***下面是由应用向导为Hello World应用生成的默认代码 -***

```
package com.example.helloworld;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;

public class MainActivity extends Activity {

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.activity_main, menu);
      return true;
   }
}

```

**这里，R.layout.activity_main引用自res/layout目录下的activity_main.xml文件。onCreate()是活动被加载之后众多被调用的方法之一。**


***Manifest文件***<br>
```
无论你开发什么组件用作应用程序中的一部分，都需要在应用程序项目根目录下的manifest.xml文件中声明所有的组件。这个文件是Android操作系统与你的应用程序之间的接口，因此，如果没有在这个文件中声明你的组件，将无法被操作系统所识别。举个例子，一个默认的清单文件看起来如下：
```

```
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
   package="com.example.helloworld"
   android:versionCode="1"
   android:versionName="1.0" >

   <uses-sdk
      android:minSdkVersion="8"
      android:targetSdkVersion="22" />

   <application
       android:icon="@drawable/ic_launcher"
       android:label="@string/app_name"
       android:theme="@style/AppTheme" >

       <activity
          android:name=".MainActivity"
          android:label="@string/title_activity_main" >

          <intent-filter>
             <action android:name="android.intent.action.MAIN" />
             <category android:name="android.intent.category.LAUNCHER"/>
          </intent-filter>

       </activity>

   </application>
</manifest>

```


这里，...标签之间是应用程序相关的组件。andnroid:icon属性指出位于res/drawable-hdpi下面的应用程序图标。这个应用使用drawable文件夹下名为ic_launcher.png的图片。<br>

标签用于指定一个活动，android:name属性指定一个Activity类子类的全名。android:label属性指定用于活动名称的字符串。可以使用标签来指定多个活动。<br>

意图过滤器的action被命名为android.intent.action.MAIN，表明这个活动被用做应用程序的入口。意图过滤器的category被命名为android.intent.category.LAUNCHER，表明应用程序可以通过设备启动器的图标来启动。<br>

@string指的是strings.xml(将在后面介绍)。因此，@string/app_name指的是定义在strings.xml中的app_name，实际为"Hello World"。类似的，应用中的其他字符串也很流行。

**下面是你的清单文件中将用到的标签，用于指定不同的Android应用程序组件：**

**活动元素**

**服务元素**

**广播接收器元素**

**内容提供者元素**

***Strings 文件***

strings.xml文件在res/value文件夹下，它包含应用程序使用到的所有文本。例如，按钮、标签的名称，默认文本，以及其他相似的strings。这个文件为他们的文本内容负责。一个默认的strings文件看起来如下：

```
<resources>
   <string name="app_name">HelloWorld</string>
   <string name="hello_world">Hello world!</string>
   <string name="menu_settings">Settings</string>
   <string name="title_activity_main">MainActivity</string>
</resources>
```

***R 文件***

gen/com.example.helloworld/R.java文件是活动的Java文件，如MainActivity.java的和资源如strings.xml之间的胶水。这是一个自动生成的文件，不要修改R.java文件的内容。下面是一个R.java文件的示例：

```
/* AUTO-GENERATED FILE.  DO NOT MODIFY.
 *
 * This class was automatically generated by the
 * aapt tool from the resource data it found.  It
 * should not be modified by hand.
 */

package com.example.helloworld;

public final class R {
   public static final class attr {
   }

   public static final class dimen {
      public static final int padding_large=0x7f040002;
      public static final int padding_medium=0x7f040001;
      public static final int padding_small=0x7f040000;
   }

   public static final class drawable {
      public static final int ic_action_search=0x7f020000;
      public static final int ic_launcher=0x7f020001;
   }

   public static final class id {
      public static final int menu_settings=0x7f080000;
   }

   public static final class layout {
      public static final int activity_main=0x7f030000;
   }

   public static final class menu {
      public static final int activity_main=0x7f070000;
   }

   public static final class string {
      public static final int app_name=0x7f050000;
      public static final int hello_world=0x7f050001;
      public static final int menu_settings=0x7f050002;
      public static final int title_activity_main=0x7f050003;
   }

   public static final class style {
      public static final int AppTheme=0x7f060000;
   }
}

```

***Layout 文件***


activity_main.xml是一个在res/layout目录下的layout文件。当应用程序构建它的界面时被引用。你将非常频繁的修改这个文件来改变应用程序的布局。在"Hello World"应用程序中，这个文件具有默认的布局，内容如下：

```
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
   xmlns:tools="http://schemas.android.com/tools"
   android:layout_width="match_parent"
   android:layout_height="match_parent" >

   <TextView
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:layout_centerHorizontal="true"
      android:layout_centerVertical="true"
      android:padding="@dimen/padding_medium"
      android:text="@string/hello_world"
      tools:context=".MainActivity" />

</RelativeLayout>

```


这是一个简单的RelativeLayout的示例，更多内容会在独立的章节中讲解。TextView是一个Android的控件用于构建用户图形界面。它包含有许多不同的属性，诸如android:layout_width, android:layout_height等用来设置它的宽度和高度等。@string指的是res/values文件夹下的strings.xml文件。因此，@string/hello_world指的是定义在strings.xml中的名为hello的字符串："Hello World!"。


运行：<Br>
部署工程就可以运行，把USB线连接到pc,运行即可，恭喜你已经开发了第一个Android应用程序，按照接下来剩余的教程一步一步来，你将成为一个牛B的Android开发人员。


