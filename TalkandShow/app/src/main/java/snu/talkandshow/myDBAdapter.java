package snu.talkandshow;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Seungyong on 2015-12-20.
 */
public class myDBAdapter {
    protected static final String TAG = "DataAdapter";

    private final Context mContext;
    private SQLiteDatabase mDb;
    private DBHelper mDbHelper;

    public myDBAdapter(Context context, String fileName)
    {
        this.mContext = context;
        mDbHelper = new DBHelper(mContext, fileName);
    }
    public myDBAdapter createDatabase() throws SQLException
    {
        try
        {
            mDbHelper.createDataBase();
        }
        catch (IOException mIOException)
        {
            Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }

    public myDBAdapter open() throws SQLException
    {
        try
        {
            mDbHelper.openDataBase();
            mDbHelper.close();
            mDb = mDbHelper.getReadableDatabase();
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "open >>"+ mSQLException.toString());
            throw mSQLException;
        }
        return this;
    }

    public void close()
    {
        mDbHelper.close();
    }
    public int getTableCount(String tableName) {
        String countQuery = "SELECT * FROM "+tableName;
        Cursor cursor = mDb.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
    public boolean isInTable(int event_id, String tableName){
        String sql ="SELECT * FROM "+tableName+" WHERE event_id="+event_id+"";
        Cursor cursor = mDb.rawQuery(sql, null);
        int count = cursor.getCount();
        if(count==0)
            return false;
        else
            return true;
    }
    public ArrayList<Integer> getIndex(String tableName) {
        try {
            String sql;
            sql = "SELECT * FROM " + tableName;
            ArrayList<Integer> index = new ArrayList<Integer>();
            Cursor mCur = mDb.rawQuery(sql, null);
            mCur.moveToFirst();
            while (!mCur.isAfterLast()) {
                index.add(mCur.getInt(mCur.getColumnIndex("event_id")));
                mCur.moveToNext();
            }
            mCur.close();
            return index;
        } catch (SQLException mSQLException) {
            Log.e(TAG, "getIndex >>" + mSQLException.toString());
            throw mSQLException;
        }
    }
    public void saveData(int id, int event_id, int date, int time, String tableName){
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", id);
        contentValues.put("event_id",event_id);
        contentValues.put("date",date);
        contentValues.put("time", time);
        mDb.insert(tableName, null, contentValues);
    }
    public void removeData(int event_id, String tableName){
        mDb.delete(tableName, "event_id="+event_id, null);
    }
}
