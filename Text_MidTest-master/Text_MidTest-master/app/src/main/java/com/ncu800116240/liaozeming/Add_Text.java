package com.ncu800116240.liaozeming;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Add_Text extends AppCompatActivity implements View.OnClickListener {
    private TextView showTime;
    private EditText add_title;
    private EditText add_context;
    private Button save_text,return_text;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__text);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        String Date = simpleDateFormat.format(new Date());
        //可以放在init()中
        showTime=findViewById(R.id.showTime);
        showTime.setText(Date);
        add_context=findViewById(R.id.add_context);
        add_title=findViewById(R.id.add_title);
        save_text=findViewById(R.id.save_text);
        return_text=findViewById(R.id.return_text);
        save_text.setOnClickListener(this);
        return_text.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            //保存操作
            case R.id.save_text:
                new AlertDialog.Builder(Add_Text.this)
                        .setTitle("系统提示")
                        .setMessage("确定保存吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String title=add_title.getText().toString();
                                String context=add_context.getText().toString();
                                //返回时间
                                String time=showTime.getText().toString();
                                //理解：相当于是给工厂，工厂有原件和方法，text_database.getReadableDatabase()获得原价
//                                text_database.adddata 执行方法，将数据库原件给他
                                Text_Database text_database=new Text_Database(Add_Text.this);
                                //返回一个 SQLiteDatabase对象
                                SQLiteDatabase sqLiteDatabase=text_database.getReadableDatabase();
                                text_database.adddata(sqLiteDatabase,title,context,time);
                                Intent intent1=new Intent(Add_Text.this,MainActivity.class);
                                startActivity(intent1);
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).create().show();
                break;

            case R.id.return_text:
                Intent intent=new Intent(Add_Text.this,MainActivity.class);
                startActivity(intent);
                break;
                default: break;
        }

    }



    //对菜单选项关于和退出事件监听
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean retValue=super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_item,menu);
        return retValue;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.item_about)
        {
            StringBuilder msgBuilder=new StringBuilder();
            msgBuilder.append("ImageViewer V1.0.0\n");
            msgBuilder.append("张浩国n");
            msgBuilder.append("\n");
            String title="关于";
            new AlertDialog.Builder(Add_Text.this).setIcon(R.drawable.lq)
                    .setTitle(title)
                    .setMessage(msgBuilder.toString())
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            }).create().show();
        }

        if(item.getItemId()==R.id.item_exit)
        {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
