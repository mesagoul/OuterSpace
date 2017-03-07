package mesa.com.outerspacemanager.outerspacemanager;

import mesa.com.outerspacemanager.outerspacemanager.buildings.Buildings;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Lucas on 06/03/2017.
 */

public interface Service {

        // USERS

        @POST("api/v1/auth/create")
        Call<User> createUserAccount(
                @Body User user);

        @POST("api/v1/auth/login")
        Call<User> connectUserAccount(
                @Body User user);

        @GET("api/v1/users/get")
        Call<User> getUser(@Header("x-access-token") String token);


        // BUILDINGS
        @GET("api/v1/buildings/list")
        Call<Buildings> getBuildings(
                @Header("x-access-token") String token
        );

        @GET("api/v1/buildings/create/{buildingId}")
        Call<String> upgradeBuilding(
                @Header("x-access-token") String token,
                @Path("buildingId") int buildingId
        );






}
