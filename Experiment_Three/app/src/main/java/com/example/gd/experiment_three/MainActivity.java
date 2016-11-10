package com.example.gd.experiment_three;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gd on 16/10/12.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        //使用ListView实现通讯录
        final List<Map<String, Object>> data = new ArrayList<>();

        String[] firsts = new String[] {"A", "E", "D", "E", "F", "J", "I", "M", "J", "P"};
        String[] names = new String[] {"Aaron", "Elvis", "David", "Edwin", "Frank",
                "Joshua", "Ivan", "Mark", "Josoph", "Phoebe"};
        for (int i = 0; i < names.length; i++) {
            Map<String, Object> temp = new LinkedHashMap<>();
            temp.put("first", firsts[i]);
            temp.put("name", names[i]);
            data.add(temp);
        }

        ListView listView = (ListView) findViewById(R.id.contacts_list);
        final SimpleAdapter simpleAdapter = new SimpleAdapter(this, data, R.layout.item, new String[] {"first", "name"},
                new int[] {R.id.first, R.id.name});
        listView.setAdapter(simpleAdapter);


        //添加监听器，处理短按
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, details.class);
                Adapter adapter = adapterView.getAdapter();
                Map<String, String> map = (Map<String, String>)adapter.getItem(position);
                Bundle bundle = new Bundle();
                //判断点击的是哪个人，将其信息封装成User对象传出去
                if (map.get("name").equals("Aaron")) {
                    bundle.putSerializable("user", new User("Aaron", "17715523654", "手机", "江苏苏州电信"));
                    intent.putExtras(bundle);
                }
                else if (map.get("name").equals("Elvis")) {
                    bundle.putSerializable("user", new User("Elvis", "18825653224", "手机", "广东揭阳移动"));
                    intent.putExtras(bundle);
                }
                else if (map.get("name").equals("David")) {
                    bundle.putSerializable("user", new User("David", "15052116654", "手机", "江苏无锡移动"));
                    intent.putExtras(bundle);
                }
                else if (map.get("name").equals("Edwin")) {
                    bundle.putSerializable("user", new User("Edwin", "18854211875", "手机", "山东青岛移动"));
                    intent.putExtras(bundle);
                }
                else if (map.get("name").equals("Frank")) {
                    bundle.putSerializable("user", new User("Frank", "13955188541", "手机", "安徽合肥移动"));
                    intent.putExtras(bundle);
                }
                else if (map.get("name").equals("Joshua")) {
                    bundle.putSerializable("user", new User("Joshua", "13621574410", "手机", "江苏苏州移动"));
                    intent.putExtras(bundle);
                }
                else if (map.get("name").equals("Ivan")) {
                    bundle.putSerializable("user", new User("Ivan", "15684122771", "手机", "山东烟台联通"));
                    intent.putExtras(bundle);
                }
                else if (map.get("name").equals("Mark")) {
                    bundle.putSerializable("user", new User("Mark", "17765213579", "手机", "广东珠海电信"));
                    intent.putExtras(bundle);
                }
                else if (map.get("name").equals("Joseph")) {
                    bundle.putSerializable("user", new User("Joseph", "13315466578", "手机", "河北石家庄电信"));
                    intent.putExtras(bundle);
                }
                else if (map.get("name").equals("Phoebe")) {
                    bundle.putSerializable("user", new User("Phoebe", "17895466428", "手机", "山东东营移动"));
                    intent.putExtras(bundle);
                }

                //startActivity(intent);
                startActivityForResult(intent, 0); //使用startActivityForResult可以保存通讯录的信息
            }
        });

        //处理长按
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long id) {
                Adapter adapter = adapterView.getAdapter();
                Map<String, String> map = (Map<String, String>)adapter.getItem(position);

                alertDialog.setTitle("删除联系人").setMessage("确定删除联系人" + map.get("name")+ "?").setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                data.remove(position);
                                simpleAdapter.notifyDataSetChanged();
                                //Toast.makeText(MainActivity.this, "对话框\"确定\"按钮被点击",Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                Toast.makeText(MainActivity.this, "对话框\"取消\"按钮被点击", Toast.LENGTH_SHORT).show();
                            }
                        }).create().show();

                return true; //返回false两个都会响应
            }
        });

    }

}





















