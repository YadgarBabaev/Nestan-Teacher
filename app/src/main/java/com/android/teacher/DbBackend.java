package com.android.teacher;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

public class DbBackend extends DbObject{

    public DbBackend(Context context) {
        super(context);
    }

    public long insert(String kg, String ru, String tr, byte[] img, byte[] snd, int category){
        ContentValues values = new ContentValues();
        values.put("kg", kg);
        values.put("ru", ru);
        values.put("tr", tr);
        values.put("img", img);
        values.put("snd", snd);
        values.put("cat_id", category);
        return this.getDbConnection().insert("dictionary", null, values);
    }

    public int update(int id, String kg, String ru, String tr, byte[] img, byte[] snd, int category){
        ContentValues values = new ContentValues();
        values.put("kg", kg);
        values.put("ru", ru);
        values.put("tr", tr);
        values.put("img", img);
        values.put("snd", snd);
        values.put("cat_id", category);
        return this.getDbConnection().update("dictionary", values, "id=" + id, null);
    }

    public void delete(int id){
        String query = "Delete from dictionary where id = " + id;
        this.getDbConnection().execSQL(query);
    }

    public ArrayObject dictionaryWords(){
        String query = "Select * from dictionary order by kg";
        Cursor cursor = this.getDbConnection().rawQuery(query, null);
        ArrayList<String> wordTerms = new ArrayList<>();
        ArrayList<String> wordRuTerms = new ArrayList<>();
        ArrayList<String> wordTrTerms = new ArrayList<>();
        ArrayList<Integer> idTerms = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                String kg = cursor.getString(cursor.getColumnIndexOrThrow("kg"));
                String ru = cursor.getString(cursor.getColumnIndexOrThrow("ru"));
                String tr = cursor.getString(cursor.getColumnIndexOrThrow("tr"));
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));

                wordTerms.add(kg);
                wordRuTerms.add(ru);
                wordTrTerms.add(tr);
                idTerms.add(id);
            } while(cursor.moveToNext());
        }
        cursor.close();
        String[] kgWords = new String[wordTerms.size()];
        String[] ruWords = new String[wordTerms.size()];
        String[] trWords = new String[wordTerms.size()];

        kgWords = wordTerms.toArray(kgWords);
        ruWords = wordRuTerms.toArray(ruWords);
        trWords = wordTrTerms.toArray(trWords);
        int[] ret = new int[idTerms.size()];
        for (int i=0; i < ret.length; i++)
        {
            ret[i] = idTerms.get(i);
        }
        return new ArrayObject(kgWords, ruWords, trWords, ret);
    }
//    public String[] dictionaryWords(){
//        String query = "Select * from dictionary order by kg";
//        Cursor cursor = this.getDbConnection().rawQuery(query, null);
//        ArrayList<String> wordTerms = new ArrayList<String>();
//        if(cursor.moveToFirst()){
//            do{
//                String word = cursor.getString(cursor.getColumnIndexOrThrow("kg"));
//                wordTerms.add(word);
//            }
//            while(cursor.moveToNext());
//        }
//        cursor.close();
//        String[] dictionaryWords = new String[wordTerms.size()];
//        dictionaryWords = wordTerms.toArray(dictionaryWords);
//        return dictionaryWords;
//    }

    public String[] categoryWords(){
        String query = "Select * from categories";
        Cursor cursor = this.getDbConnection().rawQuery(query, null);
        ArrayList<String> wordTerms = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                String word = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                wordTerms.add(word);
            }
            while(cursor.moveToNext());
        }
        cursor.close();
        String[] categoryWords = new String[wordTerms.size()];
        categoryWords = wordTerms.toArray(categoryWords);
        return categoryWords;
    }

    public WordObject getWordById(int quizId){
        WordObject quizObject = null;
        String query = "select * from dictionary where id = " + quizId;
        Cursor cursor = this.getDbConnection().rawQuery(query, null);
        if(cursor.moveToFirst()){
            String kg = cursor.getString(cursor.getColumnIndexOrThrow("kg"));
            String ru = cursor.getString(cursor.getColumnIndexOrThrow("ru"));
            String tr = cursor.getString(cursor.getColumnIndexOrThrow("tr"));
            byte[] img = cursor.getBlob(cursor.getColumnIndexOrThrow("img"));
            byte[] snd = cursor.getBlob(cursor.getColumnIndexOrThrow("snd"));
            int categ = cursor.getInt(cursor.getColumnIndexOrThrow("cat_id"));
            quizObject = new WordObject(kg, ru, tr, img, snd, categ);
        }
        cursor.close();
        return quizObject;
    }

    public ArrayObject getWordByCategory(long cat_id){
        String query = "select * from dictionary where cat_id = " + cat_id;
        Cursor cursor = this.getDbConnection().rawQuery(query, null);
        ArrayList<String> wordTerms = new ArrayList<>();
        ArrayList<Integer> idTerms = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                String word = cursor.getString(cursor.getColumnIndexOrThrow("kg"));
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                wordTerms.add(word);
                idTerms.add(id);
            } while(cursor.moveToNext());
        }
        cursor.close();
        String[] words = new String[wordTerms.size()];
        words = wordTerms.toArray(words);
        int[] ret = new int[idTerms.size()];
        for (int i=0; i < ret.length; i++)
        {
            ret[i] = idTerms.get(i);
        }
        return new ArrayObject(words, null, null, ret);
    }
}
