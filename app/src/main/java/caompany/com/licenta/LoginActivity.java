package caompany.com.licenta;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.JsonElement;

import caompany.com.licenta.networking.LoginRequest;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    TextInputLayout passWrapper;
    TextInputLayout emailWrapper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button loginButton = findViewById(R.id.loginButton);
        passWrapper = findViewById(R.id.passWrapper);
        emailWrapper = findViewById(R.id.emailWrapper);
        passWrapper.setHint("Email");
        emailWrapper.setHint("Parola");

        //RadioButton profesorButton = findViewById(R.id.radio_profesor);
        //RadioButton studentButton = findViewById(R.id.radio_student);
        TextView register = findViewById(R.id.register);
        RadioGroup radioGroup = findViewById(R.id.radio_layout);
        radioGroup.getCheckedRadioButtonId();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailWrapper.getEditText().getText().toString();
                String pass = passWrapper.getEditText().getText().toString();
                LoginRequest loginRequest = new LoginRequest(){
                    @Override
                    public void onSuccess(Response<JsonElement> response) {
                        String header = response.headers().get("x-auth");
                        //Bundle bundle = new Bundle();
                        //bundle.putString("header", header);
                        Intent intent = new Intent(LoginActivity.this, ClassActivity.class);
                        intent.putExtra("header", header);
                        startActivity(intent);
                    }

                    @Override
                    public void onFail(String err) {
                        passWrapper.setError("Datele introduse sunt incorecte");
                    }
                };
                loginRequest.tryRequest(email,pass);




            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });


    }

}
