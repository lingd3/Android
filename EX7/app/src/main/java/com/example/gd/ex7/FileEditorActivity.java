package com.example.gd.ex7;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by gd on 16/11/12.
 */
public class FileEditorActivity extends AppCompatActivity {

    private EditText editText;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_editor);

        Button save = (Button)findViewById(R.id.save);
        Button load = (Button)findViewById(R.id.load);
        Button clear = (Button)findViewById(R.id.clear);
        editText = (EditText)findViewById(R.id.editText);

        //save按钮的处理
        if (save != null) {
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        if (editText != null) {
                            //保存到名为text的文件
                            FileOutputStream fileOutputStream = openFileOutput("text", MODE_PRIVATE);
                            String text = editText.getText().toString();
                            fileOutputStream.write(text.getBytes());
                            Toast.makeText(FileEditorActivity.this, "Save successfully", Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });
        }

        //load按钮的处理
        if (load != null) {
            load.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        if (editText != null) {
                            FileInputStream fileInputStream = openFileInput("text");
                            byte[] contents = new byte[fileInputStream.available()];
                            String text = new String(contents, 0, fileInputStream.read(contents));
                            editText.setText(text);
                            //设置光标在最后
                            editText.setSelection(text.length());
                            Toast.makeText(FileEditorActivity.this, "Load successfully", Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        Toast.makeText(FileEditorActivity.this, "Fail to load file", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            });
        }

        //clear按钮的处理
        if (clear != null) {
            clear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (editText != null) {
                        editText.setText("");
                    }
                }
            });
        }

    }
}


















