package caompany.com.licenta.networking;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServerInterface {
    @POST(Requests.registrate)
    Call<JsonElement> registrate(@Body User user);
}
