package fitbit.com.firstsession;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

    private static final String TAG = MainActivity.class.getName() ;
    private Button scanButton;
    private Button scanButton_2;
    private ArrayList<String> devices = new ArrayList<>();
    MyRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    BluetoothController bluetoothController = new BluetoothController();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate");

        recyclerView = findViewById(R.id.rvDevices);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MyRecyclerViewAdapter(this, devices);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        initViews(); // scan devices; fill array with stuff


        adapter = new MyRecyclerViewAdapter(this, devices);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

//        registerReceiver(new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                String deviceinfo = intent.getStringExtra("deviceinfo");
//                devices.add(deviceinfo);
//            }
//        }, new IntentFilter("my_result_action"));
//
//        adapter = new MyRecyclerViewAdapter(this, devices);
//        adapter.setClickListener(this);
//        recyclerView.setAdapter(adapter);

    }

    private void initViews() {
        scanButton = findViewById(R.id.scan_button);
        scanButton_2 = findViewById(R.id.scan_button_BLE);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkAndRequestPermissionForScanning()) {
                    bluetoothController.startScan(MainActivity.this);

                    LocalBroadcastManager.getInstance(MainActivity.this).registerReceiver(new MyReceiver() {
                        @Override
                        public void onReceive(Context context, Intent intent) {
                            String deviceinfo = intent.getStringExtra("deviceinfo");
                            devices.add(deviceinfo);
                            adapter.notifyDataSetChanged();
                        }
                    }, new IntentFilter("my_result_action"));
                }

            }
        });


        scanButton_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private boolean checkAndRequestPermissionForScanning() {
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)) {

                Toast.makeText(MainActivity.this,
                        "Cannot scan without granting location permission.",
                        Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                       1); // <--- a number defined by you in your activity
            }
            return false;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getApplication().getBaseContext(), "hi", Toast.LENGTH_LONG);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
