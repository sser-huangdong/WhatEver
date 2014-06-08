package com.sserhuangdong.whatever.app;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OptionDAO extends SQLiteOpenHelper {
    private final static String DB_NAME = "option2.db";
    private final static int DB_VERSION = 1;
    public final static String TABLE_NAME = "option_table";

    public OptionDAO(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        String SQL_CREATE_TABLE =
                "create table " + TABLE_NAME
                        + " (_id integer primary key autoincrement,"
                        + " location TEXT,"
                        + " type TEXT,"
                        + " name TEXT,"
                        + " like INTEGER,"
                        + " blacklist INTEGER"
                        + " );";
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
        //init();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }

    public void init() {
        System.out.println("Debug: init database");
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        String SQL_CREATE_TABLE =
                "create table " + TABLE_NAME
                        + " (_id integer primary key autoincrement,"
                        + " location TEXT,"
                        + " type TEXT,"
                        + " name TEXT,"
                        + " like INTEGER,"
                        + " blacklist INTEGER"
                        + " );";
        db.execSQL(SQL_CREATE_TABLE);

        String[] loc = new String[] {"三饭","三饭","三饭","三饭","三饭","三饭","三饭","三饭","三饭","三饭","三饭","三饭","二饭","二饭","二饭","二饭","二饭","二饭","二饭","二饭","二饭","二饭","二饭","二饭","二饭","二饭","二饭","一饭","一饭","一饭","一饭","一饭","一饭","一饭","一饭","一饭","一饭","一饭","一饭","四饭","四饭","四饭","四饭","四饭","四饭","四饭","四饭","四饭","四饭","四饭","四饭","四饭"};
        String[] type = new String[] {"小炒","小炒","小炒","小炒","小炒","面食","面食","面食","面食","面食","面食","面食","套餐","套餐","套餐","套餐","套餐","套餐","自选","糕点","糕点","糕点","糕点","糕点","小炒","小炒","小炒","小炒","小炒","小炒","面食","面食","面食","面食","糕点","面食","套餐","套餐","套餐","套餐","套餐","套餐","套餐","套餐","套餐","套餐","自选","面食","面食","面食","煲仔饭","煲仔饭"};
        String[] name = new String[] {"鱼香肉丝","青瓜炒肉片","鸡蛋炒番茄","辣子鸡","孜然牛肉","蛋炒米粉","蛋炒河粉","干炒牛河","过桥米线","酸辣米线","云吞面","鸡丝拌面","5元套餐","6元套餐","川湘套餐","烧味套餐","西餐","日韩定食","各种","豆腐花","蛋糕","煎饺","萝卜糕","蛋挞","辣子鸡","鱼香肉丝","洋葱牛肉","鸡蛋盖饭","木桶饭","牛肉炒面","兰州拉面","牛肉拉面","刀削面","麻辣烫","蒸饺","小笼包","烧味","经济套餐","美味套餐","5元套餐","6元套餐","7元套餐","鸡扒饭","非洲辣鸡饭","牛扒饭","猪扒饭","各种","炒面","麻辣烫","炸酱面","牛肉","猪肉"};

        for (int i = 0; i < loc.length; i++) {
            ContentValues values = new ContentValues();
            values.put("location", loc[i]);
            values.put("type", type[i]);
            values.put("name", name[i]);
            values.put("like", 0);
            values.put("blacklist", 0);

            db.insert(TABLE_NAME, null, values);
            // db.execSQL("INSERT INTO "+ TABLE_NAME +" VALUES(null, ?, ?, ?, 0, 0)", new Object[]{loc[i], type[i], name[i]});
        }
    }

    public List<Map<String, String>> getSomeOption(int len) {
        List<Map<String, String>> ret = new ArrayList<Map<String, String>>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ TABLE_NAME +" ORDER BY RANDOM() LIMIT ?", new String[]{Integer.toString(len)});
        while (cursor.moveToNext()) {
            Map<String, String> item = new HashMap<String, String>();

            item.put("id", cursor.getString(cursor.getColumnIndex("_id")));
            item.put("name", cursor.getString(cursor.getColumnIndex("location")) + "-"
                           + cursor.getString(cursor.getColumnIndex("type")) + "-"
                           + cursor.getString(cursor.getColumnIndex("name")));

            ret.add(item);
        }
        cursor.close();
        db.close();
        return ret;
    }
}
