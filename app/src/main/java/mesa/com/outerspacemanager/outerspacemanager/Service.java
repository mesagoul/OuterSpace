package mesa.com.outerspacemanager.outerspacemanager;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Lucas on 06/03/2017.
 */

public interface Service {

        // USERS

        @POST("api/v1/auth/create")
        Call<User> createUserAccount(
                @Body User user);

        @GET("api/v1/users/get")
        Call<User> getUser(@Header("x-access-token") String token);


        // BUILDINGS
        @GET("api/v1/buildings")
        Call<Buildings> getBuildings(@Header("x-access-token") String token);


}
