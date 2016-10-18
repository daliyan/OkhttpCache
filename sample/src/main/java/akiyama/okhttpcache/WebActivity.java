package akiyama.okhttpcache;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

/**
 * Created by Administrator on 2016/10/17.
 */
public class WebActivity extends AppCompatActivity {

    public static final String EXTRA_URL = "extra_url";
    private WebView mWebView;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        mWebView = (WebView) findViewById(R.id.web_wv);
        url = getIntent().getStringExtra(EXTRA_URL);
        mWebView.loadUrl(url);
    }
}
