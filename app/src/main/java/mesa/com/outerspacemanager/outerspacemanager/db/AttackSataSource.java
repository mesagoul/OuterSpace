package mesa.com.outerspacemanager.outerspacemanager.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import mesa.com.outerspacemanager.outerspacemanager.model.Attack;
import mesa.com.outerspacemanager.outerspacemanager.model.Ship;
import mesa.com.outerspacemanager.outerspacemanager.model.Ships;

/**
 * Created by Lucas on 20/03/2017.
 */

public class AttackSataSource {

    // Database fields
    private Gson gson;
    private SQLiteDatabase database;
    private OuterSpaceDB dbHelper;
    private String[] allColumns = {
            OuterSpaceDB.KEY_ID,
            OuterSpaceDB.KEY_SHIPS,
            OuterSpaceDB.KEY_USERNAME,
            OuterSpaceDB.KEY_ATTACKTIME,
            OuterSpaceDB.KEY_BEGINATTACKTIME
    };
    public AttackSataSource(Context context) {
        dbHelper = new OuterSpaceDB(context);
    }
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
        gson = new Gson();
    }
    public void close() {
        dbHelper.close();
    }

    public void createAttack(Attack attack) {
        ContentValues values = new ContentValues();

        UUID newID = UUID.randomUUID();

        values.put(OuterSpaceDB.KEY_ID, newID.toString());
        values.put(OuterSpaceDB.KEY_SHIPS, gson.toJson(attack.getShips()));
        values.put(OuterSpaceDB.KEY_USERNAME, attack.getUsername());
        values.put(OuterSpaceDB.KEY_ATTACKTIME, attack.getAttack_time());
        values.put(OuterSpaceDB.KEY_BEGINATTACKTIME, System.currentTimeMillis());


        database.insert(OuterSpaceDB.ATTAQUE_TABLE_NAME, null,
                values);
    }


    public List<Attack> getAllAttacks() {
        List<Attack> lesAttacks = new ArrayList<Attack>();

        Cursor cursor = database.query(OuterSpaceDB.ATTAQUE_TABLE_NAME,
                allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Attack anAttack = cursorToAttack(cursor);
            lesAttacks.add(anAttack);
            cursor.moveToNext();
        }
        cursor.close();
        return lesAttacks;
    }

    private Attack cursorToAttack(Cursor cursor) {
        Attack attack = new Attack();
        Ships ships = gson.fromJson(cursor.getString(1), Ships.class);

        String result = cursor.getString(0);
        attack.setId(UUID.fromString(result));
        attack.setShips(ships);
        attack.setUsername(cursor.getString(2));
        attack.setAttack_time(cursor.getLong(3));
        attack.setBegin_attack_time(cursor.getLong(4));
        return attack;
    }

        public void deleteAttack(Attack anAttack) {
        UUID id = anAttack.getId();
        database.delete(OuterSpaceDB.ATTAQUE_TABLE_NAME, OuterSpaceDB.KEY_ID
                + " = \"" + id.toString()+"\"", null);
    }
}
