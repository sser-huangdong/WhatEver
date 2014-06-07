package com.sserhuangdong.whatever.app;

import android.os.Handler;
import android.view.View;
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

    public MainController(View rootView) {
        this.rootView = rootView;
        mAdapter = new ResultsAdapter(rootView.getContext());
        optionDAO = new OptionDAO(rootView.getContext());
        handler = new Handler();
        initView();

    }

    private void initView() {
        ListView lv_results = (ListView) rootView.findViewById(R.id.lv_results);
        lv_results.setAdapter(mAdapter);

        Button btn_pick_one = (Button) rootView.findViewById(R.id.btn_pick_one);
        btn_pick_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final Map<String, String> item = pickOne();
//                        handler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                mAdapter.addItem(item);
//                            }
//                        });
                    }
                }).start();
                // Map<String, String> item = new HashMap<String, String>();
                // item.put("name", "test");


                // mAdapter.addItem(item);
            }
        });
    }



    private Map<String, String> pickOne() {
        int len = 1;
        List<Map<String, String>> options = optionDAO.getSomeOption(len);

        len = options.size();
        if (len == 0)
            return null;
        return options.get(new Random().nextInt() % len);
    }

}
