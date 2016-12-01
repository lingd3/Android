package com.example.gd.ex8;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;

/**
 * Created by gd on 16/11/16.
 */
public class addActivity extends AppCompatActivity {

    private EditText name;
    private EditText birthday;
    private EditText gift;
    private Button add;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_info);

        final myDB db = new myDB(this);
        name = (EditText)findViewById(R.id.edit_name);
        birthday = (EditText)findViewById(R.id.edit_birthday);
        gift = (EditText)findViewById(R.id.edit_gift);
        add = (Button)findViewById(R.id.edit_add_item);

        if (add != null) {
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (name != null && birthday != null && gift != null) {
                        String edit_name = name.getText().toString();
                        String edit_birthday = birthday.getText().toString();
                        String edit_gift = gift.getText().toString();
                        //名字为空
                        if (edit_name.equals("")) {
                            Toast.makeText(addActivity.this, "名字为空，请完善", Toast.LENGTH_SHORT).show();
                        }
                        //判断名字重复
                        else if (db.query(edit_name)) {
                            Toast.makeText(addActivity.this, "名字重复啦，请检查", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            db.insert(edit_name, edit_birthday, edit_gift);
                            Intent intent = new Intent(addActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }
                }
            });
        }


    }
}
