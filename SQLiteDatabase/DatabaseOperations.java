import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseOperations extends SQLiteOpenHelper {

    public static final int database_version = 1;
    public String CREATE_QUERY = "CREATE TABLE "+ TableData.TABLE_NAME+""+ TableData.USER_NAME+"TEXT,"+TableData.USER_PASS+"TEXT );";

    public DatabaseOperations(Context context)
    {

        super(context, TableData.DATABASE_NAME, null, database_version);
        Log.d("Database operations", "Database created");


    }

    @Override
    public void onCreate(SQLiteDatabase sdb)
    {
        sdb.execSQL(CREATE_QUERY);
        //log message
        Log.d("Database operations", "Database created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2)
    {

    }

    public void putInInformation(DatabaseOperations dop, String name, String pass)
    {
        //write data into database
        SQLiteDatabase SQ = dop.getWritableDatabase();

        //create object of content values to insert data
        ContentValues cv = new ContentValues();

        //add values for each column into content value object
        cv.put(TableData.USER_NAME, name);
        cv.put(TableData.USER_PASS, pass);

        //insert data into table using SQ object
        //null not using any column for inserting null values
        //will return a long value
        long k = SQ.insert(TableData.TABLE_NAME, null, cv);

        //
        Log.d("Database operations", "one row inserted");

    }

    public Cursor getInformation(DatabaseOperations dop)
    {
        //object for retrieving data
        SQLiteDatabase SQ = dop.getReadableDatabase();

        String[] columns = {TableData.USER_NAME, TableData.USER_PASS};

        Cursor CR = SQ.query(TableData.TABLE_NAME, columns, null, null, null, null);
        return CR;
    }


}
