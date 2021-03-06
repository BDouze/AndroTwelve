/**
 * Copyright (C) 2014 Fabrice Bellamy (b dot douze at gmail dot com)
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
package org.bdouze.android.obd;

import com.example.android.OBDChat.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class SelectObdCommandActivity extends Activity {

    // Debugging
    private static final String TAG = "SelectObdCommandActivity";
    private static final boolean D = true;

    // Return Intent extra
    public static String EXTRA_OBD_COMMAND = "obd_command";
    public static String EXTRA_OBD_COMMAND_TYPE = "obd_command_type";
    
    // Member fields
    private ArrayAdapter<ObdCommand> obdCommandsArrayAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_obd_command);

        // Set result CANCELED in case the user backs out
        setResult(Activity.RESULT_CANCELED);

        // Initialize array adapter for the list of OBD command
        obdCommandsArrayAdapter = new ArrayAdapter<ObdCommand>(this, R.layout.obd_command);

        // Find and set up the ListView for paired devices
        ListView obdCommandsListView = (ListView) findViewById(R.id.obd_commands_list_view);
        obdCommandsListView.setAdapter(obdCommandsArrayAdapter);
        obdCommandsListView.setOnItemClickListener(obdCommandClickListener);
        
        
        ObdCommand[] commands = null;
        switch (getIntent().getExtras().getInt(EXTRA_OBD_COMMAND_TYPE)) {
            case ObdCommandDictionary.OBD_COMMAND_TYPE_AT : 
                commands = ObdCommandDictionary.getATCommands(); 
                break;
            case ObdCommandDictionary.OBD_COMMAND_TYPE_MODE_1 : 
                commands = ObdCommandDictionary.getMode01Commands(); 
                break;
        }
        if (null != commands) {
            obdCommandsArrayAdapter.addAll(commands);
        }

    }
    
    // The on-click listener for the OBD commands ListViews
    private OnItemClickListener obdCommandClickListener = new OnItemClickListener() {
        public void onItemClick(AdapterView<?> av, View v, int position, long id) {
            
            String info = ((TextView) v).getText().toString();
            
            if(D) Log.d(TAG, "obdCommandClickListener info : " + info);
            if(D) Log.d(TAG, "obdCommandClickListener position : " + position);
            if(D) Log.d(TAG, "obdCommandClickListener id : " + id);

            // Create the result Intent and include the MAC address
            Intent intent = new Intent();
            //intent.putExtra(EXTRA_OBD_COMMAND, obdCommandsArrayAdapter.getItem(position).getCommand());
            intent.putExtra(EXTRA_OBD_COMMAND, obdCommandsArrayAdapter.getItem(position));

            // Set result and finish this Activity
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    };
    
}
