package klep.wehere.listPeople;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import klep.wehere.DbHelper;
import klep.wehere.R;
import klep.wehere.common.BaseFragment;
import klep.wehere.model.users.Data;
import klep.wehere.utils.Const;
import nl.qbusict.cupboard.QueryResultIterable;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by klep.io on 12.03.16.
 */
public class FragmentListPeople extends BaseFragment {
    private SQLiteDatabase db;
    List<Data> userList = new ArrayList<>();
    private Adapter adapter;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_list_users;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DbHelper dbHelper = new DbHelper(getActivity());
        db = dbHelper.getWritableDatabase();

        Cursor lessonsCursor = cupboard().withDatabase(db).query(Data.class).getCursor();
        try {
            QueryResultIterable<Data> itr = cupboard().withCursor(lessonsCursor).iterate(Data.class);
            for (Data user : itr) {
                userList.add(user);
            }
        } finally {
            lessonsCursor.close();
        }
        adapter = new Adapter(userList, getActivity());

        ListView lvMain = (ListView) view.findViewById(R.id.adapter);
        lvMain.setAdapter(adapter);
    }
}


class Adapter extends BaseAdapter {
    List<Data> users = new ArrayList<>();
    Context context;
    LayoutInflater inflater;

    public Adapter(List<Data> users, Context context) {
        this.users = users;
        this.context = context;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Data getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.adapter_list_users, parent, false);
        }

        CircleImageView imageUser = (CircleImageView) view.findViewById(R.id.imageUser);
        TextView nameUser = (TextView) view.findViewById(R.id.nameUser);

        Data data = users.get(position);

        nameUser.setText(data.getName());
        Picasso.with(context)
                .load(Const.IMAGE_URL + data.getLinkToImage())
                .into(imageUser);

        return view;
    }
}
