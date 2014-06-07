package com.sserhuangdong.whatever.app;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResultsAdapter extends BaseAdapter {
    private List<Map<String, String>> results;
    private Context mContext;

    public ResultsAdapter(Context context) {
        mContext = context;
        results = new ArrayList<Map<String, String>>();
    }

    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public Object getItem(int i) {
        return results.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;

        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.listview_item , null);
            holder.tv_result = (TextView) view.findViewById(R.id.tv_result);
            holder.btn_like = (Button) view.findViewById(R.id.btn_like);
            holder.btn_unlike = (Button) view.findViewById(R.id.btn_no_feel);
            holder.btn_del = (Button) view.findViewById(R.id.btn_del);
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.tv_result.setText(results.get(i).get("name"));

        View.OnClickListener onClickListener = new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                // Toast.makeText(mContext, "hello", Toast.LENGTH_SHORT).show();
                results.remove(i);
                notifyDataSetChanged();
            }
        };

        holder.btn_like.setOnClickListener(onClickListener);
        holder.btn_unlike.setOnClickListener(onClickListener);
        holder.btn_del.setOnClickListener(onClickListener);

        return view;
    }

    public void addItem(Map<String, String> item) {
        results.add(item);
        notifyDataSetChanged();
    }

    class ViewHolder {
        TextView tv_result;
        Button btn_like;
        Button btn_unlike;
        Button btn_del;
    }
}
