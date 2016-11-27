package com.example.gd.ex4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gd on 16/10/19.
 */
public class StaticActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.static_layout);

        //使用ListView添加水果
        final List<Map<String, Object>> data = new ArrayList<>();

        int[] images = new int[] {R.mipmap.apple, R.mipmap.banana, R.mipmap.cherry, R.mipmap.coco, R.mipmap.kiwi,
                R.mipmap.orange, R.mipmap.pear, R.mipmap.strawberry, R.mipmap.watermelon};
        String[] names = new String[] {"Apple", "Banana", "Cherry", "Coco", "Kiwi",
                "Orange", "Pear", "Strawberry", "Watermelon"};
        for (int i = 0; i < names.length; i++) {
            Map<String, Object> temp = new LinkedHashMap<>();
            temp.put("image", images[i]);
            temp.put("name", names[i]);
            data.add(temp);
        }

        ListView listView = (ListView) findViewById(R.id.fruit_list);
        if (listView != null) {
            final SimpleAdapter simpleAdapter = new SimpleAdapter(this, data, R.layout.item, new String[] {"image", "name"},
                    new int[] {R.id.image, R.id.name});
            listView.setAdapter(simpleAdapter);
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //发送广播
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent("com.example.gd.ex4.staticreceiver");
                Adapter adapter = adapterView.getAdapter();
                Map<String, Integer> map = (Map<String, Integer>)adapter.getItem(position);
                Bundle bundle = new Bundle();
//                Log.v("f",map.get("name"));

                Fruit fruit = new Fruit();
                fruit.setFruits();
                for (Map.Entry<String, Integer>entry: fruit.getFruits().entrySet()) {
                    if (entry.getKey().equals(map.get("name"))) {
                        bundle.putSerializable("fruit", new Fruit(entry.getKey(), entry.getValue()));
                    }
                }
                intent.putExtras(bundle);
                sendBroadcast(intent);
            }
        });



    }

}
