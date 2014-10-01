/**
 * Based on the Bluetoothchat sample application
 * Original Copyright (C) 2009 The Android Open Source Project
 * Derivative work Copyright (C) 2014 Fabrice Bellamy (b dot douze at gmail dot com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.OBDChat;

import org.bdouze.android.obd.AffineObdResponseConverter;
import org.bdouze.android.obd.IObdResponseConverter;
import org.bdouze.android.obd.ObdCommand;
import org.bdouze.android.obd.ObdCommandDictionary;
import org.bdouze.android.obd.SelectObdCommandActivity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This is the main Activity that displays the current chat session.
 */
public class OBDChat extends Activity {
    // Debugging
    private static final String TAG = "OBDChat";
    private static final boolean D = true;

    // Message types sent from the OBDChatService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    // Key names received from the OBDChatService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
    private static final int REQUEST_ENABLE_BT = 3;    
    private static final int REQUEST_OBD_COMMAND = 4;

    // Layout Views
    private ListView conversationView;
    private EditText outEditText;
    private Button sendButton;
    private Button selectAtCommandButton;
    private Button selectMode1CommandButton;
    private Button selectMode2CommandButton;
    private Button selectMode3CommandButton;
    private Button selectMode4CommandButton;
    private Button selectMode5CommandButton;
    private Button selectMode6CommandButton;
    private Button selectMode7CommandButton;
    private Button selectMode8CommandButton;
    private Button selectMode9CommandButton;
    private Button selectModeACommandButton;
    private Button quickInitButton;

    // Name of the connected device
    private String connectedDeviceName = null;
    // Array adapter for the conversation thread
    private ArrayAdapter<String> conversationArrayAdapter;
    // String buffer for outgoing messages
    private StringBuffer outStringBuffer;
    // Local Bluetooth adapter
    private BluetoothAdapter bluetoothAdapter = null;
    // Member object for the chat services
    private OBDChatService chatService = null;
    // Last built-in OBD Command sent
    private ObdCommand lastObdCommandSent = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(D) Log.e(TAG, "+++ ON CREATE +++");

        // Set up the window layout
        setContentView(R.layout.main);

        // Get local Bluetooth adapter
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // If the adapter is null, then Bluetooth is not supported
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if(D) Log.e(TAG, "++ ON START ++");

