package fitbit.com.firstsession;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {

    String deviceName;
    String deviceHardwareAddress;
    @Override
    public void onReceive(Context context, Intent intent) {
       if (intent.getAction().equals(BluetoothDevice.ACTION_FOUND)) {
               BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
               deviceName = device.getName();
               deviceHardwareAddress = device.getAddress();
           Log.i("demo", deviceName+" ");
           Intent intent1 = new Intent();
           String result = deviceHardwareAddress + " " + deviceName;
           intent1.putExtra("deviceinfo", result);
           intent1.setAction("my_result_action");

           LocalBroadcastManager.getInstance(context).sendBroadcast(intent1);
       }
    }
}
