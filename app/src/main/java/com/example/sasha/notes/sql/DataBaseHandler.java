package com.example.sasha.notes.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sasha.notes.Person;

import java.util.ArrayList;
import java.util.List;


public class DataBaseHandler extends SQLiteOpenHelper implements IDatabaseHandler {

    public static final String DATABASE_NAME="PersonManager";
    public static final String TABLE_NAME = "persons";
    public static final String KEY_ID="id";
    public static final String KEY_NAME="name";
    public static final String KEY_PHONE="personePhone";

    public DataBaseHandler(Context context) {
        super(context,DATABASE_NAME , null, 1);
    }

    @Override
    public void addPerson(Person person) {
        SQLiteDatabase db=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(KEY_NAME,person.getName());
        contentValues.put(KEY_PHONE,person.getNumberTelefon());
        db.insert(TABLE_NAME, null, contentValues);
        db.close();
    }

    @Override
    public Person getPersonById(int id) {
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.query(TABLE_NAME,new String[]{KEY_ID,KEY_NAME,KEY_PHONE},KEY_ID+"=?",
                new String[]{String.valueOf(id)},null,null,null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        Person person=new Person(cursor.getString(1),cursor.getString(2));
        return person;
    }

    @Override
    public void deleteAll() {
        SQLiteDatabase db=getWritableDatabase();
        db.delete(TABLE_NAME,null,null);
        db.close();
    }

    @Override
    public List<Person> getAllPerson() {
        List<Person> personList=new ArrayList<Person>();
        String selectQuery="SELECT * FROM "+TABLE_NAME;
        SQLiteDatabase db=getWritableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do{
                Person person=new Person();
                person.setName(cursor.getString(1));
                person.setNumberTelefon(cursor.getString(2));
                personList.add(person);
            }while (cursor.moveToNext());
        }
        return personList;
    }

    @Override
    public void deletePerson(Person person) {
        SQLiteDatabase db=getWritableDatabase();
        db.delete(TABLE_NAME,KEY_PHONE+"=?",new String[]{person.getNumberTelefon()});
        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PERSON_TABLE="CREATE TABLE "+ TABLE_NAME+"("
                +KEY_ID+" INTEGER PRIMARY KEY,"+KEY_NAME+" TEXT,"
                +KEY_PHONE+" TEXT"+")";
        db.execSQL(CREATE_PERSON_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TEBLE IF EXIST "+TABLE_NAME);
        onCreate(db);
    }



}
