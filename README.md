# 【JetPack】为现有 Android 项目配置视图绑定 ( ViewBinding ) 模块 ( 视图绑定不影响传统布局操作 | 视图绑定类关联 Activity | 视图绑定类本质 )

https://hanshuliang.blog.csdn.net/article/details/105070393




<br>
<br>

#### I . 为现有项目配置 视图绑定 ( ViewBinding ) 应用

---

<br>


**1 . 视图绑定模块默认为全部布局生成绑定类 ;** <font color=blue>视图绑定 ( ViewBinding ) 模块一旦启用 , 应用的全部布局都会默认自动生成一个视图绑定类 , 如果生成了视图绑定模块 , 是否对于已经使用的 findViewById 或者 @BindView @BindViews 代码是否有影响 ; 

<br>

**2 . Android 项目中布局文件数量比较大 ;** <font color=purple>现在的 Android 项目如果比较大 , 布局文件可能存在上百个 , Activity , Fragment , 自定义布局的 Dialog , 自定义 View 组件 , RecyclerView 列表条目 item 布局 , 这些都要使用到布局文件 ;

<br>

**3 .<font color=red> 如果为该 Android 项目启用了视图绑定模块 , 所有的布局都会生成对应的视图绑定类 ;** 

<br>

**4 . 因此这里需要讨论如下问题 :** <font color=green>如果在 build.gradle 中启用了视图绑定模块 , 对已经开发好的代码是否有影响 , 本博客会进行详细的测试 ; 

<br>


**5 . 先说下结论 : <font color=red>视图绑定 只是为我们额外生成了一种新的操作布局和组件的方式 , 不会对之前已经写好的代码产生影响 ;** 


<br>
<br>

#### II . 视图绑定 ( ViewBinding ) 定制

---

<br>

**1 . Android 官方文档中给出的定制方案 :** <font color=blue>如果当前有几百个布局文件 , 为了不影响之前的代码 , 可以在每个布局的根视图上配置 `tools:viewBindingIgnore=“true”` 属性 ; ( 工作量较大 ) 

<br>

**2 . 不影响之前的代码 :** <font color=green>此时可以不进行上面的操作 , 虽然启用了视图绑定模块 , 系统为我们生成了视图绑定类 , <font color=purple>这个类我们可以选择使用 , 也可以选择不用 , <font color=blue>也可以继续使用 `setContentView(R.layout.activity_main)` 设置布局文件 , 使用 `findViewById(R.id.text_view)` 获取组件 ; <font color=red>可以不使用系统给生成的绑定类 XxxXxxBinding ; 





<br>
<br>

#### III . 视图绑定 ( ViewBinding ) 对于正常操作的影响测试

---

<br>

**1 . 先说下结论 : <font color=blue>视图绑定 只是为我们额外生成了一种新的操作布局和组件的方式 , 不会对之前已经写好的代码产生影响 ;** 

<br>

**2 . <font color=orange>在启用了 ViewBinding 模块后 , 布局中如果没有屏蔽视图绑定 ,  那么会为该布局生成布局绑定类 , 此时如果进行正常的操作 , 仍然不影响 , 可以不用修改之前的代码 ;** 

<br>

**3 . 在 build,gradle 中配置了视图绑定 :** <font color=green>主要是 viewBinding 配置 , 其它都是多余的 ; 

```java
apply plugin: 'com.android.application'

android {
    compileSdkVersion 29
    buildToolsVersion "29.0.2"

    defaultConfig {
        applicationId "kim.hsl.vb"
        minSdkVersion 16
        targetSdkVersion 29
        versionCode 1
        versionName "1.0"

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }

    viewBinding {
        //启用视图绑定模块
        enabled = true
    }

}

dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])

    implementation 'androidx.appcompat:appcompat:1.1.0'
    implementation 'androidx.constraintlayout:constraintlayout:1.1.3'
    testImplementation 'junit:junit:4.12'
    androidTestImplementation 'androidx.test.ext:junit:1.1.1'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.2.0'
}

```

<br>

**4 . activity_main.xml 布局文件代码 :** <font color=purple>没有设置屏蔽 视图绑定 模块 , 即系统会为该布局自动生成一个视图绑定类 ; 

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout 
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <TextView
        android:id="@+id/text_view"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello World!"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```


