package caompany.com.licenta;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.w3c.dom.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {
    private TextInputLayout usernameInput;
    private TextInputLayout passwordInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
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
                if(!verificareEmail(username)){
                    usernameInput.setError("Adresa de e-mail invalida!");
                }
                if(!verificareParola(password)){
                    passwordInput.setError("Parola invalida!");
                }else{
                    usernameInput.setErrorEnabled(false);
                    passwordInput.setErrorEnabled(false);
                    Intent intent = new Intent(RegistrationActivity.this, NfcActivity.class);
                    startActivity(intent);

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
