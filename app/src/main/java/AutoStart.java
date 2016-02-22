import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import klep.wehere.services.HandlerSocketService;

/**
 * Created by klep.io on 22.02.16.
 */
public class AutoStart extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
//            Intent i = new Intent(context, SochActivity.class);
//            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(i);

//            context.startService(new Intent(context, HandlerSocketService.class));

        }
    }

}
