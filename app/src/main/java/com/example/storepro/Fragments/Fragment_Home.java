package com.example.storepro.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.storepro.GlideImageLoader;
import com.example.storepro.News.JsonParse;
import com.example.storepro.News.NewsActivity1;
import com.example.storepro.News.NewsActivity2;
import com.example.storepro.News.NewsActivity3;
import com.example.storepro.News.NewsActivity4;
import com.example.storepro.News.NewsActivity5;
import com.example.storepro.News.NewsActivity6;
import com.example.storepro.News.NewsActivity7;
import com.example.storepro.News.NewsInfo;
import com.example.storepro.News.NewsMainActivity;
import com.example.storepro.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.image.SmartImageView;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Home extends Fragment{
    private View homeView;

    private LinearLayout loading;
    private ListView lvNews;
    private List<NewsInfo> newsInfos;
    private TextView tv_title;
    private TextView tv_description;
    private TextView tv_type;
    private NewsInfo newsInfo;
    private SmartImageView siv;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        homeView = inflater.inflate(R.layout.module_fragment_home, container, false);
        List images = new ArrayList();
        images.add("https://t7.baidu.com/it/u=2708276792,219516514&fm=193&f=GIF");
        images.add("https://t7.baidu.com/it/u=2984822884,629429889&fm=193&f=GIF");
        images.add("https://t7.baidu.com/it/u=3980489931,4090080080&fm=193&f=GIF");

        Banner banner = homeView.findViewById(R.id.banner);
        //?????????????????????
        banner.setImageLoader(new GlideImageLoader());
        //??????????????????
        banner.setImages(images);
        //banner?????????????????????????????????????????????
        banner.start();
        //
        initView();
        fillData();
        return homeView;
    }

    //???????????????
    private void initView() {
        loading = (LinearLayout) homeView.findViewById(R.id.loading);
        lvNews = (ListView) homeView.findViewById(R.id.lv_news);

    }



    //??????AsyncHttpClient????????????????????????????????????????????????????????????
    private void fillData() {

        //?????????AsyncHttpClient??????
        AsyncHttpClient client = new AsyncHttpClient();
        //??????string.xlm??????url??????get??????????????????
        client.get(getString(R.string.server_url), new AsyncHttpResponseHandler() {
            //???????????????
            @Override
            public void onSuccess(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes) {

                try {
                    //??????json??????
                    String json = new String(bytes, "utf-8");
                    //??????getNewsInfo()??????json??????
                    newsInfos = JsonParse.getNewsInfo(json);
                    //????????????????????????????????????????????????????????????????????????????????????
                    if (newsInfos == null) {
                        Toast.makeText(Fragment_Home.this.getActivity(), "????????????", Toast.LENGTH_SHORT).show();
                    } else {
                        //???????????????????????????
                        loading.setVisibility(View.INVISIBLE);
                        //???????????????
                        lvNews.setAdapter(new Fragment_Home.NewsAdapter());
                        //???????????????????????????
                        lvNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                switch (position){
                                    case 0:
                                        Intent intent = new Intent(Fragment_Home.this.getActivity(), NewsActivity1.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        break;
                                    case 1:
                                        Intent intent1 = new Intent(Fragment_Home.this.getActivity(), NewsActivity2.class);
                                        startActivity(intent1);
                                        break;
                                    case 2:
                                        Intent intent2 = new Intent(Fragment_Home.this.getActivity(), NewsActivity3.class);
                                        startActivity(intent2);
                                        break;
                                    case 3:
                                        Intent intent3 = new Intent(Fragment_Home.this.getActivity(), NewsActivity4.class);
                                        startActivity(intent3);
                                        break;
                                    case 4:
                                        Intent intent4 = new Intent(Fragment_Home.this.getActivity(), NewsActivity5.class);
                                        startActivity(intent4);
                                        break;
                                    case 5:
                                        Intent intent5 = new Intent(Fragment_Home.this.getActivity(), NewsActivity6.class);
                                        startActivity(intent5);
                                        break;
                                    case 6:
                                        Intent intent6 = new Intent(Fragment_Home.this.getActivity(), NewsActivity7.class);
                                        startActivity(intent6);
                                        break;
                                }
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //???????????????
            @Override
            public void onFailure(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes, Throwable throwable) {

            }



        });
    }


    //?????????
    private class NewsAdapter extends BaseAdapter {
        //?????????????????????
        @Override
        public int getCount() {
            return newsInfos.size();
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(Fragment_Home.this.getActivity(), R.layout.news_item, null);
            siv = (SmartImageView) view.findViewById(R.id.siv_icon);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            tv_description = (TextView) view.findViewById(R.id.tv_description);
            tv_type = (TextView) view.findViewById(R.id.tv_type);
            newsInfo = newsInfos.get(position);
            //?????????????????????????????????????????????????????????????????????????????????????????????
            siv.setImageUrl(newsInfo.getIcon(), R.drawable.ic_launcher, R.drawable.ic_launcher);
            tv_title.setText(newsInfo.getTitle());
            tv_description.setText(newsInfo.getContent());
            //???????????????????????????????????????????????????????????????
            int type = newsInfo.getType();
            switch (type) {
                case 1:
                    tv_type.setText(newsInfo.getComment());
                    break;
                case 2:
                    tv_type.setTextColor(Color.RED);
                    tv_type.setText("??????");
                    break;
                case 3:
                    tv_type.setTextColor(Color.BLUE);
                    tv_type.setText("LIVE");
                    break;
            }
            return view;
        }
        //????????????
        @Override
        public Object getItem(int position) {
            return null;
        }
        //??????id
        @Override
        public long getItemId(int position) {
            return position;
        }
    }
}