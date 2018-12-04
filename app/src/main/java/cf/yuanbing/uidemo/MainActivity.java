package cf.yuanbing.uidemo;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        Button buttonLogin = findViewById(R.id.btn_login);
        buttonLogin.setOnClickListener(this);
        Button buttonRegister = findViewById(R.id.btn_register);
        buttonRegister.setOnClickListener(this);
        Button buttonWX = findViewById(R.id.btn_wx);
        buttonWX.setOnClickListener(this);
        Button buttonQQ = findViewById(R.id.btn_qq);
        buttonQQ.setOnClickListener(this);
        Button buttonWB = findViewById(R.id.btn_wb);
        buttonWB.setOnClickListener(this);
        Button buttonWY = findViewById(R.id.btn_wy);
        buttonWY.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_register:
                Intent intent1 = new Intent(MainActivity.this, RegisterActivity.class);
                startActivityForResult(intent1, 1);
                break;
            case R.id.btn_wx:
                Toast.makeText(MainActivity.this, "微信登陆", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_qq:
                Toast.makeText(MainActivity.this, "QQ登陆", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_wb:
                Toast.makeText(MainActivity.this, "微博登陆", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_wy:
                Toast.makeText(MainActivity.this, "网易邮箱登陆", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
