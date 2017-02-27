package com.dyman.zhihudaily.database.tool;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dyman.zhihudaily.database.dao.ReadSchedule;
import com.dyman.zhihudaily.database.db.DbManager;
import com.dyman.zhihudaily.database.db.TableConfig;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 *  数据库的操作类
 *
 * Created by dyman on 2017/2/27.
 */

public class TableOperate {

    private DbManager manager;
    private SQLiteDatabase db;

    public TableOperate() {

        // 创建数据库
        manager = DbManager.newInstance();
        db = manager.getDataBase();
    }


    /**
     * 查询数据库的名，数据库的添加
     *
     * @param tableName  查询的数据库的名字
     * @param entityType 查询的数据库所对应的module
     * @param fieldName  查询的字段名
     * @param value      查询的字段值
     * @param <T>        泛型代表AttendInformation，Customer，Order，User，WorkDaily类
     * @return 返回查询结果，结果为AttendInformation，Customer，Order，User，WorkDaily对象
     */
    public <T> ArrayList<T> query(String tableName, Class<T> entityType, String fieldName, String value) {

        ArrayList<T> list = new ArrayList();
        Cursor cursor = db.query(tableName, null, fieldName + " like ?", new String[]{value}, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            try {
                T t = entityType.newInstance();
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    String content = cursor.getString(i);//获得获取的数据记录第i条字段的内容
                    String columnName = cursor.getColumnName(i);// 获取数据记录第i条字段名的
                    Field field = entityType.getDeclaredField(columnName);//获取该字段名的Field对象。
                    field.setAccessible(true);//取消对age属性的修饰符的检查访问，以便为属性赋值
                    field.set(t, content);
                    field.setAccessible(false);//恢复对age属性的修饰符的检查访问
                }
                list.add(t);
                cursor.moveToNext();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return list;
    }


    /**
     *  向数据库插入数据
     *
     * @param tableName 数据库插入数据的数据表名
     * @param object    数据库插入的对象
     */
    public void insert(String tableName, Object object) {

        Class clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields(); // 获取该类所有的属性
        ContentValues values = new ContentValues();

        for (Field field : fields) {
            try {
                field.setAccessible(true);                      // 取消对 age 属性的修饰符的检查访问, 以便为属性赋值
                String content = (String) field.get(object);    // 获取该属性的内容
                values.put(field.getName(), content);
                field.setAccessible(false);                     //  恢复对 age 属性的修饰符的检查访问
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        db.insert(tableName, null , values);
    }


    /**
     *  删除数据
     *
     * @param tableName 删除数据库的表名
     * @param fieldName 删除的字段名
     * @param value     删除的字段的值
     */
    public void delete(String tableName, String fieldName, String value) {
        db.delete(tableName, fieldName + "=?", new String[] {value});
    }


    /**
     *  更改数据库内容
     *
     * @param tableName     更改数据的数据表
     * @param columnName    更改数据的字段名
     * @param columnValue   更改数据的字段值
     * @param object        更改的数据
     */
    public void update(String tableName, String columnName, String columnValue, Object object) {

        try {
            Class clazz = object.getClass();
            Field[] fields = clazz.getDeclaredFields();
            ContentValues values = new ContentValues();
            for (Field field : fields) {
                field.setAccessible(true);                      // 取消对 age 属性的修饰符的检查访问, 以便为属性赋值
                String content = (String) field.get(object);    // 获取该属性德尔内容
                values.put(field.getName(), content);
                field.setAccessible(false);                     // 恢复对 age 属性的修饰符的检查访问
            }
            db.update(tableName, values, columnName + "=?", new String[] {columnValue});
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    public void insertReadSchedule(ReadSchedule readSchedule) {

        ContentValues values = new ContentValues();

        values.put(TableConfig.ReadSchedule.ARTICLE_ID, readSchedule.getArticleID());
        values.put(TableConfig.ReadSchedule.READ_TIME, readSchedule.getReadTime());
        values.put(TableConfig.ReadSchedule.READ_RATIO, readSchedule.getReadRatio());

        if (isHas(readSchedule.getArticleID())) {
            System.out.println("---  更新数据  ---");
            db.update(TableConfig.TABLE_READ_SCHEDULE, values,
                    TableConfig.ReadSchedule.ARTICLE_ID+"=?",
                    new String[]{readSchedule.getArticleID()});
        } else {
            System.out.println("---  插入数据  ---");
            db.insert(TableConfig.TABLE_READ_SCHEDULE, null, values);
        }
    }


    public void getReadSchedule(String articleID) {

        Cursor cursor = db.query(TableConfig.TABLE_READ_SCHEDULE,
                null,
                TableConfig.ReadSchedule.ARTICLE_ID+"=?",
                new String[]{articleID},
                null, null, null);

        cursor.moveToFirst();
        String article_id = cursor.getString(cursor.getColumnIndex(TableConfig.ReadSchedule.ARTICLE_ID));
        System.out.println("id = "+article_id);
        String read_time = cursor.getString(cursor.getColumnIndex(TableConfig.ReadSchedule.READ_TIME));
        String read_ratio = cursor.getString(cursor.getColumnIndex(TableConfig.ReadSchedule.READ_RATIO));
        cursor.close();

    }



    private boolean isHas(String articleID) {
        boolean result = false;

        Cursor cursor = db.query(TableConfig.TABLE_READ_SCHEDULE,
                new String[] {TableConfig.ReadSchedule.ARTICLE_ID},
                TableConfig.ReadSchedule.ARTICLE_ID+"=?",
                new String[]{articleID},
                null, null, null);

        if (cursor.moveToFirst()) {
            System.out.println("该数据存在");
            result = true;
        } else {
            System.out.println("该数据不存在");
        }

        cursor.close();
        return result;
    }



//    /**
//     *  关闭 SQLiteDataBase 和 dataBaseHelper (未完成)
//     */
//    public void close() {
//        //  不关闭可以吗?
//        manager.close();
//        db.close();
//    }

}