<br>

**5 . Activity 界面的 Java 代码 :** <font color=blue>仍然使用传统的布局操作方式 , 使用 `setContentView(R.layout.activity_main)` 设置布局文件 , 使用 `findViewById(R.id.text_view)` 获取组件 ; 

```java
package kim.hsl.vb;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import kim.hsl.vb.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    /**
     * 从布局中获取 TextView 组件
     */
    private TextView text_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // I . 传统使用方式

        //设置布局文件
        setContentView(R.layout.activity_main);

        //获取布局文件中的 id 为 text_view 的 TextView 组件
        text_view = findViewById(R.id.text_view);
        text_view.setText("启用视图绑定的情况下使用传统布局操作方法");


        // II . 视图绑定类分析

        //  下面的视图绑定类操作是无效的

        //获取视图绑定类 , 但是此视图绑定类没有关联该界面
        //  关联的方式是 setContentView 中设置该绑定类的根视图才可以
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());

        //由于视图绑定类中的视图并未与该 Activity 界面关联
        //  因此单纯的操作该视图绑定类不能修改本界面的 TextView 显示文字
        binding.textView.setText("ActivityMainBinding");
    }
}

```

<br>

**6 . 执行结果 :** 

![在这里插入图片描述](https://img-blog.csdnimg.cn/2020032414072195.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2hhbjEyMDIwMTI=,size_16,color_FFFFFF,t_70)


<br>
<br>

#### IV . 视图绑定 ( ViewBinding ) 关联 Activity 界面

---

<br>

**1 . 两种获取组件方式 :** <font color=blue>上面的示例代码中 , 分别使用 `findViewById(R.id.text_view)` 获取的组件 和 ActivityMainBinding 获取组件 ; 但是使用 ActivityMainBinding 获取组件无法修改界面 ; 


<br>

**2 . <font color=red>ActivityMainBinding 获取的组件无法控制界面 , 这是因为该 ActivityMainBinding 视图绑定类 , 并没有与 Activity 关联 ;** 

<br>

**3 . 界面布局分析 :** <font color=green>设置界面布局的代码是 `setContentView(R.layout.activity_main)` , 此处将 activity_main.xml 布局设置给了 Activity 显示 , <font color=blue>该布局的视图组件与 ActivityMainBinding 没有任何关联 , <font color=purple>Activity 显示的组件也不是 ActivityMainBinding 绑定类中的组件 , <font color=red>因此操作视图绑定类中的组件不能修改 Activity 界面的显示 ; 

<br>

**4 . 如果要显示需要将 ActivityMainBinding  根视图设置给 Activity 显示 , 进行如下操作 ;** 

```java
/*
    获取 视图绑定 对象
    生成绑定类 : 需要传递 LayoutInflater 参数 ,
        可以直接调用 Activity 的 getLayoutInflater() 方法获取
 */
ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
/*
    ActivityMainBinding 绑定类自带 getRoot() 方法
        可以直接获取到 布局文件的 根视图
    这里可以直接将根视图传递给 setContentView 函数作为参数 , 即可在该 Activity 中显示该布局
 */
setContentView(binding.getRoot());
```

<br>

**5 . 关联界面操作 : `setContentView(binding.getRoot())` <font color=blue>操作就是将视图绑定类与 Activity 界面关联了起来 , 此时操作视图绑定类就可以修改界面内容了 ;** 



<br>
<br>

#### V . 视图绑定 ( ViewBinding ) 本质分析

---

<br>

<font color=red>**视图绑定 ( ViewBinding ) 其本质就是提供了一种加载布局文件的便捷方式 , 与下面的操作本质是类似的 , 只是可以省略很多代码 ;** 

<br>

**① 布局加载操作 :** 
```java
//加载布局文件
View view = LayoutInflater.from(context)
  		.inflate(R.layout.activity_main, parent, false);

//查找布局文件中的组件
TextView textView = view.findViewById(R.id.text_view);
```

<br>

**② 视图绑定操作 :** 
```java
ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
TextView textView = binding.textView;
```


<br>
<br>

#### VI . GitHub 代码地址

---

<br>

**GitHub 代码地址 : [https://github.com/han1202012/002_JetPack_ViewBinding_Apply](https://github.com/han1202012/002_JetPack_ViewBinding_Apply)**
