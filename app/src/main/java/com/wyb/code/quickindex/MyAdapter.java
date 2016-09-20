package com.wyb.code.quickindex;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ${kissfoot} on 2016/9/19.
 */
public class MyAdapter extends BasicAdapter<Friends> {

    private Context mContext;
    private List<Friends> mList;

    public MyAdapter(Context context, List<Friends> mList) {
        super(mList);
        this.mContext = context;
        this.mList = mList;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.list_item, viewGroup, false);
        }

        ViewHolder holder = getViewHolder(view);
        Friends friends = mList.get(i);
        //当前位置上的首字母
        String currentLetter = String.valueOf(friends.getPinyin().charAt(0));
        if (i > 0) {
            //上一个位置的首字母
            String lastLetter = String.valueOf(mList.get(i - 1).getPinyin().charAt(0));
            if (TextUtils.equals(currentLetter,lastLetter)){
                //如果当前和上一个字母相等，则不显示当前位置的拼音首字母，只显示一个位置上的就可以了
                holder.tvLetter.setVisibility(View.GONE);
            }else {
                holder.tvLetter.setVisibility(View.VISIBLE);
                holder.tvLetter.setText(currentLetter);
            }
        }else {
            holder.tvLetter.setVisibility(View.VISIBLE);
            holder.tvLetter.setText(currentLetter);
        }
        holder.tvName.setText(friends.getName());
        return view;
    }

    private ViewHolder getViewHolder(View view) {
        ViewHolder holder = (ViewHolder) view.getTag();
        if (holder == null) {
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        return holder;
    }

    private class ViewHolder {
        private TextView tvLetter, tvName;

        public ViewHolder(View view) {
            tvLetter = (TextView) view.findViewById(R.id.tv_letter);
            tvName = (TextView) view.findViewById(R.id.tv_name);
        }
    }
}
