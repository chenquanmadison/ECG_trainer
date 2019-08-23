/*
 * Copyright (C) 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.example.wordlistsqlsearchable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Open helper for the list of words database.
 */
public class WordListOpenHelper extends SQLiteOpenHelper {

    private static final String TAG = WordListOpenHelper.class.getSimpleName();

    // Declaring all these as constants makes code a lot more readable and looking like SQL.

    // Version has to be 1 first time or app will crash.
    private static final int DATABASE_VERSION = 1;
    private static final String WORD_LIST_TABLE = "word_entries";
    private static final String DATABASE_NAME = "wordlist";

    // Column names...
    public static final String KEY_ID = "_id";
    public static final String KEY_WORD = "word";
    public static final String KEY_RATE = "rate";
    public static final String KEY_PAMP = "pamp";
    public static final String KEY_QAMP = "qamp";
    public static final String KEY_RAMP = "ramp";
    public static final String KEY_SAMP = "samp";
    public static final String KEY_TAMP = "tamp";
    public static final String KEY_PDUR = "pdur";
    public static final String KEY_QDUR = "qdur";
    public static final String KEY_RDUR = "rdur";
    public static final String KEY_SDUR = "sdur";
    public static final String KEY_TDUR = "tdur";
    public static final String KEY_STSEG = "stseg";
    public static final String KEY_PRSEG = "prseg";








    // ... and a string array of columns.
    private static final String[] COLUMNS =
            {KEY_ID, KEY_WORD, KEY_RATE, KEY_PAMP, KEY_QAMP, KEY_RAMP, KEY_SAMP, KEY_TAMP, KEY_PDUR, KEY_QDUR, KEY_RDUR, KEY_SDUR, KEY_TDUR,KEY_STSEG,KEY_PRSEG};

    // Build the SQL query that creates the table.
    private static final String WORD_LIST_TABLE_CREATE =
            "CREATE TABLE " + WORD_LIST_TABLE + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY, " + // will auto-increment if no value passed
                    KEY_WORD + " TEXT, " +
                    KEY_PAMP + " TEXT, " +
                    KEY_QAMP + " TEXT, " +
                    KEY_RAMP + " TEXT, " +
                    KEY_SAMP + " TEXT, " +
                    KEY_TAMP + " TEXT, " +
                    KEY_PDUR + " TEXT, " +
                    KEY_QDUR + " TEXT, " +
                    KEY_RDUR + " TEXT, " +
                    KEY_SDUR + " TEXT, " +
                    KEY_TDUR + " TEXT, " +
                    KEY_STSEG + " TEXT, " +
                    KEY_PRSEG + " TEXT, " +
                    KEY_RATE + " TEXT )";

    private SQLiteDatabase mWritableDB;
    private SQLiteDatabase mReadableDB;

