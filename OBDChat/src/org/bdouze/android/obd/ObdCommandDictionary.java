/**
 * 
 */
package org.bdouze.android.obd;

/**
 * @author fabriceb
 *
 */
public final class ObdCommandDictionary {
    private ObdCommandDictionary()
    {
        throw new UnsupportedOperationException("This is a static class");
    }
    
    public static final int OBD_COMMAND_TYPE_AT = 0;
    public static final int OBD_COMMAND_TYPE_MODE_1 = 1;
    public static final int OBD_COMMAND_TYPE_MODE_2 = 2;
    public static final int OBD_COMMAND_TYPE_MODE_3 = 3;
    public static final int OBD_COMMAND_TYPE_MODE_4 = 4;
    public static final int OBD_COMMAND_TYPE_MODE_5 = 5;
    public static final int OBD_COMMAND_TYPE_MODE_6 = 6;
    public static final int OBD_COMMAND_TYPE_MODE_7 = 7;
    public static final int OBD_COMMAND_TYPE_MODE_8 = 8;
    public static final int OBD_COMMAND_TYPE_MODE_9 = 9;
    public static final int OBD_COMMAND_TYPE_MODE_A = 10;
          
    private static final ObdCommand[] DICTIONARY_ENTRIES_AT = new ObdCommand[] {
        new ObdCommand("ATZ",    null, 0, new DefaultObdResponseConverter(), "Reset device", null, ""),
        new ObdCommand("ATE0",   null, 0, new DefaultObdResponseConverter(), "Echo off", null, ""),
        new ObdCommand("ATE1",   null, 0, new DefaultObdResponseConverter(), "Echo on", null, ""),
        new ObdCommand("ATL0",   null, 0, new DefaultObdResponseConverter(), "Linefeeds off", null, ""),
        new ObdCommand("ATL1",   null, 0, new DefaultObdResponseConverter(), "Linefeeds on", null, ""),
        new ObdCommand("ATS0",   null, 0, new DefaultObdResponseConverter(), "Print spaces off", null, ""),
        new ObdCommand("ATS1",   null, 0, new DefaultObdResponseConverter(), "Print spaces on", null, ""),
        new ObdCommand("ATH0",   null, 0, new DefaultObdResponseConverter(), "Headers off", null, ""),
        new ObdCommand("ATH1",   null, 0, new DefaultObdResponseConverter(), "Headers on", null, ""),
        new ObdCommand("ATSP0",  null, 0, new DefaultObdResponseConverter(), "Set protocol auto", null, "Set protocol to auto and save it"),
        new ObdCommand("ATTP0",  null, 0, new DefaultObdResponseConverter(), "Try protocol auto", null, ""),
        new ObdCommand("SPSLCS", null, 0, new DefaultObdResponseConverter(), "Print active PowerSave configuration summary", null, ""),
        new ObdCommand("SPSLLT", null, 0, new DefaultObdResponseConverter(), "Report last sleep/wakeup triggers", null, "Long_description"),
        new ObdCommand("AT",     null, 0, new DefaultObdResponseConverter(), "Short_desc", null, "Long_description")
    };

