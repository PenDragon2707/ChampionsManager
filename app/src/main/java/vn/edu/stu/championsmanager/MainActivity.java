package vn.edu.stu.championsmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button loginButton;
    private CheckBox cbRemember;

    String userStateFile = "UserState";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addControls();
//        setUser();
        addEvents();
    }

    private void addControls() {
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);
        cbRemember = findViewById(R.id.cb_remember);

    }

    private void  addEvents() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doLogin();
            }
        });
    }

    private void doLogin() {

        String user = username.getText().toString().trim();
        String pass = password.getText().toString().trim();

//        if (user.equalsIgnoreCase("Admin") && password.equals("1"))
        if (isValidCredentials(user, pass)){
            goChampionRoles();
            if (cbRemember.isChecked()) {
                SharedPreferences preferences = getSharedPreferences(userStateFile, MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("username", user);
                editor.putString("password", pass);
                editor.putString("remember", "ok");
                editor.apply();
            } else {
                SharedPreferences preferences = getSharedPreferences(userStateFile, MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("username");
                editor.remove("password");
                editor.remove("remember");
                editor.apply();
            }
        }
        else {
            showloginfalseMessage();
            SharedPreferences preferences = getSharedPreferences(userStateFile, MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove("username");
            editor.remove("password");
            editor.remove("remember");
            editor.apply();
        }


    }

//    private void setUser() {
//        username.setText("Admin");
//        password.setText("1");
//    }


    private boolean isValidCredentials(String user, String pass) {
        return "Admin".equalsIgnoreCase(user) && "11111".equals(pass);
    }

    private void goChampionRoles() {
        Intent intent = new Intent(MainActivity.this, About.class);
        startActivity(intent);
    }

    private void showloginfalseMessage() {
        String message = getString(R.string.loginfalse);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = getSharedPreferences(userStateFile, MODE_PRIVATE);
        String user = preferences.getString("username", "");
        String pass = preferences.getString("password", "");
        String ck = preferences.getString("remember", "");
        username.setText(user);
        password.setText(pass);
        if (ck.equals("ok")){cbRemember.setChecked(true);}
    }

}