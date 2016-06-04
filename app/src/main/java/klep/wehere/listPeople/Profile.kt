package klep.wehere.listPeople

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.mikepenz.fastadapter.adapters.FastItemAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import com.mikepenz.fastadapter.utils.ViewHolderFactory
import com.orhanobut.hawk.Hawk
import klep.wehere.DbHelper
import klep.wehere.R
import klep.wehere.common.BaseFragment
import klep.wehere.model.users.Data
import klep.wehere.utils.Const
import kotlinx.android.synthetic.main.fragment_profile.*
import nl.qbusict.cupboard.CupboardFactory.cupboard

/**
 * Created by klep on 04.06.16 with love.
 */

class ProfileFragment : BaseFragment() {
    var db: SQLiteDatabase? = null

    private var adapter: FastItemAdapter<UserItem>? = null
    private var recycler: RecyclerView? = null


    override fun getLayoutRes(): Int {
        return R.layout.fragment_profile
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = Hawk.get<Data>(Const.USER)
        name_user.text = data.name
        Glide.with(this).load(Const.IMAGE_URL + data.linkToImage).into(image_user)
        val dbHelper = DbHelper(context)
        db = dbHelper.getWritableDatabase()
        val cursor = cupboard()
                .withDatabase(db)
                .query(Data::class.java)
                .getCursor()
        user_child_count.text = "${cursor.count} членов семьи"
        val recycler = recycler_user
        val layoutManager = GridLayoutManager(context, 3)
        recycler.layoutManager = layoutManager

        adapter = FastItemAdapter<UserItem>().apply {
            withOnClickListener({ view, iAdapter, item, i -> false })
            recycler.adapter = this
        }
        val allah = cupboard()
                .withCursor(cursor)
                .iterate(Data::class.java)
        try {
            adapter?.set(allah.map { UserItem(it) })
        } finally {
            cursor.close()
        }
    }
}

class UserItem(val data: Data) : AbstractItem<UserItem, UserHolder>() {
    companion object {
        @JvmStatic private val itemFactory = UserItemFactory()
    }

    override fun getLayoutRes() = R.layout.card_user_profile

    override fun getType() = R.id.user_list_item

    override fun getFactory() = itemFactory

    override fun bindView(holder: UserHolder) {
        super.bindView(holder)
        holder.name.text = data.name
        Glide.with(holder.image.context)
                .load(Const.IMAGE_URL + data.linkToImage)
                .into(holder.image)
    }
}

class UserItemFactory : ViewHolderFactory<UserHolder> {
    override fun create(v: View) = UserHolder(v)
}

class UserHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val image = itemView.findViewById(R.id.family_user_image) as ImageView
    val name = itemView.findViewById(R.id.family_user) as TextView
}
