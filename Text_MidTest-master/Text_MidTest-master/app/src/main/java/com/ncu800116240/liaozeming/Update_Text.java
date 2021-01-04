package com.ncu800116240.liaozeming;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Update_Text extends AppCompatActivity implements View.OnClickListener {
    private EditText Update_Title;
    private TextView Update_Time;
    private EditText Update_Context;
    private Button Update_Button;
    private Button Update_Return;
    private String context,title;
    private  Bundle bundle;
    //得到要更新的表的id号
    private int id;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__text);
        init();
        //接受传过来的text_Bean对象
        Intent intent=this.getIntent();
         bundle=intent.getExtras();
        //展现文本和标题
        ShowText();

    }

    public void ShowText() {
        Update_Context.setText(bundle.getString("context"));
        Update_Title.setText(bundle.getString("title"));
        id=bundle.getInt("id");
    }

    public  void init() {
        Update_Context=findViewById(R.id.update_context);
        Update_Title=findViewById(R.id.update_title);
        Update_Button=findViewById(R.id.update_text);
        Update_Return=findViewById(R.id.update_return_text);
        Update_Time=findViewById(R.id.update_Time);
        //设置当前事件
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        date= simpleDateFormat.format(new Date());
        Update_Time.setText(date);
        //返回按钮的注册
        Update_Return.setOnClickListener(this);
        //更新的注册
        Update_Button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            //更新按钮
            case R.id.update_text:
                //更新时重新赋值
                context=Update_Context.getText().toString();
                date=Update_Time.getText().toString();
                title=Update_Title.getText().toString();
                Text_Database text_database=new Text_Database(Update_Text.this);
                SQLiteDatabase sqLiteDatabase=text_database.getReadableDatabase();
                boolean flag=text_database.Update(sqLiteDatabase,id,title,context,date);
                if(flag)
                {
                    Toast.makeText(this,"更新成功",Toast.LENGTH_LONG).show();
                }
                Intent intent=new Intent(Update_Text.this,MainActivity.class);
                startActivity(intent);
                break;
                //返回按钮
            case R.id.update_return_text:
                Intent intent1=new Intent(Update_Text.this,MainActivity.class);
                startActivity(intent1);
                break;
                default:break;
        }

    }
}
