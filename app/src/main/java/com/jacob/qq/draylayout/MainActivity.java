package com.jacob.qq.draylayout;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jacob.qq.draylayout.bean.MenuItem;

import java.util.Arrays;
import java.util.List;


public class MainActivity extends FragmentActivity {
    private ListView mListMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupMenuList();
    }

    private void setupMenuList() {
        mListMenu = (ListView) findViewById(R.id.list_view_left_menu);
        List<MenuItem> menuItemList = Arrays.asList(
                new MenuItem(R.drawable.ic_item_1, "开通会员"),
                new MenuItem(R.drawable.ic_item_2, "QQ钱包"),
                new MenuItem(R.drawable.ic_item_9, "我的相册"),
                new MenuItem(R.drawable.ic_item_13, "我的装扮"));
        LeftMenuAdapter adapter = new LeftMenuAdapter(menuItemList);
        mListMenu.setAdapter(adapter);
    }


    private class LeftMenuAdapter extends BaseAdapter {

        List<MenuItem> menuItemList;
        LayoutInflater layoutInflater;
        private LeftMenuAdapter(List<MenuItem> menuItemList) {
            this.menuItemList = menuItemList;
            layoutInflater = LayoutInflater.from(MainActivity.this);
        }

        @Override
        public int getCount() {
            return menuItemList.size();
        }

        @Override
        public MenuItem getItem(int position) {
            return menuItemList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder ;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = layoutInflater.inflate(R.layout.layout_menu_item, parent,false);
                viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView_menu);
                viewHolder.textView = (TextView) convertView.findViewById(R.id.textView_menu);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            MenuItem menuItem = getItem(position);
            Log.e("TAG", menuItem.toString());
            viewHolder.imageView.setImageResource(menuItem.getIcon());
            viewHolder.textView.setText(menuItem.getTitle());
            return convertView;
        }

        class ViewHolder {
            ImageView imageView;
            TextView textView;
        }
    }
}
