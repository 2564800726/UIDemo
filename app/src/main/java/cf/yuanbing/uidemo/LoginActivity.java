package cf.yuanbing.uidemo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        Toolbar toolbar = findViewById(R.id.tb_login);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button login = findViewById(R.id.btn_login_now);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText telephoneNumberInput = findViewById(R.id.et_tel);
                EditText passwordInput = findViewById(R.id.et_password);
                String password = passwordInput.getText().toString();
                String telephoneNumber = telephoneNumberInput.getText().toString();
                @SuppressLint("WrongConstant")
                SharedPreferences sharedPreferences = getSharedPreferences("User", MODE_ENABLE_WRITE_AHEAD_LOGGING);
                String passwordCorrect = sharedPreferences.getString(telephoneNumber, null);
                if (telephoneNumber.length() != 11) {
                    Toast.makeText(LoginActivity.this, "不是合法的手机号", Toast.LENGTH_SHORT).show();
                } else if ("".equals(password)) {
                    Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                } else if (password.equals(passwordCorrect)) {
                    Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MusicActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "账号或者密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
