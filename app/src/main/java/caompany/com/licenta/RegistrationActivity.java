package caompany.com.licenta;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.JsonElement;

import org.w3c.dom.Text;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import caompany.com.licenta.networking.ServerInterface;
import caompany.com.licenta.networking.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Header;



public class RegistrationActivity extends AppCompatActivity {
    private TextInputLayout usernameInput;
    private TextInputLayout passwordInput;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mContext = this;
        usernameInput = findViewById(R.id.username);
        passwordInput = findViewById(R.id.password);
        Button button = findViewById(R.id.btn);
        usernameInput.setHint("Email");
        passwordInput.setHint("Parola");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameInput.getEditText().getText().toString();
                String password = passwordInput.getEditText().getText().toString();
                Boolean verificare = true;
                if(!verificareEmail(username)){
                    usernameInput.setError("Adresa de e-mail invalida!");
                    verificare = false;
                }
                if(!verificareParola(password)){
                    passwordInput.setError("Parola invalida!");
                    verificare = false;
                }
                if(verificare){
                    usernameInput.setErrorEnabled(false);
                    passwordInput.setErrorEnabled(false);

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://whispering-dusk-58602.herokuapp.com")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    ServerInterface api = retrofit.create(ServerInterface.class);
                    User user = new User(username, password);
                    Call<JsonElement> call = api.registrate(user);
                    call.enqueue(new Callback<JsonElement>() {
                        @Override
                        public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                            Intent intent = new Intent(RegistrationActivity.this, ClassActivity.class);
                            okhttp3.Headers headerList = response.headers();
                            //Log.d("Raspuns", "onResponse: " );
                            Toast.makeText(mContext, "Token is:" + headerList.get("x-auth"), Toast.LENGTH_LONG).show();
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<JsonElement> call, Throwable t) {

                        }
                    });



                }
            }
        });
    }
    public boolean verificareParola(String parola){
        return parola.length() > 5;
    }
    public boolean verificareEmail(String email){
        final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher;
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
