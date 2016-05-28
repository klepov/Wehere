package klep.wehere.listPeople;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import de.greenrobot.event.EventBus;
import de.hdodenhof.circleimageview.CircleImageView;
import klep.wehere.DbHelper;
import klep.wehere.R;
import klep.wehere.common.BaseFragment;
import klep.wehere.event.UserClick;
import klep.wehere.model.users.Data;
import klep.wehere.utils.Const;
import nl.qbusict.cupboard.QueryResultIterable;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by klep.io on 12.03.16.
 */
public class FragmentListPeople extends BaseFragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    private SQLiteDatabase db;
    List<Data> userList = new ArrayList<>();
    private Adapter adapter;


    @Bind(R.id.toolbar_list)
    Toolbar toolbar;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_list_users;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setTitle("назад");

        Drawable exitNews = new IconicsDrawable(getActivity())
                .icon(CommunityMaterial.Icon.cmd_close)
                .color(ContextCompat.getColor(getActivity(), R.color.md_white_1000))
                .sizeDp(16);

        toolbar.setNavigationIcon(exitNews);
        toolbar.setNavigationOnClickListener(this);


        getActivity().getActionBar();
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
        lvMain.setOnItemClickListener(this);

    }

    @Override
    public void onClick(View v) {
        getActivity().finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        EventBus.getDefault().postSticky(new UserClick(userList.get(position)));
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
