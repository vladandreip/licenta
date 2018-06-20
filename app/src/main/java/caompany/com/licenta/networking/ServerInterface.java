package caompany.com.licenta.networking;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServerInterface {
    @POST(Requests.registrate)
    Call<JsonElement> registrate(@Body User user);

    @POST(Requests.login)
    Call<JsonElement> login(@Body User user);

    @POST(Requests.cursuri)
    Call<JsonElement> cursuri(@Header("x-auth") String token,
                              @Body String curs);

    @GET(Requests.cursuri)
    Call<JsonElement> getCursuri(@Header("x-auth") String token);

    @DELETE(Requests.cursuriPath)
    Call<JsonElement> deleteCursuri(@Header("x-auth") String token,
                                    @Path("Id") String courseId);

}
