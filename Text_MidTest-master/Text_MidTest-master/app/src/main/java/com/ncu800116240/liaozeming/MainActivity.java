package com.ncu800116240.liaozeming;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView textlist;
    private SQLiteDatabase sqLiteDatabase;
    private  TextAdapter textAdapter;
    private List<textBean> list;
    private Button add_text;
    //长按后点击更新时返回点击的项目和文本ID号
    private int Item_id;
    //单击返回文本
    private textBean Item_click;
    private textBean text_item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textlist=findViewById(R.id.text_list);
        Text_Database text_database=new Text_Database(MainActivity.this);
        //能读
        sqLiteDatabase=text_database.getReadableDatabase();
        //获取数据
        list=text_database.querydata(sqLiteDatabase);
        //将sqlite查询到的数据返回
       textAdapter=new TextAdapter(MainActivity.this,list);
        textlist=findViewById(R.id.text_list);
        textlist.setAdapter(textAdapter);

        registerForContextMenu(textlist);

        //列表项长按监听
        textlist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
               //实现删除功能
                //显示长按后弹出菜单，上面注册了 registerForContextMenu(textlist)
                //后面重载onCreateContextMenu方法
                //得到id号
                Item_id=list.get(i).getId();
                //得到点击第i个item时返回itemBean
                text_item=list.get(i);
                textlist.showContextMenu();
                return true;
            }
        });


        //点击显示整个文本
        textlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
     //         Item_click=list.get(i);

            }
        });

        //实现添加功能
        add_text=findViewById(R.id.add_text);
        add_text.setOnClickListener(this);

    }



    @Override
    public void onClick(View view) {
        switch (view.getId())
        {

            case R.id.add_text:
                Intent intent=new Intent(this,Add_Text.class);
                startActivity(intent);
                break;
                default:break;
        }

    }

    //重载：改写长按弹出的菜单内容
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(0,0,0,"删除文本");
        menu.add(0,1,1,"显示和编辑文本");
        super.onCreateContextMenu(menu, v, menuInfo);
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
            msgBuilder.append("张浩国\n");
            msgBuilder.append("网安181班\n");
            String title="关于";
            new AlertDialog.Builder(MainActivity.this).setIcon(R.drawable.lq)
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

    @Override
    public void onBackPressed() {
        String title="提示";
        new AlertDialog.Builder(MainActivity.this).setIcon(R.drawable.lq)
                .setTitle(title)
                .setMessage("确定退出吗？")
                .setPositiveButton("确定" ,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).create().show();
    }



    //长按后弹出的文本点击事件
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case 0:
                String title="提示";
                new AlertDialog.Builder(MainActivity.this).setIcon(R.drawable.lq)
                        .setTitle(title)
                        .setMessage("确定删除吗？")
                        .setPositiveButton("确定" ,new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //删除操作，在什么数据库下删除id号的记录
                                Text_Database text_database=new Text_Database(MainActivity.this);
                                SQLiteDatabase sqLiteDatabase=text_database.getReadableDatabase();
                                text_database.delete(sqLiteDatabase,Item_id);
                                Intent intent=new Intent(MainActivity.this,MainActivity.class);
                                startActivity(intent);
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).create().show();
            break;

            //编辑文本
            case  1:
                Intent intent=new Intent(MainActivity.this,Update_Text.class);
                Bundle bundle=new Bundle();
                String Update_Context=text_item.getContext();
                String Update_Title=text_item.getTitle();
                bundle.putInt("id",Item_id);
                //把文本和标题传递
                bundle.putString("context",Update_Context);
                bundle.putString("title",Update_Title);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            default:
                break;

        }
        return super.onContextItemSelected(item);
    }
}
