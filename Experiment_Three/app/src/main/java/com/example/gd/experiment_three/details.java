package com.example.gd.experiment_three;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gd on 16/10/15.
 */
public class details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);

        //使用ListView实现更多资料的显示
        List<Map<String, Object>> data = new ArrayList<>();

        String[] mores = new String[] {"编辑联系人", "分享联系人", "加入黑名单", "删除联系人"};
        for (int i = 0; i < mores.length; i++) {
            Map<String, Object> temp = new LinkedHashMap<>();
            temp.put("more", mores[i]);
            temp.put("none", "");
            data.add(temp);
        }

        ListView listView = (ListView)findViewById(R.id.info);
        if (listView != null) {
            SimpleAdapter simpleAdapter = new SimpleAdapter(this, data, R.layout.more, new String[] {"more", "none"},
                    new int[] {R.id.more, R.id.none});
            listView.setAdapter(simpleAdapter);
        }

        //返回按钮的实现
        ImageView back = (ImageView) findViewById(R.id.back);
        if (back != null) {
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(details.this, MainActivity.class);
                    setResult(RESULT_OK, intent);
//                    startActivity(intent);
                    finish();
                }
            });
        }

        //星星的实现
        ImageView star = (ImageView) findViewById(R.id.star);
        if (star != null) {
            star.setOnClickListener(new View.OnClickListener() {
                int count = 0;
                int[] images = new int[] {R.mipmap.empty_star, R.mipmap.full_star};
                @Override
                public void onClick(View view) {
                    if (count == 0) count = 1;
                    else count = 0;
                    ((ImageView) findViewById(R.id.star)).setImageResource(images[count]);
                }
            });
        }

        //更新信息，不同人显示不同信息，接收MainActivity传的User，获取相应信息
        Intent intent = getIntent();
        User u = (User)intent.getSerializableExtra("user");
        TextView name = (TextView)findViewById(R.id.name);
        if (name != null) name.setText(u.getUsername());
        TextView phone = (TextView)findViewById(R.id.phone);
        if (phone != null) phone.setText(u.getPhone());
        TextView place = (TextView)findViewById(R.id.place);
        if (place != null) place.setText(u.getPlace());
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.background);
        if (linearLayout != null) {
            if (u.getUsername().equals("Aaron")) linearLayout.setBackgroundColor(getResources().getColor(R.color.Aaron));
            if (u.getUsername().equals("Elvis")) linearLayout.setBackgroundColor(getResources().getColor(R.color.Elvis));
            if (u.getUsername().equals("David")) linearLayout.setBackgroundColor(getResources().getColor(R.color.David));
            if (u.getUsername().equals("Edwin")) linearLayout.setBackgroundColor(getResources().getColor(R.color.Edwin));
            if (u.getUsername().equals("Frank")) linearLayout.setBackgroundColor(getResources().getColor(R.color.Frank));
            if (u.getUsername().equals("Joshua")) linearLayout.setBackgroundColor(getResources().getColor(R.color.Joshua));
            if (u.getUsername().equals("Ivan")) linearLayout.setBackgroundColor(getResources().getColor(R.color.Ivan));
            if (u.getUsername().equals("Mark")) linearLayout.setBackgroundColor(getResources().getColor(R.color.Mark));
            if (u.getUsername().equals("Joseph")) linearLayout.setBackgroundColor(getResources().getColor(R.color.Joseph));
            if (u.getUsername().equals("Phoebe")) linearLayout.setBackgroundColor(getResources().getColor(R.color.Phoebe));

        }
    }

}
















