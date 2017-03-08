package mesa.com.outerspacemanager.outerspacemanager.network;

import mesa.com.outerspacemanager.outerspacemanager.model.Building;
import mesa.com.outerspacemanager.outerspacemanager.model.Buildings;
import mesa.com.outerspacemanager.outerspacemanager.model.Searche;
import mesa.com.outerspacemanager.outerspacemanager.model.Searches;
import mesa.com.outerspacemanager.outerspacemanager.model.User;
import mesa.com.outerspacemanager.outerspacemanager.model.Users;
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

        @GET("api/v1/users/{from}/{limit}")
        Call<Users> getUsers(
                @Header("x-access-token") String token,
                @Path("from") int from,
                @Path("limit") int limit
        );


        // BUILDINGS
        @GET("api/v1/buildings/list")
        Call<Buildings> getBuildings(
                @Header("x-access-token") String token
        );

        @POST("api/v1/buildings/create/{buildingId}")
        Call<Building> upgradeBuilding(
                @Header("x-access-token") String token,
                @Path("buildingId") int buildingId
        );

        // SEARCHES
        @GET("api/v1/searches/list")
        Call<Searches> getSearches(
                @Header("x-access-token") String token
        );

        @POST("/api/v1/searches/create/{searchId}")
        Call<Searche> upgradeSearche(
                @Header("x-access-token") String token,
                @Path("searchId") int buildingId
        );






}