    private static final ObdCommand[] DICTIONARY_ENTRIES_01 = new ObdCommand[] {
        new ObdCommand("0100", "4100", 0, new DefaultObdResponseConverter(), "PIDs supported [01 - 20]", null, "Bit mask"),
        new ObdCommand("0101", "4101", 0, new DefaultObdResponseConverter(), "Monitor status since DTCs cleared", null, "Bit encoded. Includes malfunction indicator lamp (MIL) status and number of DTCs."),
        new ObdCommand("0102", "4102", 0, new DefaultObdResponseConverter(), "Freeze DTC", null, ""),
        new ObdCommand("0103", "4103", 0, new DefaultObdResponseConverter(), "Fuel system status", null, "Bit encoded"),
        new ObdCommand("0104", "4104", 0, new AffineObdResponseConverter(100.0/255.0, 0.0), "Calculated engine load value", "%", ""),
        new ObdCommand("0105", "4105", 0, new AffineObdResponseConverter(1.0, -40.0), "Engine coolant temperature", "°C", ""),
        new ObdCommand("0106", "4106", 0, new AffineObdResponseConverter(100.0/128.0, -100.0), "Short term fuel % trim, Bank 1", "%", "-100 Subtracting Fuel (Rich Condition), 99.22 Adding Fuel (Lean Condition)"),
        new ObdCommand("0107", "4107", 0, new AffineObdResponseConverter(100.0/128.0, -100.0), "Long term fuel % trim, Bank 1", "%", "-100 Subtracting Fuel (Rich Condition), 99.22 Adding Fuel (Lean Condition)"),
        new ObdCommand("0108", "4108", 0, new AffineObdResponseConverter(100.0/128.0, -100.0), "Short term fuel % trim, Bank 2", "%", "-100 Subtracting Fuel (Rich Condition), 99.22 Adding Fuel (Lean Condition)"),
        new ObdCommand("0109", "4109", 0, new AffineObdResponseConverter(100.0/128.0, -100.0), "Long term fuel % trim, Bank 2", "%", "-100 Subtracting Fuel (Rich Condition), 99.22 Adding Fuel (Lean Condition)"),
        new ObdCommand("010A", "410A", 0, new AffineObdResponseConverter(3.0, 0.0), "Fuel pressure", "kPa", ""),
        new ObdCommand("010B", "410B", 0, new AffineObdResponseConverter(1.0, 0.0), "Intake manifold absolute pressure", "kPa", ""),
        new ObdCommand("010C", "410C", 0, new AffineObdResponseConverter(0.25, 0.0), "Engine RPM", "RPM", "Engine Rotation Per Minute"),
        new ObdCommand("010D", "410D", 0, new AffineObdResponseConverter(1.0, 0.0), "Vehicle speed", "km/h", ""),
        new ObdCommand("010E", "410E", 0, new AffineObdResponseConverter(0.5, -64.0), "Timing advance", "°", "Timing advance relative to first cylinder"),
        new ObdCommand("010F", "410F", 0, new AffineObdResponseConverter(1.0, -40.0), "Intake air temperature", "°C", ""),

        new ObdCommand("0110", "4110", 0, new AffineObdResponseConverter(0.01, 0.0), "MAF air flow rate", "g/s", ""),
        new ObdCommand("0111", "4111", 0, new AffineObdResponseConverter(100.0/255.0, 0.0), "Throttle position", "%", ""),
        new ObdCommand("0112", "4112", 0, new DefaultObdResponseConverter(), "Commanded secondary air status", null, "Bit encoded"),
        new ObdCommand("0113", "4113", 0, new DefaultObdResponseConverter(), "Oxygen sensors present", null, "Bit mask"),
        new ObdCommand("0114", "4114", 0, new DualAffineObdResponseConverter(0.005, 0.0, 100.0/128.0, -100.0), "Oxygen sensor Bank 1, Sensor 1", "{V, %}", "This PID returns 2 values: {Oxygen sensor voltage, Short term fuel trim}"),
        new ObdCommand("0115", "4115", 0, new DualAffineObdResponseConverter(0.005, 0.0, 100.0/128.0, -100.0), "Oxygen sensor Bank 1, Sensor 2", "{V, %}", "Long_description"),
        new ObdCommand("0116", "4116", 0, new DualAffineObdResponseConverter(0.005, 0.0, 100.0/128.0, -100.0), "Oxygen sensor Bank 1, Sensor 3", "{V, %}", "Long_description"),
        new ObdCommand("0117", "4117", 0, new DualAffineObdResponseConverter(0.005, 0.0, 100.0/128.0, -100.0), "Oxygen sensor Bank 1, Sensor 4", "{V, %}", "Long_description"),
        new ObdCommand("0118", "4118", 0, new DualAffineObdResponseConverter(0.005, 0.0, 100.0/128.0, -100.0), "Oxygen sensor Bank 2, Sensor 1", "{V, %}", "Long_description"),
        new ObdCommand("0119", "4119", 0, new DualAffineObdResponseConverter(0.005, 0.0, 100.0/128.0, -100.0), "Oxygen sensor Bank 2, Sensor 2", "{V, %}", "Long_description"),
        new ObdCommand("011A", "411A", 0, new DualAffineObdResponseConverter(0.005, 0.0, 100.0/128.0, -100.0), "Oxygen sensor Bank 2, Sensor 3", "{V, %}", "Long_description"),
        new ObdCommand("011B", "411B", 0, new DualAffineObdResponseConverter(0.005, 0.0, 100.0/128.0, -100.0), "Oxygen sensor Bank 2, Sensor 4", "{V, %}", "Long_description"),
        new ObdCommand("011C", "411C", 0, new DefaultObdResponseConverter(), "OBD standards this vehicle conforms to", null, "Bit encoded"),
        new ObdCommand("011D", "411D", 0, new DefaultObdResponseConverter(), "Oxygen sensors present", null, "Bit encoded"),
        new ObdCommand("011E", "411E", 0, new DefaultObdResponseConverter(), "Auxiliary input status", null, "Bit encoded"),
        new ObdCommand("011F", "411F", 0, new AffineObdResponseConverter(1.0, 0.0), "Run time since engine start", "s", ""),

        new ObdCommand("0120", "4120", 0, new DefaultObdResponseConverter(), "PIDs supported [21 - 40]", null, "Bit mask"),
        new ObdCommand("0140", "4140", 0, new DefaultObdResponseConverter(), "PIDs supported [41 - 60]", null, "Bit mask"),
        new ObdCommand("015E", "415E", 0, new AffineObdResponseConverter(0.05, 0.0), "Engine fuel rate", "l/h", ""),
        new ObdCommand("0160", "4160", 0, new DefaultObdResponseConverter(), "PIDs supported [61 - 80]", null, "Bit mask"),
        new ObdCommand("0180", "4180", 0, new DefaultObdResponseConverter(), "PIDs supported [81 - A0]", null, "Bit mask"),
        new ObdCommand("01A0", "41A0", 0, new DefaultObdResponseConverter(), "PIDs supported [A1 - C0]", null, "Bit mask"),
        new ObdCommand("01C0", "41C0", 0, new DefaultObdResponseConverter(), "PIDs supported [C1 - E0]", null, "Bit mask"),
        new ObdCommand("01E0", "41E0", 0, new DefaultObdResponseConverter(), "PIDs supported [E1 - FF]", null, "Bit mask")
    };
    
    public static ObdCommand[] getMode01Commands() {
        return DICTIONARY_ENTRIES_01;
    }

    public static ObdCommand[] getATCommands() {
        return DICTIONARY_ENTRIES_AT;
    }
}
