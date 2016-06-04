package klep.wehere;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import klep.wehere.model.users.Data;
import klep.wehere.model.users.Users;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by klep on 15.05.16 with love.
 */
public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "wehere.db";
    private static final int DATABASE_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    static {
        cupboard().register(Data.class);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        cupboard().withDatabase(db).createTables();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        cupboard().withDatabase(db).upgradeTables();
    }
}
