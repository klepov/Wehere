package klep.wehere.listPeople

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
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
        var cursor = cupboard()
                .withDatabase(db)
                .query(Data::class.java)
                .getCursor()
        user_child_count.text = "${cursor.count} членов семьи"

    }

}
