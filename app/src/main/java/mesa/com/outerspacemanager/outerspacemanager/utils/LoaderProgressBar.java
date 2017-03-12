package mesa.com.outerspacemanager.outerspacemanager.utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Lucas on 07/03/2017.
 */

public class LoaderProgressBar {
    private ProgressDialog progress;

    public LoaderProgressBar(Context context){
        progress = new ProgressDialog(context);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
    }
    public void show(){
        this.progress.show();
    }
    public void dismiss(){
        this.progress.dismiss();
    }
}
