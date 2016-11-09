package demo.sensors.md.shouse_sensors;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import demo.sensors.md.shouse_sensors.ws.ApiServiceProvider;
import demo.sensors.md.shouse_sensors.ws.DataHolder;
import demo.sensors.md.shouse_sensors.ws.response.NodesResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements SensorsFragment.OnFragmentInteractionListener{

    private TabLayout tabLayout;

    private ViewPager viewPager;

    Callback<NodesResponse> nodesCallback =  new Callback<NodesResponse>() {
        @Override
        public void onResponse(Call<NodesResponse> call, Response<NodesResponse> response) {
            if(response.isSuccessful() && response.body() != null && response.body().getData().size() != 0) {
                DataHolder.setNodes(response.body().getData());
                setupTabs();
            }
        }

        @Override
        public void onFailure(Call<NodesResponse> call, Throwable t) {
            Toast.makeText(HomeActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View parent = LayoutInflater.from(this).inflate(R.layout.act_home_layout, null);
        setContentView(parent);
        setupView(parent);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_act_main_layout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            requestNodes();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        requestNodes();
    }

    private void requestNodes(){
        Call<NodesResponse> nodesResponseCall = ApiServiceProvider.getApiService().getNodes();
        nodesResponseCall.enqueue(nodesCallback);
    }

    private void setupView(View parent) {
        if (parent != null) {
            final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            CollapsingToolbarLayout collapsingToolbar =
                    (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
//            collapsingToolbar.setTitle(getString(R.string.app_name));
            collapsingToolbar.setTitleEnabled(false);


            setupCollapsingToolbar();
            setupToolbar();
        }
    }

    private void setupTabs() {
        final CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(
                R.id.collapsing_toolbar);

        collapsingToolbar.setTitleEnabled(false);
        // Fragment manager to add fragment in viewpager we will pass object of Fragment manager to adpater class.
        FragmentManager fragmentManager = getSupportFragmentManager();

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        viewPager.setAdapter(new HomePagerFragmentAdapter(fragmentManager, this));

        tabLayout.setupWithViewPager(viewPager);

        // adding functionality to tab and viewpager to manage each other when a page is changed or when a tab is selected
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }

    private void setupCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(
                R.id.collapsing_toolbar);

        collapsingToolbar.setTitleEnabled(false);
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
