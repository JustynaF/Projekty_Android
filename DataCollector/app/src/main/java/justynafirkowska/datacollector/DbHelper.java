package justynafirkowska.datacollector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "DataCollectorDb.db";

    public static final String PERSONS_TABLE_NAME = "persons";
    public static final String PERSONS_COLUMN_ID = "id";
    public static final String PERSONS_COLUMN_FN = "FirstName";
    public static final String PERSONS_COLUMN_LN = "LastName";
    public static final String PERSONS_COLUMN_BIRTH = "Birth";

    public static final String HISTORY_TABLE_NAME = "history";
    public static final String HISTORY_COLUMN_ID = "id";
    public static final String HISTORY_COLUMN_MSG = "message";
    public static final String HISTORY_COLUMN_DATE = "date";

    private Context context;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table " + PERSONS_TABLE_NAME + " (" +
                        PERSONS_COLUMN_ID + " integer primary key," +
                        PERSONS_COLUMN_FN + " text," +
                        PERSONS_COLUMN_LN + " text," +
                        PERSONS_COLUMN_BIRTH + " text)"
        );

        db.execSQL(
                "create table " + HISTORY_TABLE_NAME + " (" +
                        HISTORY_COLUMN_ID + " integer primary key," +
                        HISTORY_COLUMN_MSG + " text," +
                        HISTORY_COLUMN_DATE + " text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PERSONS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + HISTORY_TABLE_NAME);
        onCreate(db);
    }

    public void insertPerson (String firstName, String lastName, String birthDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PERSONS_COLUMN_FN, firstName);
        contentValues.put(PERSONS_COLUMN_LN, lastName);
        contentValues.put(PERSONS_COLUMN_BIRTH, birthDate);
        db.insert(PERSONS_TABLE_NAME, null, contentValues);
    }

    public ArrayList<Person> getAllPersons() {
        ArrayList<Person> persons = new ArrayList<Person>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + PERSONS_TABLE_NAME, null );
        res.moveToFirst();
        SimpleDateFormat birthFormat = new SimpleDateFormat(this.context.getString(R.string.BirthFormat));
        while(!res.isAfterLast()){
            try {
                Person person = new Person(
                    res.getInt(res.getColumnIndex(PERSONS_COLUMN_ID)),
                    res.getString(res.getColumnIndex(PERSONS_COLUMN_FN)),
                    res.getString(res.getColumnIndex(PERSONS_COLUMN_LN)),
                    birthFormat.parse(res.getString(res.getColumnIndex(PERSONS_COLUMN_BIRTH)))
                );
                persons.add(person);
            }
            catch (ParseException e)
            {
                Log.e("ParseException", e.getMessage());
            }
            res.moveToNext();
        }
        return persons;
    }

    public void updatePerson (Integer id, String fName, String lName, String birth) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PERSONS_COLUMN_FN, fName);
        contentValues.put(PERSONS_COLUMN_LN, lName);
        contentValues.put(PERSONS_COLUMN_BIRTH, birth);
        db.update(PERSONS_TABLE_NAME, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
    }

    public Integer deletePerson (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(PERSONS_TABLE_NAME, "id = ? ", new String[] { Integer.toString(id) });
    }

    public void cleanPersons() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + PERSONS_TABLE_NAME);
    }

    public void insertHistory (String msg) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(HISTORY_COLUMN_MSG, msg);
        contentValues.put(HISTORY_COLUMN_DATE, new Date().toString());
        db.insert(HISTORY_TABLE_NAME, null, contentValues);
    }
    public ArrayList<History> getHistory() {
        ArrayList<History> history = new ArrayList<History>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + HISTORY_TABLE_NAME, null );
        res.moveToFirst();
        while(!res.isAfterLast()){
            History hist = new History(
                    res.getString(res.getColumnIndex(HISTORY_COLUMN_MSG)),
                    res.getString(res.getColumnIndex(HISTORY_COLUMN_DATE))
            );
            history.add(hist);
            res.moveToNext();
        }
        return history;
    }
}
