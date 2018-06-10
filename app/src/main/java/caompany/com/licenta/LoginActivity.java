package caompany.com.licenta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button loginButton = findViewById(R.id.loginButton);
        //RadioButton profesorButton = findViewById(R.id.radio_profesor);
        //RadioButton studentButton = findViewById(R.id.radio_student);
        TextView register = findViewById(R.id.register);
        RadioGroup radioGroup = findViewById(R.id.radio_layout);
        radioGroup.getCheckedRadioButtonId();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ClassActivity.class);
                startActivity(intent);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, NfcActivity.class);
                startActivity(intent);
            }
        });

    }

}
