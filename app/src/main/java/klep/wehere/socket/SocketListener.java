package klep.wehere.socket;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import klep.wehere.DbHelper;
import klep.wehere.maps.MapPresenter;
import klep.wehere.model.user.User;
import klep.wehere.model.users.Data;
import klep.wehere.model.users.Users;
import nl.qbusict.cupboard.QueryResultIterable;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by klep.io on 21.02.16.
 */
public class SocketListener extends HandlerSubscribe {
    private Context context;
    private SQLiteDatabase db;

    public SocketListener(Context context) {
        this.context = context;
    }


    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
    }

    @Override
    public void onNext(Object users) {
        if (users instanceof Users) {

            Users usersList = (Users) users;
            Intent intent = new Intent(MapPresenter.GET_RELATIONS);
            intent.putExtra(MapPresenter.ABSTRACT_USER, usersList);
            DbHelper dbHelper = new DbHelper(context);
            db = dbHelper.getWritableDatabase();
            List<Data> userList = usersList.getData();

            Cursor lessonsCursor = cupboard().withDatabase(db).query(Data.class).getCursor();
            try {
                QueryResultIterable<Data> itr = cupboard().withCursor(lessonsCursor).iterate(Data.class);
                for (Data user : itr) {
                    cupboard().withDatabase(db).delete(user);
                }
            } finally {
                lessonsCursor.close();
            }



            cupboard().withDatabase(db).put(userList);

            context.sendBroadcast(intent);
        } else {
            Intent intent = new Intent(MapPresenter.GET_UPDATE_USER);
            intent.putExtra(MapPresenter.ABSTRACT_USER, (User) users);
            context.sendBroadcast(intent);


        }
    }
}
