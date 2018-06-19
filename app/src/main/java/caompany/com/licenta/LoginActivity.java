package caompany.com.licenta;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonElement;

import caompany.com.licenta.networking.LoginRequest;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    //TextInputLayout passWrapper;
    //TextInputLayout emailWrapper;
    EditText passEdit;
    EditText mailEdit;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button loginButton = findViewById(R.id.loginButton);
        mContext = this;
        //passWrapper = findViewById(R.id.passWrapper);
        //emailWrapper = findViewById(R.id.emailWrapper);
        //passWrapper.setHint("Email");
        //emailWrapper.setHint("Parola");
        passEdit = findViewById(R.id.pass);
        mailEdit = findViewById(R.id.email);

        //RadioButton profesorButton = findViewById(R.id.radio_profesor);
        //RadioButton studentButton = findViewById(R.id.radio_student);
        TextView register = findViewById(R.id.register);
        RadioGroup radioGroup = findViewById(R.id.radio_layout);
        radioGroup.getCheckedRadioButtonId();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mailEdit.getText().toString();
                String pass =  passEdit.getText().toString();
                Log.d("REQQQ", "onClick: " + email + pass);
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
                        Log.d("ATTEMPT", "onFail: ");
                        AlertDialog.Builder builder =  new AlertDialog.Builder(mContext);
                        builder.setTitle("Atentie");
                        builder.setMessage("Datele introduse sunt incorecte");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builder.create();
                        builder.show();
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
