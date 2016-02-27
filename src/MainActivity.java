package ads.app.dveag.com.dshop;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private WebView mwebview;
    private ArrayList<String> optionList;
    private ListView drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mwebview = (WebView) findViewById(R.id.webView);

        WebSettings mwebsettings = mwebview.getSettings();
        mwebsettings.setJavaScriptEnabled(true);

        mwebview.loadUrl("https://daviela.pswebshop.com/es/");

        mwebview.setWebViewClient(new WebViewClient());
        mwebview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        mwebview.getSettings().setLoadWithOverviewMode(true);
        mwebview.getSettings().setUseWideViewPort(true);

        mwebview.getSettings().setSupportZoom(true);
        mwebview.getSettings().setBuiltInZoomControls(true);
        mwebview.getSettings().setDisplayZoomControls(false);

        drawerLayout = (ListView) findViewById(R.id.drawerLayout);
        optionList = new ArrayList<>();
        optionList.add("Mapa");
        optionList.add("Llamar");

        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_expandable_list_item_1,optionList);
        drawerLayout.setAdapter(adapter);

        drawerLayout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0) {
                    Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                    startActivity(intent);
                }
                else{
                    call();
                }

            }
        });
    }

    private void call() {
        Intent in=new Intent(Intent.ACTION_DIAL);
        in.setData(Uri.parse("tel:663191236"));
        try{
            startActivity(in);
        }

        catch (android.content.ActivityNotFoundException ex){
            Toast.makeText(getApplicationContext(),"yourActivity is not founded", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed(){
        if(mwebview.canGoBack())
            mwebview.goBack();
        else
            super.onBackPressed();
    }
}
