package com.sserhuangdong.whatever.app;

import android.content.SharedPreferences;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainController {
    private View rootView;
    private ResultsAdapter mAdapter;
    private OptionDAO optionDAO;
    private Handler handler;
    Button btn_pick_one;

    public MainController(View rootView) {
        this.rootView = rootView;
        mAdapter = new ResultsAdapter(rootView.getContext());
        optionDAO = new OptionDAO(rootView.getContext());

        SharedPreferences sp = rootView.getContext().getSharedPreferences("whatever", 0);
        boolean isDataBaseInit = sp.getBoolean("isDataBaseInit", false);
        if (!isDataBaseInit) {
            SharedPreferences.Editor ed = sp.edit();
            optionDAO.init();
            ed.putBoolean("isDataBaseInit", true);
            ed.commit();
        }


    handler = new Handler();
        initView();

    }

    private void initView() {
        ListView lv_results = (ListView) rootView.findViewById(R.id.lv_results);
        lv_results.setAdapter(mAdapter);
        lv_results.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        btn_pick_one = (Button) rootView.findViewById(R.id.btn_pick_one);
        btn_pick_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_pick_one.setClickable(false);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Map<String, String> item = pickOne();
                        handler.post(new AddItemRunnable(item));
                    }
                }).start();

            }
        });
    }



    private Map<String, String> pickOne() {
        int len = 5;
        List<Map<String, String>> options = optionDAO.getSomeOption(len);

        len = options.size();
        if (len == 0) {

            return null;
        }
        return options.get(0);
    }

    class AddItemRunnable implements Runnable {
        Map<String, String> item;

        public AddItemRunnable(Map<String, String> it) {
            this.item = it;
        }

        @Override
        public void run() {
            if (mAdapter != null && item != null) {
                mAdapter.addItem(item);
            } else {
                System.out.println("Debug: mAdapter null? " + (mAdapter == null));
            }
            btn_pick_one.setClickable(true);
        }
    }

}