    public WordListOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, "Construct WordListOpenHelper");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(WORD_LIST_TABLE_CREATE);
        fillDatabaseWithData(db);
    }

    /**
     * Adds the initial data set to the database.
     * According to the docs, onCreate for the open helper does not run on the UI thread.
     *
     * @param db Database to fill with data since the member variables are not initialized yet.
     */
    public void fillDatabaseWithData(SQLiteDatabase db) {

        String[] words = {"Sinus", "Junctional","Arial Fib", "Ventricular Escape"};
        Integer[] rate = {60,70,80,90};
        Integer[] pamp = {250,700,800,900};
        Integer[] qamp = {200,700,800,900};
        Integer[] ramp = {1500,700,800,900};
        Integer[] samp = {200,700,800,900};
        Integer[] tamp = {200,700,800,900};
        Integer[] pdur = {200,700,800,900};
        Integer[] qdur = {100,700,800,900};
        Integer[] rdur = {50,700,800,900};
        Integer[] sdur = {100,700,800,900};
        Integer[] tdur = {200,700,800,900};
        Integer[] stseg = {0,0,0,0};
        Integer[] prseg = {0,0,0,0};






        // Create a container for the data.
        ContentValues values = new ContentValues();

        for (int i=0; i < words.length; i++) {
            // Put column/value pairs for current row into the container.
            values.put(KEY_WORD, words[i]); // put() overrides existing values.
            values.put(KEY_RATE, rate[i]); // put() overrides existing values.
            values.put(KEY_PAMP, pamp[i]); // put() overrides existing values.
            values.put(KEY_QAMP, qamp[i]); // put() overrides existing values.
            values.put(KEY_RAMP, ramp[i]); // put() overrides existing values.
            values.put(KEY_SAMP, samp[i]); // put() overrides existing values.
            values.put(KEY_TAMP, tamp[i]); // put() overrides existing values.

            values.put(KEY_PDUR, pdur[i]); // put() overrides existing values.
            values.put(KEY_QDUR, qdur[i]); // put() overrides existing values.
            values.put(KEY_RDUR, rdur[i]); // put() overrides existing values.
            values.put(KEY_SDUR, sdur[i]); // put() overrides existing values.
            values.put(KEY_TDUR, tdur[i]); // put() overrides existing values.

            values.put(KEY_STSEG, stseg[i]); // put() overrides existing values.
            values.put(KEY_PRSEG, prseg[i]); // put() overrides existing values.



            // Insert the row.
            db.insert(WORD_LIST_TABLE, null, values);
        }
    }

    public Cursor search(String searchString) {
        String[] columns = new String[]{KEY_WORD};
        String where =  KEY_WORD + " LIKE ?";
        searchString = "%" + searchString + "%";
        String[] whereArgs = new String[]{searchString};

        Cursor cursor = null;
        try {
            if (mReadableDB == null) {
                mReadableDB = getReadableDatabase();
            }
            cursor = mReadableDB.query(WORD_LIST_TABLE, columns, where, whereArgs, null, null, null);
        } catch (Exception e) {
            Log.d(TAG, "SEARCH EXCEPTION! " + e); // Just log the exception
        }
        return cursor;
    }


    /**
     * Queries the database for an entry at a given position.
     *
     * @param position The Nth row in the table.
     * @return a WordItem with the requested database entry.
     */
    public WordItem query(int position) {
        String query = "SELECT  * FROM " + WORD_LIST_TABLE +
                " ORDER BY " + KEY_WORD + " ASC " +
                "LIMIT " + position + ",1";

        Cursor cursor = null;
        WordItem entry = new WordItem();

        try {
            if (mReadableDB == null) {
                mReadableDB = getReadableDatabase();
            }
            cursor = mReadableDB.rawQuery(query, null);
            cursor.moveToFirst();

            entry.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
            entry.setWord(cursor.getString(cursor.getColumnIndex(KEY_WORD)));
            entry.setRate(cursor.getString(cursor.getColumnIndex(KEY_RATE)));
            entry.setPamp(cursor.getString(cursor.getColumnIndex(KEY_PAMP)));
            entry.setQamp(cursor.getString(cursor.getColumnIndex(KEY_QAMP)));
            entry.setRamp(cursor.getString(cursor.getColumnIndex(KEY_RAMP)));
            entry.setSamp(cursor.getString(cursor.getColumnIndex(KEY_SAMP)));
            entry.setTamp(cursor.getString(cursor.getColumnIndex(KEY_TAMP)));
            entry.setPdur(cursor.getString(cursor.getColumnIndex(KEY_PDUR)));
            entry.setQdur(cursor.getString(cursor.getColumnIndex(KEY_QDUR)));
            entry.setRdur(cursor.getString(cursor.getColumnIndex(KEY_RDUR)));
            entry.setSdur(cursor.getString(cursor.getColumnIndex(KEY_SDUR)));
            entry.setTdur(cursor.getString(cursor.getColumnIndex(KEY_TDUR)));
            entry.setSTseg(cursor.getString(cursor.getColumnIndex(KEY_STSEG)));
            entry.setPRseg(cursor.getString(cursor.getColumnIndex(KEY_PRSEG)));


        } catch (Exception e) {
            Log.d(TAG, "QUERY EXCEPTION! " + e); // Just log the exception
        } finally {
            // Must close cursor and db now that we are done with it.
            cursor.close();
            return entry;
        }
    }

    /**
     * Gets the number of rows in the word list table.
     *
     * @return The number of entries in WORD_LIST_TABLE.
     */
    public long count() {
        if (mReadableDB == null) {
            mReadableDB = getReadableDatabase();
        }
        return DatabaseUtils.queryNumEntries(mReadableDB, WORD_LIST_TABLE);
    }

    /**
     * Adds a single word row/entry to the database.
     *
     * @param  word New word.
     * @return The id of the inserted word.
     */
    public long insert(String word,String rate,String pamp, String qamp, String ramp, String samp, String tamp,String pdur, String qdur, String rdur, String sdur, String tdur,String stseg,String prseg) {
        long newId = 0;
        ContentValues values = new ContentValues();
        values.put(KEY_RATE, rate);
        values.put(KEY_WORD, word);
        values.put(KEY_PAMP, pamp);
        values.put(KEY_QAMP, qamp);
        values.put(KEY_RAMP, ramp);
        values.put(KEY_SAMP, samp);
        values.put(KEY_TAMP, tamp);
        values.put(KEY_PDUR, pdur);
        values.put(KEY_QDUR, qdur);
        values.put(KEY_RDUR, rdur);
        values.put(KEY_SDUR, sdur);
        values.put(KEY_TDUR, tdur);
        values.put(KEY_STSEG, stseg);
        values.put(KEY_PRSEG, prseg);


        try {
            if (mWritableDB == null) {
                mWritableDB = getWritableDatabase();
            }
            newId = mWritableDB.insert(WORD_LIST_TABLE, null, values);
        } catch (Exception e) {
            Log.d(TAG, "INSERT EXCEPTION! " + e);
        }
        return newId;
    }

    /**
     * Updates the word with the supplied id to the supplied value.
     *
     * @param id Id of the word to update.
     * @param word The new value of the word.
     * @return the number of rows affected or -1 of nothing was updated.
     */
    public int update(int id, String word, String rate,String pamp, String qamp, String ramp, String samp, String tamp,String pdur, String qdur, String rdur, String sdur, String tdur,String stseg, String prseg) {
        int mNumberOfRowsUpdated = -1;
        try {
            if (mWritableDB == null) {
                mWritableDB = getWritableDatabase();
            }
            ContentValues values = new ContentValues();
            values.put(KEY_WORD, word);
            values.put(KEY_RATE, rate);
            values.put(KEY_PAMP, pamp);
            values.put(KEY_QAMP, qamp);
            values.put(KEY_RAMP, ramp);
            values.put(KEY_SAMP, samp);
            values.put(KEY_TAMP, tamp);
            values.put(KEY_PDUR, pdur);
            values.put(KEY_QDUR, qdur);
            values.put(KEY_RDUR, rdur);
            values.put(KEY_SDUR, sdur);
            values.put(KEY_TDUR, tdur);
            values.put(KEY_STSEG, stseg);
            values.put(KEY_PRSEG, prseg);



            mNumberOfRowsUpdated = mWritableDB.update(WORD_LIST_TABLE, //table to change
                    values, // new values to insert
                    KEY_ID + " = ?", // selection criteria for row (in this case, the _id column)
                    new String[]{String.valueOf(id)}); //selection args; the actual value of the id

        } catch (Exception e) {
            Log.d (TAG, "UPDATE EXCEPTION! " + e);
        }
        return mNumberOfRowsUpdated;
    }

    /**
     * Deletes one entry identified by its id.
     *
     * @param id ID of the entry to delete.
     * @return The number of rows deleted. Since we are deleting by id, this should be 0 or 1.
     */
    public int delete(int id) {
        int deleted = 0;
        try {
            if (mWritableDB == null) {
                mWritableDB = getWritableDatabase();
            }

            deleted = mWritableDB.delete(WORD_LIST_TABLE, //table name
                    KEY_ID + " =? ", new String[]{String.valueOf(id)});

        } catch (Exception e) {
            Log.d (TAG, "DELETE EXCEPTION! " + e);        }
        return deleted;
    }

    /**
     * Called when a database needs to be upgraded. The most basic version of this method drops
     * the tables, and then recreates them. All data is lost, which is why for a production app,
     * you want to back up your data first. If this method fails, changes are rolled back.
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(WordListOpenHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + WORD_LIST_TABLE);
        onCreate(db);
    }
}
