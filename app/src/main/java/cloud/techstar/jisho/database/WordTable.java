package cloud.techstar.jisho.database;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class WordTable {

    public WordTable() {
        Word word = new Word();
    }

    public static String create(){
        return "CREATE TABLE "+ Word.TABLE_WORDS+" (" +
                Word.WORDS_ID + " TEXT PRIMARY KEY," +
                Word.WORDS_CHARACTER + " TEXT," +
                Word.WORDS_MEANING + " TEXT," +
                Word.WORDS_MEANING_MN + " TEXT," +
                Word.WORDS_KANJI + " TEXT," +
                Word.WORDS_PART_OF_SPEECH + " TEXT," +
                Word.WORDS_LEVEL + " TEXT," +
                Word.WORDS_IS_MEMORIZE + " TEXT," +
                Word.WORDS_IS_FAVORITE + " TEXT," +
                Word.WORDS_CREATED + " TEXT);";
    }

    public void insert(Word word) {
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
            cv.put(Word.WORDS_ID, word.getId());
            cv.put(Word.WORDS_CHARACTER, word.getCharacter());
            cv.put(Word.WORDS_MEANING, word.getMeaning());
            cv.put(Word.WORDS_MEANING_MN, word.getMeaningMon());
            cv.put(Word.WORDS_KANJI, word.getKanji());
            cv.put(Word.WORDS_PART_OF_SPEECH, word.getPartOfSpeech());
            cv.put(Word.WORDS_LEVEL, word.getLevel());
            cv.put(Word.WORDS_IS_MEMORIZE, word.getIsMemorize());
            cv.put(Word.WORDS_IS_FAVORITE, word.getIsFavorite());
            cv.put(Word.WORDS_CREATED, word.getCreated());
            db.insert(Word.TABLE_WORDS, null, cv);
            db.setTransactionSuccessful();
        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
            db.endTransaction();
            DatabaseManager.getInstance().closeDatabase();
        }
    }

    public Word select(String id) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        if (db == null) {
            return null;
        }
        Cursor cursor = db.query(Word.TABLE_WORDS, new String[]{
                        Word.WORDS_ID,
                        Word.WORDS_CHARACTER,
                        Word.WORDS_MEANING,
                        Word.WORDS_MEANING_MN,
                        Word.WORDS_KANJI,
                        Word.WORDS_PART_OF_SPEECH,
                        Word.WORDS_LEVEL,
                        Word.WORDS_IS_MEMORIZE,
                        Word.WORDS_IS_FAVORITE,
                        Word.WORDS_CREATED
                }, Word.WORDS_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (!cursor.moveToFirst()) {
            return null;
        }

        Word word = new Word();
        word.setId(cursor.getString(Word.WORDS_ID_INDEX));
        word.setCharacter(cursor.getString(Word.WORDS_CHARACTER_INDEX));
        word.setMeaning(cursor.getString(Word.WORDS_MEANING_INDEX));
        word.setMeaningMon(cursor.getString(Word.WORDS_MEANING_MN_INDEX));
        word.setKanji(cursor.getString(Word.WORDS_KANJI_INDEX));
        word.setPartOfSpeech(cursor.getString(Word.WORDS_PART_OF_SPEECH_INDEX));
        word.setLevel(cursor.getString(Word.WORDS_LEVEL_INDEX));
        word.setIsMemorize(cursor.getString(Word.WORDS_IS_MEMORIZE_INDEX));
        word.setIsFavorite(cursor.getString(Word.WORDS_IS_FAVORITE_INDEX));
        word.setCreated(cursor.getString(Word.WORDS_CREATED_INDEX));
        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        return word;
    }

    public List<Word> selectAll() {
        List<Word> words = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + Word.TABLE_WORDS;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Word word = new Word();
                word.setId(cursor.getString(Word.WORDS_ID_INDEX));
                word.setCharacter(cursor.getString(Word.WORDS_CHARACTER_INDEX));
                word.setMeaning(cursor.getString(Word.WORDS_MEANING_INDEX));
                word.setMeaningMon(cursor.getString(Word.WORDS_MEANING_MN_INDEX));
                word.setKanji(cursor.getString(Word.WORDS_KANJI_INDEX));
                word.setPartOfSpeech(cursor.getString(Word.WORDS_PART_OF_SPEECH_INDEX));
                word.setLevel(cursor.getString(Word.WORDS_LEVEL_INDEX));
                word.setIsMemorize(cursor.getString(Word.WORDS_IS_MEMORIZE_INDEX));
                word.setIsFavorite(cursor.getString(Word.WORDS_IS_FAVORITE_INDEX));
                word.setCreated(cursor.getString(Word.WORDS_CREATED_INDEX));
                words.add(word);
            } while (cursor.moveToNext());
        }
        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        return words;
    }

    public List<Word> selectFavorite() {
        List<Word> words = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + Word.TABLE_WORDS + " WHERE " + Word.WORDS_IS_FAVORITE + " = 'true'" ;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Word word = new Word();
                word.setId(cursor.getString(Word.WORDS_ID_INDEX));
                word.setCharacter(cursor.getString(Word.WORDS_CHARACTER_INDEX));
                word.setMeaning(cursor.getString(Word.WORDS_MEANING_INDEX));
                word.setMeaningMon(cursor.getString(Word.WORDS_MEANING_MN_INDEX));
                word.setKanji(cursor.getString(Word.WORDS_KANJI_INDEX));
                word.setPartOfSpeech(cursor.getString(Word.WORDS_PART_OF_SPEECH_INDEX));
                word.setLevel(cursor.getString(Word.WORDS_LEVEL_INDEX));
                word.setIsMemorize(cursor.getString(Word.WORDS_IS_MEMORIZE_INDEX));
                word.setIsFavorite(cursor.getString(Word.WORDS_IS_FAVORITE_INDEX));
                word.setCreated(cursor.getString(Word.WORDS_CREATED_INDEX));
                words.add(word);
            } while (cursor.moveToNext());
        }
        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        return words;
    }

    public int update(Word word) {
        if (word == null) {
            return -1;
        }
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        if (db == null) {
            return -1;
        }
        ContentValues cv = new ContentValues();
        cv.put(Word.WORDS_ID, word.getId());
        cv.put(Word.WORDS_CHARACTER, word.getCharacter());
        cv.put(Word.WORDS_MEANING, word.getMeaning());
        cv.put(Word.WORDS_MEANING_MN, word.getMeaningMon());
        cv.put(Word.WORDS_KANJI, word.getKanji());
        cv.put(Word.WORDS_PART_OF_SPEECH, word.getPartOfSpeech());
        cv.put(Word.WORDS_LEVEL, word.getLevel());
        cv.put(Word.WORDS_IS_MEMORIZE, word.getIsMemorize());
        cv.put(Word.WORDS_IS_FAVORITE, word.getIsFavorite());
        cv.put(Word.WORDS_CREATED, word.getCreated());
        int rowCount = db.update(Word.TABLE_WORDS, cv, Word.WORDS_ID + "=?",
                new String[]{String.valueOf(word.getId())});
        DatabaseManager.getInstance().closeDatabase();
        return rowCount;
    }

    public void delete(Word word) {
        if (word == null) {
            return;
        }
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        if (db == null) {
            return;
        }
        db.delete(Word.TABLE_WORDS, Word.WORDS_ID + "=?", new String[]{String.valueOf(word.getId())});
        DatabaseManager.getInstance().closeDatabase();
    }

    public void deleteAll()
    {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Word.TABLE_WORDS, null, null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public int count() {
        String query = "SELECT * FROM  " + Word.TABLE_WORDS;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();
        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        return count;
    }
}
