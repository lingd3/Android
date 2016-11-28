package com.example.gd.ex7;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by gd on 16/11/12.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText newPass = (EditText)findViewById(R.id.newPass);
        final EditText conPass = (EditText)findViewById(R.id.conPass);
        final EditText pass = (EditText)findViewById(R.id.password);
        Button ok = (Button)findViewById(R.id.ok_button);
        Button clear = (Button)findViewById(R.id.clear_button);

        //实例化SharedPreferences对象
        final SharedPreferences sp = getSharedPreferences("data", Activity.MODE_PRIVATE);
        //实例化SharedPreferences.Editor对象
        final SharedPreferences.Editor editor = sp.edit();

        //获取data里保存的password
        final String password = sp.getString("password", "");
        //首次进入
        if (password.equals("") && pass != null) {
            pass.setVisibility(View.GONE);
        }
        //非首次进入
        else {
            if (newPass != null && conPass != null) {
                newPass.setVisibility(View.GONE);
                conPass.setVisibility(View.GONE);
            }
        }

        //OK按钮的处理
        if (ok != null) {
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //首次进入
                    if (password.equals("")) {
                        if (newPass != null && conPass != null) {
                            //密码为空
                            if (newPass.getText().toString().equals("") || conPass.getText().toString().equals("")) {
                                Toast.makeText(MainActivity.this, "Password connot be empty", Toast.LENGTH_SHORT).show();
                            }
                            //密码不匹配
                            else if (!newPass.getText().toString().equals(conPass.getText().toString())) {
                                Toast.makeText(MainActivity.this, "Password Mismatch", Toast.LENGTH_SHORT).show();
                            }
                            //成功登入
                            else {
                                //用putString的方法保存数据
                                editor.putString("password",newPass.getText().toString());
                                //提交当前数据
                                editor.commit();

                                Intent intent = new Intent(MainActivity.this, FileEditorActivity.class);
                                startActivity(intent);
                            }
                        }
                    }
                    //非首次进入
                    else {
                        if (pass != null) {
                            //密码正确
                            if (password.equals(pass.getText().toString())) {
                                Intent intent = new Intent(MainActivity.this, FileEditorActivity.class);
                                startActivity(intent);
                            }
                            //密码错误
                            else {
                                Toast.makeText(MainActivity.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            });
        }

        //clear按钮的处理
        if (clear != null) {
            clear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (newPass != null && conPass != null && pass !=null) {
                        newPass.setText("");
                        conPass.setText("");
                        pass.setText("");
                    }
                }
            });
        }

    }
}
