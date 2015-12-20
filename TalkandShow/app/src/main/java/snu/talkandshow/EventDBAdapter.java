package snu.talkandshow;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

public class EventDBAdapter
{
    protected static final String TAG = "DataAdapter";

    private final Context mContext;
    private SQLiteDatabase mDb;
    private DBHelper mDbHelper;

    public EventDBAdapter(Context context, String fileName)
    {
        this.mContext = context;
        mDbHelper = new DBHelper(mContext, fileName);
    }

    public EventDBAdapter createDatabase() throws SQLException
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

    public EventDBAdapter open() throws SQLException
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
    public void saveEvent(Event event){
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", event.getId());
        contentValues.put("type", event.getType());
        contentValues.put("title",event.getTitle());
        contentValues.put("place",event.getPlace());
        contentValues.put("date",event.getDate());
        contentValues.put("host",event.getHost());
        contentValues.put("contact",event.getContact());
        contentValues.put("fee",event.getFee());
        contentValues.put("info",event.getInfo());
        mDb.insert("events", null, contentValues);
    }
    public int getCount() {
        String countQuery = "SELECT  * FROM events";
        Cursor cursor = mDb.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
    public Event loadEvent(int id)
    {
        try {
            String sql ="SELECT * FROM events WHERE _id="+id+"";
            Cursor mCur = mDb.rawQuery(sql, null);
            Event event = new Event();
            mCur.moveToFirst();
            while(!mCur.isAfterLast())
            {
                event.setId(mCur.getInt(mCur.getColumnIndex("_id")));
                event.setTitle(mCur.getString(mCur.getColumnIndex("title")));
                event.setPlace(mCur.getString(mCur.getColumnIndex("place")));
                event.setDate(mCur.getInt(mCur.getColumnIndex("date")));
                mCur.moveToNext();
            }
            mCur.close();
            return event;
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "loadEvent >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }
    public Event loadEventDetail(int id)
    {
        try {
            String sql ="SELECT * FROM events WHERE _id="+id+"";
            Cursor mCur = mDb.rawQuery(sql, null);
            Event event = new Event();
            mCur.moveToFirst();
            while(!mCur.isAfterLast())
            {
                event.setId(mCur.getInt(mCur.getColumnIndex("_id")));
                event.setTitle(mCur.getString(mCur.getColumnIndex("title")));
                event.setPlace(mCur.getString(mCur.getColumnIndex("place")));
                event.setDate(mCur.getInt(mCur.getColumnIndex("date")));
                event.setHost(mCur.getString(mCur.getColumnIndex("host")));
                event.setFee(mCur.getInt(mCur.getColumnIndex("fee")));
                event.setContact(mCur.getString(mCur.getColumnIndex("contact")));
                event.setInfo(mCur.getString(mCur.getColumnIndex("info")));
                mCur.moveToNext();
            }
            mCur.close();
            return event;
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "loadEventDetail >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }
    public ArrayList<Integer> getIndex(int type)
    {
        try {
            String sql;
            if(type==0) {
                sql = "SELECT _id FROM events";
            }
            else {
                sql = "SELECT _id FROM events WHERE type='"+type+"'";
            }
            ArrayList<Integer> index = new ArrayList<Integer>();
            Cursor mCur = mDb.rawQuery(sql,null);
            mCur.moveToFirst();
            while(!mCur.isAfterLast())
            {
                index.add(mCur.getInt(mCur.getColumnIndex("_id")));
                mCur.moveToNext();
            }
            mCur.close();
            return index;
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "getIndex >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }
}