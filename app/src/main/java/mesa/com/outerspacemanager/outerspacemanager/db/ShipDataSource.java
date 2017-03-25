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

import mesa.com.outerspacemanager.outerspacemanager.model.Ship;
import mesa.com.outerspacemanager.outerspacemanager.model.Ships;

/**
 * Created by Lucas on 21/03/2017.
 */

public class ShipDataSource {
    // Database fields
    private Gson gson;
    private SQLiteDatabase database;
    private OuterSpaceDB dbHelper;
    private String[] allColumns = {
            OuterSpaceDB.KEY_SHIP_ID,
            OuterSpaceDB.KEY_SHIP_LIBELLE,
    };
    public ShipDataSource(Context context) {
        dbHelper = new OuterSpaceDB(context);
    }
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }
    public void close() {
        dbHelper.close();
    }

    public void createShip(Ship ship) {

        if(!verifyShipExist(ship.getShipId())){
            ContentValues values = new ContentValues();

            values.put(OuterSpaceDB.KEY_SHIP_ID, ship.getShipId());
            values.put(OuterSpaceDB.KEY_SHIP_LIBELLE,  ship.getName());

            database.insert(OuterSpaceDB.SHIP_TABLE_NAME, null,
                    values);
        }

    }

    private boolean verifyShipExist(int shipId) {
        Cursor cursor = database.query(OuterSpaceDB.SHIP_TABLE_NAME,
                allColumns,OuterSpaceDB.KEY_SHIP_ID
                        + " = ?" , new String[]{String.valueOf(shipId)}, null, null, null);
        return cursor.getCount() > 0;

    }

    public Ship getShipByID(int shipId){
        Cursor cursor = database.query(OuterSpaceDB.SHIP_TABLE_NAME,
                allColumns,OuterSpaceDB.KEY_SHIP_ID
                        + " = ?" , new String[]{String.valueOf(shipId)}, null, null, null);
        cursor.moveToFirst();
        Ship newShip = cursorToShip(cursor);
        return newShip;
    }


    public List<Ship> getAllShips() {
        List<Ship> lesShips = new ArrayList<Ship>();

        Cursor cursor = database.query(OuterSpaceDB.SHIP_TABLE_NAME,
                allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Ship aShip = cursorToShip(cursor);
            lesShips.add(aShip);
            cursor.moveToNext();
        }
        cursor.close();
        return lesShips;
    }

    private Ship cursorToShip(Cursor cursor) {
        Ship ship = new Ship(cursor.getInt(0),cursor.getString(1));
        return ship;
    }

    public void deleteShip(Ship aShip) {
        int id = aShip.getShipId();
        database.delete(OuterSpaceDB.SHIP_TABLE_NAME, OuterSpaceDB.KEY_SHIP_ID
                + " = \"" + id+"\"", null);
    }
}
