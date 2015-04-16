package com.jacob.qq.draylayout;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jacob.qq.draylayout.bean.ListItemBean;
import com.jacob.qq.draylayout.view.QQDragLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends FragmentActivity {
    private QQDragLayout mDragLayout;
    private ListView mListMenu;
    private ListView mListMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDragLayout = (QQDragLayout) findViewById(R.id.drag_layout);
        setupMenuList();
        setupMainList();

        ImageView imageView = (ImageView) findViewById(R.id.image_view_avatar);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDragLayout.toggle();
            }
        });
    }

    private void setupMainList() {
        mListMain = (ListView) findViewById(R.id.list_view_user);
        List<ListItemBean> mainList= new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            mainList.add(new ListItemBean(R.drawable.pic5,"Hello Android ViewDragHelper"));
            mainList.add(new ListItemBean(R.drawable.pic2,"Hello Android ViewDragHelper"));
            mainList.add(new ListItemBean(R.drawable.pic3,"Hello Android ViewDragHelper"));
            mainList.add(new ListItemBean(R.drawable.pic4,"Hello Android ViewDragHelper"));
        }
        LeftMenuAdapter adapter = new LeftMenuAdapter(mainList,1);
        mListMain.setAdapter(adapter);
    }

    private void setupMenuList() {
        mListMenu = (ListView) findViewById(R.id.list_view_left_menu);
        List<ListItemBean> menuItemList = Arrays.asList(
                new ListItemBean(R.drawable.ic_item_1, "开通会员"),
                new ListItemBean(R.drawable.ic_item_2, "QQ钱包"),
                new ListItemBean(R.drawable.ic_item_9, "我的相册"),
                new ListItemBean(R.drawable.ic_item_13, "我的装扮"));
        LeftMenuAdapter adapter = new LeftMenuAdapter(menuItemList,2);
        mListMenu.setAdapter(adapter);
    }


    private class LeftMenuAdapter extends BaseAdapter {

        List<ListItemBean> menuItemList;
        LayoutInflater layoutInflater;
        int type ;
        private LeftMenuAdapter(List<ListItemBean> menuItemList,int type) {
            this.menuItemList = menuItemList;
            layoutInflater = LayoutInflater.from(MainActivity.this);
            this.type = type;
        }

        @Override
        public int getCount() {
            return menuItemList.size();
        }

        @Override
        public ListItemBean getItem(int position) {
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
                if (type == 1){
                    viewHolder.textView.setTextColor(Color.parseColor("#FF595959"));
                }else{
                    viewHolder.textView.setTextColor(Color.parseColor("#FFFFFF"));
                }
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            ListItemBean menuItem = getItem(position);
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
