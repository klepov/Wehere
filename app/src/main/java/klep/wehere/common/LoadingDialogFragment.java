package klep.wehere.common;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import klep.wehere.R;

/**
 * Created by klep.io on 14.02.16.
 */
public class LoadingDialogFragment extends DialogFragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v;

        v = inflater.inflate(R.layout.loading_dialog_fragment,
                container,
                false);
        return v;

    }

}
