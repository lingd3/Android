package com.example.gd.experimenttwo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Created by gd on 16/9/28.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        final TextInputLayout name_input = (TextInputLayout)findViewById(R.id.username_text);
        final TextInputLayout pass_input = (TextInputLayout)findViewById(R.id.password_text);
        final EditText usernameText = (EditText)findViewById(R.id.username);
        final EditText passwordText = (EditText)findViewById(R.id.password);

        final Button btn = (Button)findViewById(R.id.button1);
        if (btn != null) {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = usernameText.getText().toString();
                    String pass = passwordText.getText().toString();
                    if (name.equals("")) {
                        name_input.setErrorEnabled(true);
                        name_input.setError("用户名不能为空");
                    }
                    else if (pass.equals("")) {
                        pass_input.setErrorEnabled(true);
                        pass_input.setError("密码不能为空");
                    }
                    else {
                        name_input.setErrorEnabled(false);
                        pass_input.setErrorEnabled(false);
                        if (name.equals("Android") && pass.equals("123456")) {
                            Snackbar.make(btn, "登录成功", Snackbar.LENGTH_SHORT)
                                    .setAction("按钮", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Toast.makeText(MainActivity.this, "Snackbar的按钮被点击了", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                                    .setDuration(5000)
                                    .show();
                        }
                        else {
                            Snackbar.make(btn, "登录失败", Snackbar.LENGTH_SHORT)
                                    .setAction("按钮", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Toast.makeText(MainActivity.this, "Snackbar的按钮被点击了", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                                    .setDuration(5000)
                                    .show();
                        }
                    }
                }
            });
        }

        //handle radiobutton
        final RadioGroup buttongroup = (RadioGroup)findViewById(R.id.id0);
        if (buttongroup != null) {
            buttongroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(RadioGroup buttongroup, int checkedId) {
                    if (checkedId == R.id.id1) {
                        Snackbar.make(btn, "学生身份被选中", Snackbar.LENGTH_SHORT)
                                .setAction("按钮", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Toast.makeText(MainActivity.this, "Snackbar的按钮被点击了", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                                .setDuration(5000)
                                .show();
                    }
                    else if (checkedId == R.id.id2) {
                        Snackbar.make(btn, "教师身份被选中", Snackbar.LENGTH_SHORT)
                                .setAction("按钮", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Toast.makeText(MainActivity.this, "Snackbar的按钮被点击了", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                                .setDuration(5000)
                                .show();
                    }
                    else if (checkedId == R.id.id3) {
                        Snackbar.make(btn, "社团身份被选中", Snackbar.LENGTH_SHORT)
                                .setAction("按钮", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Toast.makeText(MainActivity.this, "Snackbar的按钮被点击了", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                                .setDuration(5000)
                                .show();
                    }
                    else if (checkedId == R.id.id4) {
                        Snackbar.make(btn, "管理者身份被选中", Snackbar.LENGTH_SHORT)
                                .setAction("按钮", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Toast.makeText(MainActivity.this, "Snackbar的按钮被点击了", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                                .setDuration(5000)
                                .show();
                    }
                }

            });
        }

        //the register button
        final Button btn1 = (Button)findViewById(R.id.button2);
        if (btn1 != null) {
            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = buttongroup.getCheckedRadioButtonId();
                    if (id == R.id.id1) {
                        Snackbar.make(btn, "学生身份注册功能尚未开启", Snackbar.LENGTH_SHORT)
                                .setAction("按钮", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Toast.makeText(MainActivity.this, "Snackbar的按钮被点击了", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                                .setDuration(5000)
                                .show();
                    }
                    else if (id == R.id.id2) {
                        Snackbar.make(btn, "教师身份注册功能尚未开启", Snackbar.LENGTH_SHORT)
                                .setAction("按钮", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Toast.makeText(MainActivity.this, "Snackbar的按钮被点击了", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                                .setDuration(5000)
                                .show();
                    }
                    else if (id == R.id.id3) {
                        Snackbar.make(btn, "社团身份注册功能尚未开启", Snackbar.LENGTH_SHORT)
                                .setAction("按钮", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Toast.makeText(MainActivity.this, "Snackbar的按钮被点击了", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                                .setDuration(5000)
                                .show();
                    }
                    else if (id == R.id.id4) {
                        Snackbar.make(btn, "管理者身份注册功能尚未开启", Snackbar.LENGTH_SHORT)
                                .setAction("按钮", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Toast.makeText(MainActivity.this, "Snackbar的按钮被点击了", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                                .setDuration(5000)
                                .show();
                    }
                }
            });
        }

    }
}






















