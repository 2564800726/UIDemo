package cf.yuanbing.uidemo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        Toolbar toolbar = findViewById(R.id.tb_register);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final Button finishRegister = findViewById(R.id.btn_register_now);
        finishRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText  tel = findViewById(R.id.et_tel_register);
                EditText password = findViewById(R.id.et_password_register);
                @SuppressLint("WrongConstant")
                SharedPreferences sharedPreferences = getSharedPreferences("User", MODE_APPEND);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String telephoneCorrect = tel.getText().toString();
                String passwordCorrect = password.getText().toString();
                if (telephoneCorrect.length() != 11) {
                    Toast.makeText(RegisterActivity.this, "不是合法的手机号", Toast.LENGTH_SHORT).show();
                } else if ("".equals(passwordCorrect)) {
                    Toast.makeText(RegisterActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    editor.putString(telephoneCorrect, passwordCorrect);
                    editor.apply();
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
