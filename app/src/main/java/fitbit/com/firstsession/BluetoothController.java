package fitbit.com.firstsession;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class BluetoothController {
    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    MyReceiver receiver;

    public void startScan(Activity activity) {

        if (bluetoothAdapter == null) {
            // Device doesn't support Bluetooth
            Toast.makeText(activity.getApplicationContext(), "Doesn't have bluetooth!", Toast.LENGTH_LONG).show();
            return;
        } else {
            if (bluetoothAdapter.isEnabled()) {
                IntentFilter ifilter = new IntentFilter();
                ifilter.addAction(BluetoothDevice.ACTION_FOUND);
                ifilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
                ifilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

                receiver = new MyReceiver();
                activity.registerReceiver(receiver, ifilter);

                bluetoothAdapter.startDiscovery();

            } else {
                //Ask to the user turn the bluetooth on
                Intent turnBTon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                activity.startActivityForResult(turnBTon, 1);
            }
        }
    }

}
