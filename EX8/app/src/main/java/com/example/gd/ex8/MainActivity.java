package com.example.gd.ex8;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gd on 16/11/16.
 */
public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private myDB db;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        updateListView();

        //添加条目
        Button add_item = (Button)findViewById(R.id.add_item);
        if (add_item != null) {
            add_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, addActivity.class);
                    startActivity(intent);
                }
            });
        }

        listView = (ListView) findViewById(R.id.items_list);
        if (listView != null) {
            //添加监听器，处理短按
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    Adapter adapter = adapterView.getAdapter();
                    final Map<String, String> map = (Map<String, String>)adapter.getItem(position);

                    //弹出爱定义对话框
                    LayoutInflater factory = LayoutInflater.from(MainActivity.this);
                    final View view1 = factory.inflate(R.layout.dialoglayout, null);
                    final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setView(view1);
                    final AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                    WindowManager.LayoutParams params = alertDialog.getWindow().getAttributes();
                    params.width = 1050;
                    params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    alertDialog.getWindow().setAttributes(params);

                    //获取电话
                    //读取联系人列表
                    Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
                    String phoneNumer = "";
                    //遍历查询结果，找到手机号码
                    while (cursor.moveToNext()) {
                        //联系人id
                        String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                        //联系人的名字
                        String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        if (contactName.equals(map.get("name"))) {
                            Cursor c = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                            while (c.moveToNext()) {
                                phoneNumer += c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)) + " ";
                            }
                        }
                    }

                    //渲染对话框
                    final TextView name = (TextView)view1.findViewById(R.id.dia_name);
                    final EditText birthday = (EditText)view1.findViewById(R.id.dia_birthday);
                    final EditText gift = (EditText)view1.findViewById(R.id.dia_gift);
                    TextView phone = (TextView)view1.findViewById(R.id.phone);
                    if (name != null && birthday != null && gift != null && phone != null) {
                        name.setText(map.get("name"));
                        birthday.setText(map.get("birthday"));
                        gift.setText(map.get("gift"));
                        if (!"".equals(phoneNumer)) {
                            phone.setText(phoneNumer);
                        }
                    }

                    //放弃修改按钮处理
                    TextView cancel = (TextView)view1.findViewById(R.id.dia_cancel);
                    TextView save = (TextView)view1.findViewById(R.id.dia_save);
                    if (cancel != null) {
                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alertDialog.dismiss();
                            }
                        });
                    }

                    //保存修改按钮处理
                    if (save != null) {
                        save.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String edit_name = name.getText().toString();
                                String edit_birthday = birthday.getText().toString();
                                String edit_gift = gift.getText().toString();
                                db = new myDB(MainActivity.this);
                                db.update(edit_name, edit_birthday, edit_gift);
                                updateListView();
                                alertDialog.dismiss();
                            }
                        });
                    }

                }
            });

            //添加监听器，处理长按
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                    final Adapter adapter = adapterView.getAdapter();
                    final Map<String, String> map = (Map<String, String>)adapter.getItem(position);

                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                    alertDialog.setTitle("是否删除").setNegativeButton("否",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    db = new myDB(MainActivity.this);
                                    db.delete(map.get("name"));
                                    updateListView();
                                }
                            }).create().show();

                    return true;
                }
            });

        }

    }

    //显示所有条目
    public void updateListView() {
        db = new myDB(this);
        Cursor cursor = db.query();
        String[] names = new String[cursor.getCount()];
        String[] birthdays = new String[cursor.getCount()];
        String[] gifts = new String[cursor.getCount()];
        int count = 0;
        cursor.moveToNext();
        while (cursor.moveToNext()) {
            names[count] = cursor.getString(cursor.getColumnIndex("name"));
            birthdays[count] = cursor.getString(cursor.getColumnIndex("birth"));
            gifts[count] = cursor.getString(cursor.getColumnIndex("gift"));
            count++;
        }

        final List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < names.length-1; i++) {
            Map<String, Object> temp = new LinkedHashMap<>();
            temp.put("name", names[i]);
            temp.put("birthday", birthdays[i]);
            temp.put("gift", gifts[i]);
            data.add(temp);
        }

        final SimpleAdapter simpleAdapter = new SimpleAdapter(this, data, R.layout.item, new String[] {"name", "birthday", "gift"},
                new int[] {R.id.name, R.id.birthday, R.id.gift});

        listView = (ListView) findViewById(R.id.items_list);
        if (listView != null) {
            listView.setAdapter(simpleAdapter);
        }
    }
}












