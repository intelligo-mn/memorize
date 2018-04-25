package cloud.techstar.jisho.database;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import cloud.techstar.jisho.models.Words;

public class WordTable {

    public WordTable() {
        Words word = new Words();
    }

    public static String create(){
        return "CREATE TABLE "+ Words.TABLE_WORDS+" (" +
                Words.WORDS_ID + " TEXT PRIMARY KEY," +
                Words.WORDS_CHARACTER + " TEXT," +
                Words.WORDS_MEANING + " TEXT," +
                Words.WORDS_MEANING_MN + " TEXT," +
                Words.WORDS_KANJI + " TEXT," +
                Words.WORDS_PART_OF_SPEECH + " TEXT," +
                Words.WORDS_LEVEL + " TEXT," +
                Words.WORDS_ISMEMORIZE + " TEXT);";
    }

    public void insert(Words word) {
        if (word == null) {
            return;
        }
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        if (db == null) {
            return;
        }
        db.beginTransaction();
        try {
            ContentValues cv = new ContentValues();
            cv.put(Words.WORDS_ID, word.getId());
            cv.put(Words.WORDS_CHARACTER, word.getCharacter());
            cv.put(Words.WORDS_MEANING, word.getMeaning());
            cv.put(Words.WORDS_MEANING_MN, word.getMeaningMon());
            cv.put(Words.WORDS_KANJI, word.getKanji());
            cv.put(Words.WORDS_PART_OF_SPEECH, word.getPartOfSpeech());
            cv.put(Words.WORDS_LEVEL, word.getLevel());
            cv.put(Words.WORDS_ISMEMORIZE, word.getIsMemorize());
            db.insert(Words.TABLE_WORDS, null, cv);
            db.setTransactionSuccessful();
        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
            db.endTransaction();
            DatabaseManager.getInstance().closeDatabase();
        }
    }

    public Words select(int id) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        if (db == null) {
            return null;
        }
        Cursor cursor = db.query(Words.TABLE_WORDS, new String[]{
                        Words.WORDS_ID,
                        Words.WORDS_CHARACTER,
                        Words.WORDS_MEANING,
                        Words.WORDS_MEANING_MN,
                        Words.WORDS_KANJI,
                        Words.WORDS_PART_OF_SPEECH,
                        Words.WORDS_LEVEL,
                        Words.WORDS_ISMEMORIZE
                }, Words.WORDS_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (!cursor.moveToFirst()) {
            return null;
        }

        Words word = new Words();
        word.setId(cursor.getString(Words.WORDS_ID_INDEX));
        word.setCharacter(cursor.getString(Words.WORDS_CHARACTER_INDEX));
        word.setMeaning(cursor.getString(Words.WORDS_MEANING_INDEX));
        word.setMeaningMon(cursor.getString(Words.WORDS_MEANING_MN_INDEX));
        word.setKanji(cursor.getString(Words.WORDS_KANJI_INDEX));
        word.setPartOfSpeech(cursor.getString(Words.WORDS_PART_OF_SPEECH_INDEX));
        word.setLevel(cursor.getString(Words.WORDS_LEVEL_INDEX));
        word.setIsMemorize(cursor.getString(Words.WORDS_ISMEMORIZE_INDEX));
        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        return word;
    }

    public List<Words> selectAll() {
        List<Words> words = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + Words.TABLE_WORDS;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Words word = new Words();
                word.setId(cursor.getString(Words.WORDS_ID_INDEX));
                word.setCharacter(cursor.getString(Words.WORDS_CHARACTER_INDEX));
                word.setMeaning(cursor.getString(Words.WORDS_MEANING_INDEX));
                word.setMeaningMon(cursor.getString(Words.WORDS_MEANING_MN_INDEX));
                word.setKanji(cursor.getString(Words.WORDS_KANJI_INDEX));
                word.setPartOfSpeech(cursor.getString(Words.WORDS_PART_OF_SPEECH_INDEX));
                word.setLevel(cursor.getString(Words.WORDS_LEVEL_INDEX));
                word.setIsMemorize(cursor.getString(Words.WORDS_ISMEMORIZE_INDEX));
                words.add(word);
            } while (cursor.moveToNext());
        }
        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        return words;
    }

    public int update(Words word) {
        if (word == null) {
            return -1;
        }
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        if (db == null) {
            return -1;
        }
        ContentValues cv = new ContentValues();
        cv.put(Words.WORDS_ID, word.getId());
        cv.put(Words.WORDS_CHARACTER, word.getCharacter());
        cv.put(Words.WORDS_MEANING, word.getMeaning());
        cv.put(Words.WORDS_MEANING_MN, word.getMeaningMon());
        cv.put(Words.WORDS_KANJI, word.getKanji());
        cv.put(Words.WORDS_PART_OF_SPEECH, word.getPartOfSpeech());
        cv.put(Words.WORDS_LEVEL, word.getLevel());
        cv.put(Words.WORDS_ISMEMORIZE, word.getIsMemorize());
        int rowCount = db.update(Words.TABLE_WORDS, cv, Words.WORDS_ID + "=?",
                new String[]{String.valueOf(word.getId())});
        DatabaseManager.getInstance().closeDatabase();
        return rowCount;
    }

    public void delete(Words word) {
        if (word == null) {
            return;
        }
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        if (db == null) {
            return;
        }
        db.delete(Words.TABLE_WORDS, Words.WORDS_ID + "=?", new String[]{String.valueOf(word.getId())});
        DatabaseManager.getInstance().closeDatabase();
    }

    public void deleteAll()
    {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Words.TABLE_WORDS, null, null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public int count() {
        String query = "SELECT * FROM  " + Words.TABLE_WORDS;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();
        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        return count;
    }
}
