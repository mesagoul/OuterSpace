package mesa.com.outerspacemanager.outerspacemanager.Permissions;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import mesa.com.outerspacemanager.outerspacemanager.activity.MainActivity;

/**
 * Created by Lucas on 25/03/2017.
 */

public class InternalStorage extends AppCompatActivity{
    private Activity activity;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 2;

    public InternalStorage(Activity activity) {
        this.activity = activity;
    }

    public void askForPermission(){
        if (ContextCompat.checkSelfPermission(this.activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this.activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this.activity,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this.activity,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            }
        }else{
            if(this.activity instanceof  MainActivity){
                ((MainActivity)this.activity).initAfterRequiredDataBase();
            }
        }
    }


    public final int getExternalStorageCode(){
        return this.MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE;
    }


}
