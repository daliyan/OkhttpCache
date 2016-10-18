package akiyama.okhttpcache;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.zwyan.generlviewadapter.adapter.GeneraViewAdapter;
import com.zwyan.generlviewadapter.adapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import akiyama.library.exception.NetworkException;
import akiyama.library.request.CacheType;
import akiyama.library.request.HttpManagerHelper;
import akiyama.library.request.NameValuePair;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ListView mListView;
    private List<ZhiHuModel> mZhiHuModels;
    private GeneraViewAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private int mCurrentPage = 1;
    private static final String LIMIT= "12";
    private static final String OFFSET = "10";
    private static final String SEED = "1";
    private boolean mIsRefresh = false;
    private int mCurrentCacheType = CacheType.NETWORK_ELSE_CACHE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mZhiHuModels = new ArrayList<>();
        mListView = (ListView) findViewById(R.id.zhihu_lv);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh_sfl);
        initView();
        loadNextPageData();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(!mIsRefresh){
                    mCurrentPage ++;
                    mIsRefresh = true;
                    loadNextPageData();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId()){
           case R.id.only_cache:
               mCurrentCacheType = CacheType.ONLY_CACHE;
               break;
           case R.id.only_network:
               mCurrentCacheType = CacheType.ONLY_NETWORK;
               break;
           case R.id.network_cache:
               mCurrentCacheType = CacheType.NETWORK_ELSE_CACHE;
               break;
           case R.id.cache_network:
               mCurrentCacheType = CacheType.CACHE_ELSE_NETWORK;
               break;
           case R.id.clean_data:
               if(mZhiHuModels!=null && !mZhiHuModels.isEmpty()){
                   mZhiHuModels.clear();
                   mAdapter.notifyDataSetChanged();
               }
               break;
           default:
               break;
       }
        return true;
    }

    private void loadNextPageData(){
        ZhiHuParam zhiHuParam = new ZhiHuParam();
        zhiHuParam.limit = LIMIT;
        zhiHuParam.offset = OFFSET;
        zhiHuParam.seed = SEED;
        new RequestZhihuTask().execute(zhiHuParam);
    }

    private void initView(){
        mAdapter = new GeneraViewAdapter<ZhiHuModel>(this,mZhiHuModels,R.layout.zhihu_zhuanlan_item) {

            @Override
            public void convert(ViewHolder viewHolder, final ZhiHuModel item) {
                //Log.e("111",item.name);
                String imageUrl = "https://pic2.zhimg.com/"+item.mAvatar.id+"_l.jpg";
                viewHolder.setImageViewByUrl(R.id.zhihu_iv,imageUrl);
                viewHolder.setTextViewString(R.id.title_tv,item.name);
                viewHolder.setTextViewString(R.id.content_tv,item.description);
                viewHolder.setTextViewString(R.id.infomation_tv,item.followersCount+"关注|"+item.postsCount+"篇文章");
                View itemLl = viewHolder.getView(R.id.item_rl);
                itemLl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this,WebActivity.class);
                        intent.putExtra(WebActivity.EXTRA_URL,item.url);
                        startActivity(intent);
                    }
                });
            }
        };
        mListView.setAdapter(mAdapter);
    }
    private class RequestZhihuTask extends AsyncTask<ZhiHuParam, Void, List<ZhiHuModel>> {

        private static final String ZHIHU_ZHUANLAN_API = "https://zhuanlan.zhihu.com/api/recommendations/columns";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<ZhiHuModel> doInBackground(ZhiHuParam... params) {
            try {
                List<NameValuePair> nameValuePairs = new ArrayList<>();
                ZhiHuParam zhiHuParam = params[0];
                if (zhiHuParam != null) {
                    nameValuePairs.add(new NameValuePair("limit", zhiHuParam.limit));
                    nameValuePairs.add(new NameValuePair("offset", zhiHuParam.offset));
                    nameValuePairs.add(new NameValuePair("seed", zhiHuParam.seed));
                    String response = HttpManagerHelper.getInstance().getRequestByCache(ZHIHU_ZHUANLAN_API, nameValuePairs, mCurrentCacheType);
                    return ZhiHuModel.getZhihuModelS(response);
                }
            } catch (NetworkException e) {
                Log.e(TAG,e.toString());
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<ZhiHuModel> zhiHuModels) {
            refreshList(zhiHuModels);

        }
    }

    private void refreshList(final List<ZhiHuModel> zhiHuModels){
        if(zhiHuModels!=null && !zhiHuModels.isEmpty()){
            mZhiHuModels.addAll(zhiHuModels);
            mAdapter.notifyDataSetChanged();
            mIsRefresh = false;
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    private class ZhiHuParam {
        //limit=12&offset=32&seed=18
        public String limit;
        public String offset;
        public String seed;
    }
}
