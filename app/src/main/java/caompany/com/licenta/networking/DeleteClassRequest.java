package caompany.com.licenta.networking;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DeleteClassRequest {
    public void tryRequest(String header, String courseId){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://whispering-dusk-58602.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ServerInterface api = retrofit.create(ServerInterface.class);

        Call<JsonElement> call = api.deleteCursuri(header, courseId);
        try {
            call.enqueue(new Callback<JsonElement>() {
                @Override
                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                    if(response.code() == 200){
                        if(!call.isCanceled()){
                            onSuccess(response);
                        }
                    }else{
                        onFail(response.message());
                    }
                }

                @Override
                public void onFailure(Call<JsonElement> call, Throwable t) {
                    if(!call.isCanceled()){
                        onFail(t.toString());
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void onSuccess(Response<JsonElement> response){

    }
    public void onFail(String err){

    }
}