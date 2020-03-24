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
