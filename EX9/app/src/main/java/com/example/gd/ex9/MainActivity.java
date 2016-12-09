package com.example.gd.ex9;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gd on 16/11/23.
 */
public class MainActivity extends AppCompatActivity {

    private EditText city;
    private WeatherAdapter adapter;
    private RecyclerView recyclerView;
    //WebService地址
    private static final String url = "http://ws.webxml.com.cn/WebServices/WeatherWS.asmx/getWeather";
    private static final String theUserID= "be06c044d21542788b6adc72a85ae746";
    private static final int UPDATE_CONTENT = 0;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //RecyclerView的设置
        recyclerView = (RecyclerView)findViewById(R.id.weatherList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        if (recyclerView != null && layoutManager != null) {
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(layoutManager);
        }

        //查询按钮的处理
        Button bt = (Button)findViewById(R.id.search);
        if (bt != null) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //网络可用
                    if (hasNet()) {
                        sendRequestWithHttpURLConnection();
                    }
                    //网络不可用
                    else {
                        Toast.makeText(MainActivity.this, "当前没有可用的网络！", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendRequestWithHttpURLConnection() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //http请求操作
                HttpURLConnection connection = null;
                try {
                    //打开连接并设置访问方法以及时间设置
                    connection = (HttpURLConnection)(new URL(url.toString()).openConnection());
                    connection.setRequestMethod("POST");
                    connection.setReadTimeout(8000);
                    connection.setConnectTimeout(8000);

                    DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                    city = (EditText)findViewById(R.id.input_city);
                    if (city != null) {
                        String request = city.getText().toString();
                        request = URLEncoder.encode(request, "utf-8");
                        out.writeBytes("theCityCode=" + request + "&theUserID="+theUserID);
                    }

                    //获取xml转化为字符串
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    //Message消息传递
                    Message message = new Message();
                    message.what = UPDATE_CONTENT;
                    message.obj = parseXMLWithPull(response.toString());
                    handler.sendMessage(message);

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    //判断是否有可用网络
    private boolean hasNet() {
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) return true;
        return false;
    }

    //XmlPullParser操作
    private List<String> parseXMLWithPull(String response) throws Exception {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();//获取实例
        XmlPullParser parser = factory.newPullParser();
        parser.setInput(new StringReader(response));
        int eventType = parser.getEventType();
        List<String> list = new ArrayList();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if ("string".equals(parser.getName())) {
                        String str = parser.nextText();
                        list.add(str);
                    }
                    break;
                case XmlPullParser.END_TAG:
                    break;
                default:
                    break;
            }
            eventType = parser.next();
        }
        return list;
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case UPDATE_CONTENT:
                    //显示下方内容
                    LinearLayout detail = (LinearLayout)findViewById(R.id.detail);
                    if (detail != null) {
                        detail.setVisibility(View.VISIBLE);
                    }

                    //由子线程得到的字符串，对其进行处理
                    List<String> list = (ArrayList<String>)message.obj;

                    //没有错误时更新UI
                    if (!hasError(list)) {
                        //上方UI更新
                        TextView city = (TextView)findViewById(R.id.city);
                        TextView time = (TextView)findViewById(R.id.time);
                        TextView temperature = (TextView)findViewById(R.id.temperature);
                        TextView temperature_range = (TextView)findViewById(R.id.temperature_range);
                        TextView humidity = (TextView)findViewById(R.id.humidity);
                        TextView air_quality = (TextView)findViewById(R.id.air_quality);
                        TextView wind = (TextView)findViewById(R.id.wind);

                        if (list.size()>8 && city != null && time != null && temperature != null && temperature_range != null &&
                                humidity != null && air_quality != null && wind != null) {
                            city.setText(list.get(1));
                            time.setText(list.get(3).substring(list.get(3).length()-8, list.get(3).length())+ "更新");
                            String temp = list.get(4);
                            temperature.setText(temp.substring(temp.indexOf("：", temp.indexOf("：")+1)+1,temp.indexOf("；")));
                            temperature_range.setText(list.get(8));
                            humidity.setText(temp.substring(temp.lastIndexOf("；")+1));
                            air_quality.setText(list.get(5).substring(list.get(5).indexOf("。")+1, list.get(5).length()-1));
                            wind.setText(temp.substring(temp.indexOf("：", 12)+1, temp.lastIndexOf("；")));
                        }

                        //中间ListView更新
                        String[] properties =  {"紫外线指数","感冒指数","穿衣指数","洗车指数","运动指数","空气污染指数"};
                        String[] values =  {"暂无数据","暂无数据","暂无数据","暂无数据","暂无数据","暂无数据"};
                        if (list.size()>6 && list.get(6) != null) {
                            String temp = list.get(6);
                            int first = temp.indexOf("。");
                            int second = temp.indexOf("。", first+1);
                            int third = temp.indexOf("。", second+1);
                            int fourth = temp.indexOf("。", third+1);
                            int fifth = temp.indexOf("。", fourth+1);
                            values[0] = temp.substring(temp.indexOf("：")+1, first+1);
                            values[1] = temp.substring(temp.indexOf("：", first)+1, second+1);
                            values[2] = temp.substring(temp.indexOf("：", second)+1, third+1);
                            values[3] = temp.substring(temp.indexOf("：", third)+1, fourth+1);
                            values[4] = temp.substring(temp.indexOf("：", fourth)+1, fifth+1);
                            values[5] = temp.substring(temp.indexOf("：", fifth)+1);
                        }

                        final List<Map<String, Object>> data = new ArrayList<>();
                        for (int i = 0; i < properties.length; i++) {
                            Map<String, Object> temp = new LinkedHashMap<>();
                            temp.put("property", properties[i]);
                            temp.put("value", values[i]);
                            data.add(temp);
                        }
                        final SimpleAdapter simpleAdapter = new SimpleAdapter(MainActivity.this, data, R.layout.item, new String[] {"property", "value"},
                                new int[] {R.id.property, R.id.value});
                        ListView listView = (ListView) findViewById(R.id.list);
                        if (listView != null) {
                            listView.setAdapter(simpleAdapter);
                        }

                        //最下方RecyclerView的更新
                        ArrayList<Weather> weather_list = new ArrayList();
                        if (list.size() > 32) {
                            for (int i = 0; i < 5; i++) {
                                int space = list.get(7 + i * 5).indexOf(" ");
                                String date = list.get(7 + i * 5).substring(0, space);
                                String weather = list.get(7 + i * 5).substring(space+1);
                                String temperature_ = list.get(8 + i * 5);
                                weather_list.add(new Weather(weather, date, temperature_));
                            }
                        }

                        adapter = new WeatherAdapter(MainActivity.this, weather_list);
                        recyclerView.setAdapter(adapter);

                    }
                    break;
                default:
                    break;
            }
        }
    };

    private boolean hasError(List<String> list) {
        for (String s: list) {
            //城市名不正确
            if (s.indexOf("查询结果为空") != -1) {
                Toast.makeText(MainActivity.this, "当前城市不存在，请重新输入", Toast.LENGTH_SHORT).show();
                return true;
            }
            //快速点击按钮(二次查询<600ms)
            if (s.indexOf("免费用户不能使用高速") != -1) {
                Toast.makeText(MainActivity.this, "您的点击速度过快，二次查询间隔<600ms", Toast.LENGTH_SHORT).show();
                return true;
            }
            //查询打到上限50次
            if (s.indexOf("免费用户24小时内访问超过") != -1) {
                Toast.makeText(MainActivity.this, "免费用户24小时内访问超过规定数量50次", Toast.LENGTH_SHORT).show();
                return true;
            }
            if (s.indexOf("暂无实况") != -1) {
                Toast.makeText(MainActivity.this, "请输入中国城市", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return false;
    }

}


























