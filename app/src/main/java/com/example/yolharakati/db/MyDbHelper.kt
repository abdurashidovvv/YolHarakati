package com.example.yolharakati.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.yolharakati.models.Label

class MyDbHelper(context: Context):SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION), MyDbInterface {

    companion object{
        val DB_NAME="db_name"
        val DB_VERSION=1

        val LABEL_TABLE="label_table"
        val LABEL_ID="id"
        val LABEL_NAME="name"
        val LABEL_INFO="info"
        val LABEL_IMG="img"
        val LABEL_LIKE="likes"
        val LABEL_TYPE="type"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val query="create table $LABEL_TABLE($LABEL_ID integer not null primary key autoincrement unique, $LABEL_NAME text not null, $LABEL_INFO text not null, $LABEL_IMG text not null, $LABEL_LIKE text not null, $LABEL_TYPE text not null)"
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    override fun addLabel(label: Label) {
        val database=writableDatabase
        val contentValues=ContentValues()
        contentValues.put(LABEL_NAME, label.name)
        contentValues.put(LABEL_INFO, label.info)
        contentValues.put(LABEL_IMG, label.img)
        contentValues.put(LABEL_LIKE, label.like)
        contentValues.put(LABEL_TYPE, label.type)
        database.insert(LABEL_TABLE, null, contentValues)
        database.close()
    }

    override fun getAllLabel(): ArrayList<Label> {
        val list=ArrayList<Label>()
        val query="select * from $LABEL_TABLE"
        val database=readableDatabase
        val cursor=database.rawQuery(query, null)
        if (cursor.moveToFirst()){
            do {
                list.add(
                    Label(
                        id = cursor.getInt(0),
                        name = cursor.getString(1),
                        info = cursor.getString(2),
                        img = cursor.getString(3),
                        like = cursor.getString(4),
                        type = cursor.getString(5)
                    )
                )
            }while (cursor.moveToNext())
        }
        return list
    }

    override fun editLabel(label: Label) {
        val database=writableDatabase
        val contentValues=ContentValues()
        contentValues.put(LABEL_ID, label.id)
        contentValues.put(LABEL_NAME,label.name)
        contentValues.put(LABEL_INFO,label.info)
        contentValues.put(LABEL_IMG, label.img)
        contentValues.put(LABEL_LIKE, label.like)
        contentValues.put(LABEL_TYPE, label.type)
        database.update(LABEL_TABLE, contentValues, "$LABEL_ID=?", arrayOf(label.id.toString()))
    }

    override fun deleteLabel(label: Label) {
        val database=writableDatabase
        database.delete(LABEL_TABLE, "$LABEL_ID=?", arrayOf(label.id.toString()))
        database.close()
    }

}