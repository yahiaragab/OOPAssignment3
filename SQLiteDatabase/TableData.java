import android.provider.BaseColumns;

/**
 * Created by stephenmcgirr on 14/04/16.
 */
public class TableData {


    public static String TABLE_NAME;
    public static String DATABASE_NAME;
    public static String USER_NAME;
    public static String USER_PASS;

    public TableData ()
    {

    }

    public static abstract class TableInfo implements BaseColumns
    {
        public static final String USER_NAME = "user-name";
        public static final String USER_PASS = "user-pass";
        public static final String DATABASE_NAME = "user_info";
        public static final String TABLE_NAME = "reg_info ";
    }


   
}