        // If BT is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        // Otherwise, setup the chat session
        } else {
            if (chatService == null) setupChat();
        }
    }

    @Override
    public synchronized void onResume() {
        super.onResume();
        if(D) Log.e(TAG, "+ ON RESUME +");

        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
        if (chatService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (chatService.getState() == OBDChatService.STATE_NONE) {
              // Start the Bluetooth chat services
              chatService.start();
            }
        }
    }

    private void setupChat() {
        Log.d(TAG, "setupChat()");

        // Initialize the array adapter for the conversation thread
        conversationArrayAdapter = new ArrayAdapter<String>(this, R.layout.message);
        conversationView = (ListView) findViewById(R.id.in);
        conversationView.setAdapter(conversationArrayAdapter);

        // Initialize the compose field with a listener for the return key
        outEditText = (EditText) findViewById(R.id.edit_text_out);
        outEditText.setOnEditorActionListener(mWriteListener);

        // Initialize the send button with a listener that for click events
        sendButton = (Button) findViewById(R.id.button_send);
        sendButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // Send a message using content of the edit text widget
                TextView view = (TextView) findViewById(R.id.edit_text_out);
                String message = view.getText().toString();
                sendMessage(message);
            }
        });
        
        quickInitButton = (Button) findViewById(R.id.button_select_quick_init);
        quickInitButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String s = "11 2E";
                IObdResponseConverter conv = new AffineObdResponseConverter(100.0/255.0, 0);
                ObdCommand cmd = new ObdCommand("dummy", "dummy", 0, conv, "dummy", "d", "dummy");
                Message msg = new Message();
                msg.what = MESSAGE_READ;
                msg.arg1 = s.length();
                msg.obj = s.getBytes();
                lastObdCommandSent = cmd;
                mHandler.handleMessage(msg);
            }
        });

        selectAtCommandButton = (Button) findViewById(R.id.button_select_at_command);
        selectAtCommandButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent serverIntent = new Intent(OBDChat.this , SelectObdCommandActivity.class);
                serverIntent.putExtra(SelectObdCommandActivity.EXTRA_OBD_COMMAND_TYPE, ObdCommandDictionary.OBD_COMMAND_TYPE_AT);
                startActivityForResult(serverIntent, REQUEST_OBD_COMMAND);
            }
        });
        
        selectMode1CommandButton = (Button) findViewById(R.id.button_select_mode_1_command);
        selectMode1CommandButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent serverIntent = new Intent(OBDChat.this , SelectObdCommandActivity.class);
                serverIntent.putExtra(SelectObdCommandActivity.EXTRA_OBD_COMMAND_TYPE, ObdCommandDictionary.OBD_COMMAND_TYPE_MODE_1);
                startActivityForResult(serverIntent, REQUEST_OBD_COMMAND);
            }
        });
        
        // Initialize the OBDChatService to perform bluetooth connections
        chatService = new OBDChatService(this, mHandler);

        // Initialize the buffer for outgoing messages
        outStringBuffer = new StringBuffer("");
    }

    @Override
    public synchronized void onPause() {
        super.onPause();
        if(D) Log.e(TAG, "- ON PAUSE -");
    }

    @Override
    public void onStop() {
        super.onStop();
        if(D) Log.e(TAG, "-- ON STOP --");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Stop the Bluetooth chat services
        if (chatService != null) chatService.stop();
        if(D) Log.e(TAG, "--- ON DESTROY ---");
    }

    private void ensureDiscoverable() {
        if(D) Log.d(TAG, "ensure discoverable");
        if (bluetoothAdapter.getScanMode() !=
            BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }
    }

    /**
     * Sends a message.
     * @param message  A string of text to send.
     */
    private void sendMessage(String message) {
        message = message + "\r";
        if(D) Log.d(TAG, "going to send " + message.length() + " chars");
        
        // Check that we're actually connected before trying anything
        if (chatService.getState() != OBDChatService.STATE_CONNECTED) {
            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }

        // Check that there's actually something to send
        if (message.length() > 0) {
            // Get the message bytes and tell the OBDChatService to write
            byte[] send = message.getBytes();
            chatService.write(send);

            // Reset out string buffer to zero and clear the edit text field
            outStringBuffer.setLength(0);
            //outEditText.setText(outStringBuffer);
        }
    }

    // The action listener for the EditText widget, to listen for the return key
    private TextView.OnEditorActionListener mWriteListener =
        new TextView.OnEditorActionListener() {
        public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
            // If the action is a key-up event on the return key, send the message
            if (actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_UP) {
                String message = view.getText().toString();
                sendMessage(message);
            }
            if(D) Log.i(TAG, "END onEditorAction");
            return true;
        }
    };

    private final void setStatus(int resId) {
        final ActionBar actionBar = getActionBar();
        actionBar.setSubtitle(resId);
    }

    private final void setStatus(CharSequence subTitle) {
        final ActionBar actionBar = getActionBar();
        actionBar.setSubtitle(subTitle);
    }

    // The Handler that gets information back from the OBDChatService
    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case MESSAGE_STATE_CHANGE:
                if(D) Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
                switch (msg.arg1) {
                case OBDChatService.STATE_CONNECTED:
                    setStatus(getString(R.string.title_connected_to, connectedDeviceName));
                    conversationArrayAdapter.clear();
                    break;
                case OBDChatService.STATE_CONNECTING:
                    setStatus(R.string.title_connecting);
                    break;
                case OBDChatService.STATE_LISTEN:
                case OBDChatService.STATE_NONE:
                    setStatus(R.string.title_not_connected);
                    break;
                }
                break;
            case MESSAGE_WRITE:
                byte[] writeBuf = (byte[]) msg.obj;
                // construct a string from the buffer
                String writeMessage = new String(writeBuf);
                conversationArrayAdapter.add("Me:  " + writeMessage);
                break;
            case MESSAGE_READ:
                byte[] readBuf = (byte[]) msg.obj;
                // construct a string from the valid bytes in the buffer
                String readMessage = new String(readBuf, 0, msg.arg1);
                handleResponse(readMessage);
                break;
            case MESSAGE_DEVICE_NAME:
                // save the connected device's name
                connectedDeviceName = msg.getData().getString(DEVICE_NAME);
                Toast.makeText(getApplicationContext(), "Connected to "
                               + connectedDeviceName, Toast.LENGTH_SHORT).show();
                break;
            case MESSAGE_TOAST:
                Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),
                               Toast.LENGTH_SHORT).show();
                break;
            }
        }
    };

    private void handleResponse(String readMessage) {
        if (null != lastObdCommandSent) {
            // Built-in command was sent
            String cleanResponse = lastObdCommandSent.cleanupResponse(readMessage);
            if (lastObdCommandSent.getConverter().isNumericalsupported()) {
                // Convert response value to numerical and check error cases
                if (null != cleanResponse 
                        && cleanResponse.length() > 0) {
                    String convertedResponse = lastObdCommandSent.getConverter().convertToString(cleanResponse);
                    if (convertedResponse.equals("NaN")) {
                        // Error case, converter failed to convert to a number
                        try {
                            long x = Long.parseLong(readMessage, 16);
                            convertedResponse = " NaN (Raw convert: " + Long.toString(x) + ")";
                        } catch (NumberFormatException e) {
                            convertedResponse = " NaN (Exception: " + e.getMessage() + ")";
                        }                            
                        conversationArrayAdapter.add("OBD: '" 
                                + readMessage 
                                + "' = " 
                                + convertedResponse);
                    } else {
                        // Normal case
                        conversationArrayAdapter.add("OBD: '" 
                                + readMessage 
                                + "' = " 
                                + convertedResponse + " " 
                                + lastObdCommandSent.getUnit());
                    }
                } else {
                    // Error case, no value in response
                    conversationArrayAdapter.add("OBD: '" 
                            + readMessage 
                            + "' = <Empty response>");
                }
            } else {
                // Non numerical value. Display without conversion
                conversationArrayAdapter.add("OBD: " + cleanResponse);
            }
            lastObdCommandSent = null;
        } else {
            // Custom command was sent. Display raw response message
            conversationArrayAdapter.add("OBD: " + readMessage);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(D) Log.d(TAG, "onActivityResult " + resultCode);
        switch (requestCode) {
        case REQUEST_CONNECT_DEVICE_SECURE:
            // When DeviceListActivity returns with a device to connect
            if (resultCode == Activity.RESULT_OK) {
                connectDevice(data, true);
            }
            break;
        case REQUEST_CONNECT_DEVICE_INSECURE:
            // When DeviceListActivity returns with a device to connect
            if (resultCode == Activity.RESULT_OK) {
                connectDevice(data, false);
            }
            break;
        case REQUEST_ENABLE_BT:
            // When the request to enable Bluetooth returns
            if (resultCode == Activity.RESULT_OK) {
                // Bluetooth is now enabled, so set up a chat session
                setupChat();
            } else {
                // User did not enable Bluetooth or an error occurred
                Log.d(TAG, "BT not enabled");
                Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
                finish();
            }
            break;
        case REQUEST_OBD_COMMAND:
            // When SelectObdCommandActivity returns with an OBD command to send
            if (resultCode == Activity.RESULT_OK) {
                lastObdCommandSent = (ObdCommand)data.getExtras().getSerializable(SelectObdCommandActivity.EXTRA_OBD_COMMAND);
                
                EditText view = (EditText) findViewById(R.id.edit_text_out);
                String command = lastObdCommandSent.getCommand();
                view.setText(command);
                view.setSelection(view.getText().length());
                sendMessage(command);
            }
        }
    }

    private void connectDevice(Intent data, boolean secure) {
        // Get the device MAC address
        String address = data.getExtras()
            .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        // Get the BluetoothDevice object
        BluetoothDevice device = bluetoothAdapter.getRemoteDevice(address);
        // Attempt to connect to the device
        chatService.connect(device, secure);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent serverIntent = null;
        switch (item.getItemId()) {
        case R.id.secure_connect_scan:
            // Launch the DeviceListActivity to see devices and do scan
            serverIntent = new Intent(this, DeviceListActivity.class);
            startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_SECURE);
            return true;
        case R.id.insecure_connect_scan:
            // Launch the DeviceListActivity to see devices and do scan
            serverIntent = new Intent(this, DeviceListActivity.class);
            startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_INSECURE);
            return true;
        case R.id.discoverable:
            // Ensure this device is discoverable by others
            ensureDiscoverable();
            return true;
        }
        return false;
    }

}
