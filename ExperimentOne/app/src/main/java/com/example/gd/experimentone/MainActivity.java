package com.example.gd.experimentone;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Created by gd on 16/9/14.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("提示").setPositiveButton("确认",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "对话框\"确定\"按钮被点击",Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "对话框\"取消\"按钮被点击", Toast.LENGTH_SHORT).show();
                    }
                }).create();

        Button btn = (Button)findViewById(R.id.button1);
        if (btn != null) {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText username = (EditText)findViewById(R.id.editText1);
                    EditText password = (EditText)findViewById(R.id.editText2);
                    if (username != null && password != null) {
                        if (username.getText().length() == 0) {
                            Toast.makeText(MainActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                        }
                        else if (password.getText().length() == 0) {
                            Toast.makeText(MainActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            if (username.getText().toString().equals("Android") && password.getText().toString().equals("123456")) {
                                alertDialog.setMessage("登录成功");
                                alertDialog.show();
                            }
                            else {
                                alertDialog.setMessage("登录失败");
                                alertDialog.show();
                            }
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
                        Toast.makeText(MainActivity.this, "学生身份被选中", Toast.LENGTH_SHORT).show();
                    }
                    else if (checkedId == R.id.id2) {
                        Toast.makeText(MainActivity.this, "教师身份被选中", Toast.LENGTH_SHORT).show();
                    }
                    else if (checkedId == R.id.id3) {
                        Toast.makeText(MainActivity.this, "社团身份被选中", Toast.LENGTH_SHORT).show();
                    }
                    else if (checkedId == R.id.id4) {
                        Toast.makeText(MainActivity.this, "管理者身份被选中", Toast.LENGTH_SHORT).show();
                    }
                }

            });
        }

        //the register button
        Button btn1 = (Button)findViewById(R.id.button2);
        if (btn1 != null) {
            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = buttongroup.getCheckedRadioButtonId();
                    if (id == R.id.id1) {
                        Toast.makeText(MainActivity.this, "学生身份注册功能尚未开启", Toast.LENGTH_SHORT).show();
                    }
                    else if (id == R.id.id2) {
                        Toast.makeText(MainActivity.this, "教师身份注册功能尚未开启", Toast.LENGTH_SHORT).show();
                    }
                    else if (id == R.id.id3) {
                        Toast.makeText(MainActivity.this, "社团身份注册功能尚未开启", Toast.LENGTH_SHORT).show();
                    }
                    else if (id == R.id.id4) {
                        Toast.makeText(MainActivity.this, "管理者身份注册功能尚未开启", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


    }

}

























