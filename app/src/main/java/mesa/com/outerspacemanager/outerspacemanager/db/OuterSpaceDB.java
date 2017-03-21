package mesa.com.outerspacemanager.outerspacemanager.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

/**
 * Created by Lucas on 20/03/2017.
 */

public class OuterSpaceDB extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "OuterSpace.db";
    public static final String ATTAQUE_TABLE_NAME = "ATTACK";
    public static final String KEY_ID = "ID";
    public static final String KEY_SHIPS = "SHIPS";
    public static final String KEY_USERNAME = "USERNAME";
    public static final String KEY_ATTACKTIME = "ATTACK_TIME";
    public static final String KEY_BEGINATTACKTIME = "BEGIN_ATTACK_TIME";
    private static final String ATTACK_TABLE_CREATE =
            "CREATE TABLE " + ATTAQUE_TABLE_NAME +
                    " (" + KEY_ID + " TEXT, " +
                        KEY_SHIPS + " TEXT, " +
                        KEY_USERNAME + " TEXT," +
                        KEY_ATTACKTIME + " REAL," +
                        KEY_BEGINATTACKTIME + " REAL" +
                    ");";
    public OuterSpaceDB (Context context) {
        super(context, Environment.getExternalStorageDirectory()+"/"+DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(ATTACK_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ATTACK_TABLE_CREATE);
        onCreate(db);
    }
}
