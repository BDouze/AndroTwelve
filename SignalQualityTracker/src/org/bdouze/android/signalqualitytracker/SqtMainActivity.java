package org.bdouze.android.signalqualitytracker;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class SqtMainActivity extends Activity {

    private PhoneStateListener phoneStateListener;
    private TextView textViewSQV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqt_main);
        // Get reference to widgets we will interact with
        textViewSQV = (TextView) this.findViewById(R.id.textViewSQV);
        
        // Create a listener to receive phone state changes
        phoneStateListener = new PhoneStateListener() {
            public void onCellLocationChanged(CellLocation location) {}
            public void onDataConnectionStateChanged(int state) {}
            public void onServiceStateChanged(ServiceState serviceState) {
            	serviceStateChanged(serviceState);
            }
            public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            	signalStrengthsChanged(signalStrength); 	
            }
        };
            
        // Register the listener
        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(phoneStateListener, 
                PhoneStateListener.LISTEN_SIGNAL_STRENGTHS | 
                PhoneStateListener.LISTEN_SERVICE_STATE | 
                PhoneStateListener.LISTEN_CELL_INFO | 
                PhoneStateListener.LISTEN_CELL_LOCATION | 
                PhoneStateListener.LISTEN_DATA_CONNECTION_STATE);
    }

    private void signalStrengthsChanged(SignalStrength signalStrength) {
    	textViewSQV.setText(Integer.toString(signalStrength.getGsmSignalStrength()));
    }
    
    private void serviceStateChanged(ServiceState serviceState) {
    	
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_sqt_main, menu);
        return true;
    }
    


    @Override
    protected void onStart() {
    	super.onStart();
    }


    /*
    @Override
    protected void onRestart() {
    	super.onRestart();
    }
    */

    
    /*
    @Override
    protected void onResume() {
    	super.onResume();
    }
    */

    
    /*
    @Override
    protected void onPause() {
    	super.onPause();
    }
    */

    
    /*
    @Override
    protected void onStop() {
    	super.onStop();
    }
    */

    /*
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    }
    */
    
}
