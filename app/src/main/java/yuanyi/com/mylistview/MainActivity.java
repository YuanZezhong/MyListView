package yuanyi.com.mylistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import yuanyi.com.mylistview.base.BaseApp;
import yuanyi.com.mylistview.view.FlexibleListView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "tag";
    private FlexibleListView flv;
    private MyAdapter adapter;
    private List<String> data;
    private View actionBar;
    private View head;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        density();

        initView();
        init();
        registerListener();
    }

    private void density() {
        BaseApp.log("density --> " + getResources().getDisplayMetrics().density);
        BaseApp.log("densityDpi --> " + getResources().getDisplayMetrics().densityDpi);
        BaseApp.log("scaleDensity --> " + getResources().getDisplayMetrics().scaledDensity);
    }

    private void initView() {
        this.flv = (FlexibleListView) findViewById(R.id.listView);
        head = LayoutInflater.from(this).inflate(R.layout.listview_header, flv, false);
        actionBar = findViewById(R.id.custom_actionbar);
    }

    private void registerListener() {
        flv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getAdapter().getItem(position);
                Toast.makeText(MainActivity.this, item, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init() {
        data = new ArrayList<String>();
        for (int i = 0; i < 50; ++i) {
            data.add("Item --> " + i);
        }

        ViewGroup.LayoutParams params = head.getLayoutParams();
        adapter = new MyAdapter();
        flv.bindActionBar(actionBar);
        flv.addHeaderView(head);
        flv.setAdapter(adapter);
    }

    private class MyAdapter extends BaseAdapter {
        private LayoutInflater inflater;

        public MyAdapter() {
            this.inflater = LayoutInflater.from(MainActivity.this);
        }

        @Override
        public int getCount() {
            return data == null ? 0 : data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.listview_item, parent, false);
                holder = new ViewHolder();
                holder.textView = (TextView) convertView.findViewById(R.id.textView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            String item = data.get(position);
            holder.textView.setText(item);
            return convertView;
        }
    }

    private static class ViewHolder {
        public TextView textView;
    }
}
