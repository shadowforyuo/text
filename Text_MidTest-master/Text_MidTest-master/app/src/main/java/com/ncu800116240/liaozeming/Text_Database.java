package com.ncu800116240.liaozeming;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Text_Database extends SQLiteOpenHelper {

    public Text_Database(Context context) {
        super(context, "text_db", null, 1);
    }

    //第一次执行时创建
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table text(id integer primary key autoincrement," +
                "title varchar(20),context varchar(100),date Date)";
        sqLiteDatabase.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //删
    public void delete(SQLiteDatabase sqLiteDatabase, int id) {
        sqLiteDatabase.delete("text", "id=?", new String[]{id + ""});
        sqLiteDatabase.close();
    }

    //查

    public List<textBean> querydata(SQLiteDatabase sqLiteDatabase)
    {
        Cursor cursor=sqLiteDatabase.query("text",null,null,null,null,null,"id ASC");
        List<textBean> list=new ArrayList<textBean>();
        while (cursor.moveToNext())
        {
            int id=cursor.getInt(cursor.getColumnIndex("id"));
            String title=cursor.getString(1);
            String context=cursor.getString(2);
            String date=cursor.getString(3);
            list.add(new textBean(id,title,context,date));
        }
        cursor.close();
        sqLiteDatabase.close();
        //返回查询的记事本集合
        return list;
    }
    //增
    public void adddata(SQLiteDatabase sqLiteDatabase,String title,String context,String date)
    {
        ContentValues contentValues=new ContentValues();
        contentValues.put("title",title);
        contentValues.put("context",context);
        contentValues.put("date",date);
        sqLiteDatabase.insert("text",null,contentValues);
        sqLiteDatabase.close();
    }
    //根据ID号更新表
    public boolean Update( SQLiteDatabase sqLiteDatabase,int id,String title,String context,String date)
    {
        ContentValues contentValues=new ContentValues();
        contentValues.put("title",title);
        contentValues.put("context",context);
        contentValues.put("date",date);
        sqLiteDatabase.update("text",contentValues,"id=?",new String[]{id+""});
        sqLiteDatabase.close();
        return true;
    }
}
