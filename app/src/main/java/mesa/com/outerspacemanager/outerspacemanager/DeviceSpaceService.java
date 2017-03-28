package mesa.com.outerspacemanager.outerspacemanager;

import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

/**
 * Created by Lucas on 27/03/2017.
 */

public class DeviceSpaceService extends JobService {
    @Override
    public boolean onStartJob(JobParameters job) {
        Log.d("APPDEBUG","Load my JOB");
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }
}
