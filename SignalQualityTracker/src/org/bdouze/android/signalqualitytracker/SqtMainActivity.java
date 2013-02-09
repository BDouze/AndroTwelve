package org.bdouze.android.signalqualitytracker;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.view.Menu;
import android.widget.TextView;

public class SqtMainActivity extends Activity {

    private static final double[] BER_ASSUMED_VALUES_NUM = { 0.14, 0.28, 0.57,
                                                         1.13, 2.26, 4.53,
                                                         9.05, 18.10 };

    private static final String[] BER_ASSUMED_VALUES_TXT = { "0.14", "0.28",
                                                         "0.57", "1.13",
                                                         "2.26", "4.53",
                                                         "9.05", "18.10" };

    private PhoneStateListener    phoneStateListener     = null;
    private LocationListener      locationListener       = null;

    private TextView              textViewSQV            = null;
    private TextView              textViewSQV2           = null;
    private TextView              textViewSQV3           = null;
    private TextView              textViewSQV4           = null;
    private TextView              textViewSQV5           = null;
    private TextView              textViewSQV6           = null;
    private TextView              textViewSQV7           = null;

    private CellInfo              currentCellInfo        = null;

    private OpenCellIdCellLocator currentCellLocator     = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqt_main);
        // Get reference to widgets we will interact with
        textViewSQV = (TextView) this.findViewById(R.id.textViewSQV);
        textViewSQV2 = (TextView) this.findViewById(R.id.textViewSQV2);
        textViewSQV3 = (TextView) this.findViewById(R.id.textViewSQV3);
        textViewSQV4 = (TextView) this.findViewById(R.id.textViewSQV4);
        textViewSQV5 = (TextView) this.findViewById(R.id.textViewSQV5);
        textViewSQV6 = (TextView) this.findViewById(R.id.textViewSQV6);
        textViewSQV7 = (TextView) this.findViewById(R.id.textViewSQV7);

        // Create a listener to receive phone state changes
        phoneStateListener = new PhoneStateListener() {
            public void onCellLocationChanged(CellLocation location) {
                cellLocationChanged(location);
            }

            public void onDataConnectionStateChanged(int state) {
                dataConnectionStateChanged(state);
            }

            public void onServiceStateChanged(ServiceState serviceState) {
                serviceStateChanged(serviceState);
            }

            public void onSignalStrengthsChanged(SignalStrength signalStrength) {
                signalStrengthsChanged(signalStrength);
            }
        };

        // Create a listener to receive device location changes
        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                deviceLocationChanged(location);

            }

            public void onProviderDisabled(String provider) {

            }

            public void onProviderEnabled(String provider) {

            }

            public void onStatusChanged(String provider,
                    int status,
                    Bundle extras) {

            }
        };

    }

    @Override
    protected void onResume() {
        super.onResume();

        currentCellInfo = new CellInfo();

        // Register the phone state listener
        TelephonyManager telephonyManager = (TelephonyManager) this
                .getSystemService(Context.TELEPHONY_SERVICE);

        telephonyManager.listen(phoneStateListener,
                PhoneStateListener.LISTEN_SIGNAL_STRENGTHS
                        | PhoneStateListener.LISTEN_SERVICE_STATE
                        | PhoneStateListener.LISTEN_CELL_INFO
                        | PhoneStateListener.LISTEN_CELL_LOCATION
                        | PhoneStateListener.LISTEN_DATA_CONNECTION_STATE);

        // Register the location listener
        LocationManager locationManager = (LocationManager) this
                .getSystemService(Context.LOCATION_SERVICE);

        Criteria locationProviderCriteria = new Criteria();
        locationProviderCriteria.setAccuracy(Criteria.ACCURACY_FINE);
        locationProviderCriteria.setAltitudeRequired(true);
        locationManager.requestLocationUpdates(5000, 0,
                locationProviderCriteria, locationListener, null);

    }

    @Override
    protected void onPause() {
        // Unregister the location listener
        LocationManager locationManager = (LocationManager) this
                .getSystemService(Context.LOCATION_SERVICE);

        locationManager.removeUpdates(locationListener);

        // Unregister the phone state listener
        TelephonyManager telephonyManager = (TelephonyManager) this
                .getSystemService(Context.TELEPHONY_SERVICE);

        telephonyManager.listen(phoneStateListener,
                PhoneStateListener.LISTEN_NONE);

        super.onPause();
    }

    private void deviceLocationChanged(Location location) {
        StringBuilder sb = new StringBuilder();
        sb.append(getString(R.string.msg_device_location));
        sb.append(' ');
        sb.append(location.getLatitude());
        sb.append(',');
        sb.append(location.getLongitude());
        sb.append(" (");
        sb.append(location.getAccuracy());
        sb.append(" m)");

        textViewSQV7.setText(sb.toString());
    }

    protected void dataConnectionStateChanged(int state) {
        TelephonyManager telephonyManager = (TelephonyManager) this
                .getSystemService(Context.TELEPHONY_SERVICE);
        int networkType = telephonyManager.getNetworkType();

        StringBuilder sb = new StringBuilder();
        sb.append(getString(R.string.msg_data));
        sb.append(' ');

        switch (state) {
            case TelephonyManager.DATA_DISCONNECTED:
                // IP traffic not available.
                sb.append(getString(R.string.msg_data_disconnected));
                if (TelephonyManager.NETWORK_TYPE_UNKNOWN != networkType) {
                    sb.append(" (");
                    sb.append(getNetworkTypeText(networkType));
                    sb.append(")");
                }
                break;
            case TelephonyManager.DATA_CONNECTING:
                // Currently setting up a data connection.
                sb.append(getString(R.string.msg_data_connecting));
                if (TelephonyManager.NETWORK_TYPE_UNKNOWN != networkType) {
                    sb.append(" (");
                    sb.append(getNetworkTypeText(networkType));
                    sb.append(")");
                }
                break;
            case TelephonyManager.DATA_CONNECTED:
                // IP traffic should be available.
                sb.append(getString(R.string.msg_data_connected));
                sb.append(" (");
                sb.append(getNetworkTypeText(networkType));
                sb.append(")");
                break;
            case TelephonyManager.DATA_SUSPENDED:
                // The connection is up, but IP traffic is temporarily
                // unavailable. For example, in a 2G network, data activity may
                // be suspended when a voice call arrives
                sb.append(getString(R.string.msg_data_suspended));
                if (TelephonyManager.NETWORK_TYPE_UNKNOWN != networkType) {
                    sb.append(" (");
                    sb.append(getNetworkTypeText(networkType));
                    sb.append(")");
                }
                break;
            default:
                sb.append(getString(R.string.msg_unknown));
        }

        textViewSQV5.setText(sb.toString());
    }

    /**
     * Handle signal strength changed event.
     * 
     * Notes:
     * - Only GSM signal strength and error rate are currently handled.
     * - the only action currently implemented is to display the values on text
     * views
     * 
     * @param signalStrength
     */
    private void signalStrengthsChanged(SignalStrength signalStrength) {

        /*
        Received signal strength indication (rssi) values from specification :
        3GPP TS 27.007 V11.5.0 (2012-12), sub-clause 8.5

        -------- Citation --------

        <rssi>: integer type
        0        -113 dBm or less
        1        -111 dBm
        2...30   -109 ... -53 dBm
        31       -51 dBm or greater
        99       not known or not detectable
        
        -------- End of Citation --------     
        
        So value in dBm is equal to (2 * rssi - 113) when rssi is between 0 and
        31 included.
        */

        StringBuilder sb = new StringBuilder();
        sb.append(getString(R.string.msg_gsm_strenght));
        sb.append(' ');
        int gsmStrength = signalStrength.getGsmSignalStrength();
        if (99 != gsmStrength) {
            sb.append(Integer.toString(gsmStrength));
            sb.append(" (");
            sb.append(Integer.toString(2 * gsmStrength - 131));
            sb.append(" dBm)");
        } else {
            sb.append(getString(R.string.msg_unknown));
        }
        textViewSQV3.setText(sb.toString());

        /*
        Bit Error Rate (ber) values from specification : 
        3GPP TS 27.007 V11.5.0 (2012-12), sub-clause 8.5

        -------- Citation --------

        <ber>: integer type; channel bit error rate (in percent)
        0...7   as RXQUAL values in the table in 3GPP TS 45.008 [20] subclause 8.2.4
        99      not known or not detectable

        -------- End of Citation --------         

        RXQUAL values from specification :
        3GPP TS 45.008 V11.2.0 (2012-08), sub-clause 8.2.4

        -------- Citation --------

        When the quality is assessed over the full set and sub set of frames 
        defined in subclause 8.4, eight levels of RXQUAL are defined and shall
        be mapped to the equivalent BER before channel decoding as follows:
        
        RXQUAL_0                    BER   <   0.2 %   Assumed value = 0.14 %
        RXQUAL_1        0.2 %   <   BER   <   0.4 %   Assumed value = 0.28 %
        RXQUAL_2        0.4 %   <   BER   <   0.8 %   Assumed value = 0.57 %
        RXQUAL_3        0.8 %   <   BER   <   1.6 %   Assumed value = 1.13 %
        RXQUAL_4        1.6 %   <   BER   <   3.2 %   Assumed value = 2.26 %
        RXQUAL_5        3.2 %   <   BER   <   6.4 %   Assumed value = 4.53 %
        RXQUAL_6        6.4 %   <   BER   <   12.8 %  Assumed value = 9.05 %
        RXQUAL_7        12.8 %  <   BER               Assumed value = 18.10 %
        
        The assumed values may be employed in any averaging process applied to 
        RXQUAL.

        -------- End of Citation --------     
        */

        sb = new StringBuilder();
        sb.append(getString(R.string.msg_gsm_error_rate));
        sb.append(' ');
        int gsmErrorRate = signalStrength.getGsmBitErrorRate();
        if (99 != gsmErrorRate) {
            sb.append(gsmErrorRate);
            if ((0 <= gsmErrorRate)
                    && (gsmErrorRate <= BER_ASSUMED_VALUES_TXT.length)) {
                sb.append(" (~ ");
                sb.append(BER_ASSUMED_VALUES_TXT[gsmErrorRate]);
                sb.append("  %)");
            }
        } else {
            sb.append(getString(R.string.msg_unknown));
        }
        textViewSQV4.setText(sb.toString());

        currentCellInfo.setSignalStrength(gsmStrength);
    }

    /**
     * Handle service state changed event.
     * 
     * Notes:
     * - the only action currently implemented is to display the service state
     * on a text view
     * 
     * @param serviceState
     */
    private void serviceStateChanged(ServiceState serviceState) {
        StringBuilder sb = new StringBuilder();
        sb.append(getString(R.string.msg_service));
        sb.append(' ');

        String operator = serviceState.getOperatorAlphaShort();
        String operatorNum = serviceState.getOperatorNumeric();
        String mcc = operatorNum.substring(0, 3);
        String mnc = operatorNum.substring(3);

        switch (serviceState.getState()) {
            case ServiceState.STATE_EMERGENCY_ONLY:
                // The phone is registered and locked. Only emergency numbers
                // are allowed.
                sb.append(getString(R.string.msg_service_state_emergency_only));
                if (null != operator) {
                    sb.append(" (mcc=");
                    sb.append(mcc);
                    sb.append(", mnc=");
                    sb.append(mnc);
                    sb.append(": ");
                    sb.append(operator);
                    sb.append(")");
                }
                break;
            case ServiceState.STATE_IN_SERVICE:
                // Normal operation condition, the phone is registered with an
                // operator either in home network or in roaming.
                if (null != operator) {
                    sb.append(operator);
                    sb.append(" (mcc=");
                    sb.append(mcc);
                    sb.append(", mnc=");
                    sb.append(mnc);
                    sb.append(")");
                } else {
                    sb.append(getString(R.string.msg_service_state_in_service));
                }
                break;
            case ServiceState.STATE_OUT_OF_SERVICE:
                // Phone is not registered with any operator, the phone can be
                // currently searching a new operator to register to, or not
                // searching to registration at all, or registration is denied,
                // or radio signal is not available.
                sb.append(getString(R.string.msg_service_state_out_of_service));
                if (null != operator) {
                    sb.append(" (mcc=");
                    sb.append(mcc);
                    sb.append(", mnc=");
                    sb.append(mnc);
                    sb.append(": ");
                    sb.append(operator);
                    sb.append(")");
                }
                break;
            case ServiceState.STATE_POWER_OFF:
                // Radio of telephony is explicitly powered off.
                sb.append(getString(R.string.msg_service_state_power_off));
                break;
            default:
                sb.append(getString(R.string.msg_unknown));
        }

        textViewSQV.setText(sb.toString());

        try {
            currentCellInfo.setMobileCountryCode(Integer.valueOf(mcc));
            currentCellInfo.setMobileNetworkCode(Integer.valueOf(mnc));
        } catch (Exception e) {
            currentCellInfo.setMobileCountryCode(0);
            currentCellInfo.setMobileNetworkCode(0);
        }
    }

    private String getNetworkTypeText(int type) {
        switch (type) {
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                return getString(R.string.msg_unknown);
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return "GPRS";
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return "EDGE";
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return "UMTS";
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                return "HSDPA";
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                return "HSUPA";
            case TelephonyManager.NETWORK_TYPE_HSPA:
                return "HSPA";
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return "CDMA";
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                return "EVDO 0";
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                return "EVDO A";
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                return "1xRTT";
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return "IDEN";
                // Since: API Level 9
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                return "EVDO B";
                // Since: API Level 11
            case TelephonyManager.NETWORK_TYPE_LTE:
                return "LTE";
            case TelephonyManager.NETWORK_TYPE_EHRPD:
                return "EHRPD";
                // Since: API Level 13
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return "HSPA+";
            default:
                return getString(R.string.msg_unknown) + ": "
                        + Integer.toString(type);

        }
    }

    private void cellLocationChanged(CellLocation location) {
        TelephonyManager telephonyManager = (TelephonyManager) this
                .getSystemService(Context.TELEPHONY_SERVICE);
        int networkType = telephonyManager.getNetworkType();

        StringBuilder sb = new StringBuilder();
        sb.append(getString(R.string.msg_cell));
        sb.append(' ');

        if (location instanceof GsmCellLocation) {
            GsmCellLocation gsmLocation = (GsmCellLocation) location;
            int cid = gsmLocation.getCid();
            int lac = gsmLocation.getLac();

            sb.append("CID=");
            sb.append(cid);
            sb.append(", LAC=");
            sb.append(lac);

            if (null == currentCellInfo) {
                currentCellInfo = new CellInfo();
            }
            currentCellInfo.setLocalAreaCode(lac);
            currentCellInfo.setCellId(cid);
            currentCellInfo.setNetworkType(networkType);
            
            locateCell(currentCellInfo);
        } else {
            sb.append(getString(R.string.msg_cell_not_gsm));
        }
        textViewSQV2.setText(sb.toString());

    }

    private void locateCell(CellInfo cellInfo) {

        if (null != currentCellLocator) {
            currentCellLocator.cancel(true);
            currentCellLocator = null;
        }

        if (null == currentCellLocator) {
            currentCellLocator = new OpenCellIdCellLocator() {
                public void cellLocated(String location) {
                    currentCellLocator = null;
                    textViewSQV6.setText(getString(R.string.msg_cell_location)
                            + " "
                            + location);
                }
            };

            textViewSQV6.setText("...");
            currentCellLocator.execute(cellInfo);
        }
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
     * @Override protected void onRestart() { super.onRestart(); }
     */

    /*
     * @Override protected void onStop() { super.onStop(); }
     */

    /*
     * @Override protected void onDestroy() { super.onDestroy(); }
     */

}
