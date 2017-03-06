package mesa.com.outerspacemanager.outerspacemanager;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Lucas on 06/03/2017.
 */

public interface Service {
        @POST("/api/v1/auth/create")
        Call<List<User>> createUserAccount(
                @Body User user);

}
