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

import java.util.HashMap;
import java.util.Map;

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
        //ToDo: Implement bit mask converter
        new ObdCommand("0100", "4100", 0, new DefaultObdResponseConverter(),                   "PIDs supported [01 - 20]", null, "Bit mask"),
        //ToDo: Implement monitor status converter
        new ObdCommand("0101", "4101", 0, new DefaultObdResponseConverter(),                   "Monitor status since DTCs cleared", null, "Bit encoded. Includes malfunction indicator lamp (MIL) status and number of DTCs."),
        // PID 02 is not available in Mode 1
        //new ObdCommand("0102", "4102", 0, new DefaultObdResponseConverter(),                   "Freeze DTC", null, ""),
        //ToDo: Implement fuel system status converter
        new ObdCommand("0103", "4103", 0, new DefaultObdResponseConverter(),                   "Fuel system status", null, "Bit encoded"),
        new ObdCommand("0104", "4104", 0, new AffineObdResponseConverter(100.0/255.0, 0.0),    "Calculated engine load value", "%", ""),
        new ObdCommand("0105", "4105", 0, new AffineObdResponseConverter(1.0, -40.0),          "Engine coolant temperature", "°C", ""),
        new ObdCommand("0106", "4106", 0, new AffineObdResponseConverter(100.0/128.0, -100.0), "Short term fuel % trim, Bank 1", "%", "-100 Subtracting Fuel (Rich Condition), 99.22 Adding Fuel (Lean Condition)"),
        new ObdCommand("0107", "4107", 0, new AffineObdResponseConverter(100.0/128.0, -100.0), "Long term fuel % trim, Bank 1", "%", "-100 Subtracting Fuel (Rich Condition), 99.22 Adding Fuel (Lean Condition)"),
        new ObdCommand("0108", "4108", 0, new AffineObdResponseConverter(100.0/128.0, -100.0), "Short term fuel % trim, Bank 2", "%", "-100 Subtracting Fuel (Rich Condition), 99.22 Adding Fuel (Lean Condition)"),
        new ObdCommand("0109", "4109", 0, new AffineObdResponseConverter(100.0/128.0, -100.0), "Long term fuel % trim, Bank 2", "%", "-100 Subtracting Fuel (Rich Condition), 99.22 Adding Fuel (Lean Condition)"),
        new ObdCommand("010A", "410A", 0, new AffineObdResponseConverter(3.0, 0.0),            "Fuel pressure", "kPa", ""),
        new ObdCommand("010B", "410B", 0, new AffineObdResponseConverter(1.0, 0.0),            "Intake manifold absolute pressure", "kPa", ""),
        new ObdCommand("010C", "410C", 0, new AffineObdResponseConverter(0.25, 0.0),           "Engine RPM", "RPM", "Engine Rotation Per Minute"),
        new ObdCommand("010D", "410D", 0, new AffineObdResponseConverter(1.0, 0.0),            "Vehicle speed", "km/h", ""),
        new ObdCommand("010E", "410E", 0, new AffineObdResponseConverter(0.5, -64.0),          "Timing advance", "°", "Timing advance relative to first cylinder"),
        new ObdCommand("010F", "410F", 0, new AffineObdResponseConverter(1.0, -40.0),          "Intake air temperature", "°C", ""),

        new ObdCommand("0110", "4110", 0, new AffineObdResponseConverter(0.01, 0.0),        "MAF air flow rate", "g/s", ""),
        new ObdCommand("0111", "4111", 0, new AffineObdResponseConverter(100.0/255.0, 0.0), "Throttle position", "%", ""),
        //ToDo: Implement air status converter
        new ObdCommand("0112", "4112", 0, new DefaultObdResponseConverter(),                "Commanded secondary air status", null, "Bit encoded"),
        //ToDo: Implement system status converter
        new ObdCommand("0113", "4113", 0, new DefaultObdResponseConverter(),                "Oxygen sensors present", null, "Bit mask"),
        new ObdCommand("0114", "4114", 0, new DualAffineObdResponseConverter(0.005, 0.0, 100.0/128.0, -100.0), "Oxygen sensor Bank 1, Sensor 1", "{V, %}", "This PID returns 2 values: {Oxygen sensor voltage, Short term fuel trim}"),
        new ObdCommand("0115", "4115", 0, new DualAffineObdResponseConverter(0.005, 0.0, 100.0/128.0, -100.0), "Oxygen sensor Bank 1, Sensor 2", "{V, %}", "Long_description"),
        new ObdCommand("0116", "4116", 0, new DualAffineObdResponseConverter(0.005, 0.0, 100.0/128.0, -100.0), "Oxygen sensor Bank 1, Sensor 3", "{V, %}", "Long_description"),
        new ObdCommand("0117", "4117", 0, new DualAffineObdResponseConverter(0.005, 0.0, 100.0/128.0, -100.0), "Oxygen sensor Bank 1, Sensor 4", "{V, %}", "Long_description"),
        new ObdCommand("0118", "4118", 0, new DualAffineObdResponseConverter(0.005, 0.0, 100.0/128.0, -100.0), "Oxygen sensor Bank 2, Sensor 1", "{V, %}", "Long_description"),
        new ObdCommand("0119", "4119", 0, new DualAffineObdResponseConverter(0.005, 0.0, 100.0/128.0, -100.0), "Oxygen sensor Bank 2, Sensor 2", "{V, %}", "Long_description"),
        new ObdCommand("011A", "411A", 0, new DualAffineObdResponseConverter(0.005, 0.0, 100.0/128.0, -100.0), "Oxygen sensor Bank 2, Sensor 3", "{V, %}", "Long_description"),
        new ObdCommand("011B", "411B", 0, new DualAffineObdResponseConverter(0.005, 0.0, 100.0/128.0, -100.0), "Oxygen sensor Bank 2, Sensor 4", "{V, %}", "Long_description"),
        //ToDo: Implement OBD standards converter
        new ObdCommand("011C", "411C", 0, new DefaultObdResponseConverter(),        "OBD standards this vehicle conforms to", null, "Bit encoded"),
        //ToDo: Implement Oxygen present converter
        new ObdCommand("011D", "411D", 0, new DefaultObdResponseConverter(),        "Oxygen sensors present", null, "Bit encoded"),
        //ToDo: Implement auxiliary input status converter
        new ObdCommand("011E", "411E", 0, new DefaultObdResponseConverter(),        "Auxiliary input status", null, "Bit encoded"),
        new ObdCommand("011F", "411F", 0, new AffineObdResponseConverter(1.0, 0.0), "Run time since engine start", "s", ""),

        
        //ToDo: Implement bit mask converter
        new ObdCommand("0120", "4120", 0, new DefaultObdResponseConverter(),          "PIDs supported [21 - 40]", null, "Bit mask"),
        new ObdCommand("0121", "4121", 0, new AffineObdResponseConverter(1.0, 0.0),   "Distance traveled with MIL on", "km", "Distance traveled with malfunction indicator lamp (MIL) on"),
        new ObdCommand("0122", "4122", 0, new AffineObdResponseConverter(0.079, 0.0), "Fuel Rail Pressure (relative to manifold vacuum)", "kPa", ""),
        new ObdCommand("0123", "4123", 0, new AffineObdResponseConverter(10.0, 0.0),  "Fuel Rail Pressure (diesel, or gasoline direct inject)", "kPa", ""),
        new ObdCommand("0124", "4124", 0, new DualAffineObdResponseConverter(2.0/65535.0, 0.0, 8.0/65535.0, 0.0), "O2S1_WR_lambda(1)", "{, V}", "{Equivalence Ratio, Voltage}"),
        new ObdCommand("0125", "4125", 0, new DualAffineObdResponseConverter(2.0/65535.0, 0.0, 8.0/65535.0, 0.0), "O2S2_WR_lambda(1)", "{, V}", "{Equivalence Ratio, Voltage}"),
        new ObdCommand("0126", "4126", 0, new DualAffineObdResponseConverter(2.0/65535.0, 0.0, 8.0/65535.0, 0.0), "O2S3_WR_lambda(1)", "{, V}", "{Equivalence Ratio, Voltage}"),
        new ObdCommand("0127", "4127", 0, new DualAffineObdResponseConverter(2.0/65535.0, 0.0, 8.0/65535.0, 0.0), "O2S4_WR_lambda(1)", "{, V}", "{Equivalence Ratio, Voltage}"),
        new ObdCommand("0128", "4128", 0, new DualAffineObdResponseConverter(2.0/65535.0, 0.0, 8.0/65535.0, 0.0), "O2S5_WR_lambda(1)", "{, V}", "{Equivalence Ratio, Voltage}"),
        new ObdCommand("0129", "4129", 0, new DualAffineObdResponseConverter(2.0/65535.0, 0.0, 8.0/65535.0, 0.0), "O2S6_WR_lambda(1)", "{, V}", "{Equivalence Ratio, Voltage}"),
        new ObdCommand("012A", "412A", 0, new DualAffineObdResponseConverter(2.0/65535.0, 0.0, 8.0/65535.0, 0.0), "O2S7_WR_lambda(1)", "{, V}", "{Equivalence Ratio, Voltage}"),
        new ObdCommand("012B", "412B", 0, new DualAffineObdResponseConverter(2.0/65535.0, 0.0, 8.0/65535.0, 0.0), "O2S8_WR_lambda(1)", "{, V}", "{Equivalence Ratio, Voltage}"),
        new ObdCommand("012C", "412C", 0, new AffineObdResponseConverter(100.0/255.0, 0.0),    "Commanded EGR", "%", ""),
        new ObdCommand("012D", "412D", 0, new AffineObdResponseConverter(100.0/128.0, -100.0), "EGR Error", "%", ""),
        new ObdCommand("012E", "412E", 0, new AffineObdResponseConverter(100.0/255.0, 0.0),    "Commanded evaporative purge", "%", ""),
        new ObdCommand("012F", "412F", 0, new AffineObdResponseConverter(100.0/255.0, 0.0),    "Fuel Level Input", "%", ""),

        new ObdCommand("0130", "4130", 0, new AffineObdResponseConverter(1.0, 0.0), "Warm-ups since codes cleared", null, "Number of warm-ups since codes cleared"),
        new ObdCommand("0131", "4131", 0, new AffineObdResponseConverter(1.0, 0.0), "Distance traveled since codes cleared", "km", ""),
        //ToDo: Implement converter for 2's complement values
        new ObdCommand("0132", "4132", 0, new DefaultObdResponseConverter(),        "Evap. System Vapor Pressure", "kPa", "Evaporation System Vapor Pressure. ((A*256)+B)/4 (A and B are two's complement signed)"),
        new ObdCommand("0133", "4133", 0, new AffineObdResponseConverter(1.0, 0.0), "Barometric pressure", "kPa", ""),
        new ObdCommand("0134", "4134", 0, new DualAffineObdResponseConverter(2.0/65535.0, 0.0, 1.0/256.0, -128.0), "O2S1_WR_lambda(1)", "{, mA}", "{Equivalence Ratio, Current}"),
        new ObdCommand("0135", "4135", 0, new DualAffineObdResponseConverter(2.0/65535.0, 0.0, 1.0/256.0, -128.0), "O2S2_WR_lambda(1)", "{, mA}", "{Equivalence Ratio, Current}"),
        new ObdCommand("0136", "4136", 0, new DualAffineObdResponseConverter(2.0/65535.0, 0.0, 1.0/256.0, -128.0), "O2S3_WR_lambda(1)", "{, mA}", "{Equivalence Ratio, Current}"),
        new ObdCommand("0137", "4137", 0, new DualAffineObdResponseConverter(2.0/65535.0, 0.0, 1.0/256.0, -128.0), "O2S4_WR_lambda(1)", "{, mA}", "{Equivalence Ratio, Current}"),
        new ObdCommand("0138", "4138", 0, new DualAffineObdResponseConverter(2.0/65535.0, 0.0, 1.0/256.0, -128.0), "O2S5_WR_lambda(1)", "{, mA}", "{Equivalence Ratio, Current}"),
        new ObdCommand("0139", "4139", 0, new DualAffineObdResponseConverter(2.0/65535.0, 0.0, 1.0/256.0, -128.0), "O2S6_WR_lambda(1)", "{, mA}", "{Equivalence Ratio, Current}"),
        new ObdCommand("013A", "413A", 0, new DualAffineObdResponseConverter(2.0/65535.0, 0.0, 1.0/256.0, -128.0), "O2S7_WR_lambda(1)", "{, mA}", "{Equivalence Ratio, Current}"),
        new ObdCommand("013B", "413B", 0, new DualAffineObdResponseConverter(2.0/65535.0, 0.0, 1.0/256.0, -128.0), "O2S8_WR_lambda(1)", "{, mA}", "{Equivalence Ratio, Current}"),
        new ObdCommand("013C", "413C", 0, new AffineObdResponseConverter(0.1, -40.0), "Catalyst Temperature Bank 1, Sensor 1", "°C", ""),
        new ObdCommand("013D", "413D", 0, new AffineObdResponseConverter(0.1, -40.0), "Catalyst Temperature Bank 2, Sensor 1", "°C", ""),
        new ObdCommand("013E", "413E", 0, new AffineObdResponseConverter(0.1, -40.0), "Catalyst Temperature Bank 1, Sensor 2", "°C", ""),
        new ObdCommand("013F", "413F", 0, new AffineObdResponseConverter(0.1, -40.0), "Catalyst Temperature Bank 2, Sensor 2", "°C", ""),

        
        //ToDo: Implement bit mask converter
        new ObdCommand("0140", "4140", 0, new DefaultObdResponseConverter(),                "PIDs supported [41 - 60]", null, "Bit mask"),
        new ObdCommand("0141", "4141", 0, new AffineObdResponseConverter(1.0, 0.0),         "Monitor status this drive cycle", null, "Bit encoded"),
        new ObdCommand("0142", "4142", 0, new AffineObdResponseConverter(0.001, 0.0),       "Control module voltage", "V", ""),
        new ObdCommand("0143", "4143", 0, new AffineObdResponseConverter(100.0/255.0, 0.0), "Absolute load value", "%", ""),
        new ObdCommand("0144", "4144", 0, new AffineObdResponseConverter(2.0/65535.0, 0.0), "Command equivalence ratio", null, ""),
        new ObdCommand("0145", "4145", 0, new AffineObdResponseConverter(100.0/255, 0.0),   "Relative throttle position", "%", ""),
        new ObdCommand("0146", "4146", 0, new AffineObdResponseConverter(100.0/255, 0.0),   "Ambient air temperature", "°C", ""),
        new ObdCommand("0147", "4147", 0, new AffineObdResponseConverter(100.0/255, 0.0),   "Absolute throttle position B", "%", ""),
        new ObdCommand("0148", "4148", 0, new AffineObdResponseConverter(100.0/255, 0.0),   "Absolute throttle position C", "%", ""),
        new ObdCommand("0149", "4149", 0, new AffineObdResponseConverter(100.0/255, 0.0),   "Accelerator pedal position D", "%", ""),
        new ObdCommand("014A", "414A", 0, new AffineObdResponseConverter(100.0/255, 0.0),   "Accelerator pedal position E", "%", ""),
        new ObdCommand("014B", "414B", 0, new AffineObdResponseConverter(100.0/255, 0.0),   "Accelerator pedal position F", "%", ""),
        new ObdCommand("014C", "414C", 0, new AffineObdResponseConverter(100.0/255, 0.0),   "Commanded throttle actuator", "%", ""),
        new ObdCommand("014D", "414D", 0, new AffineObdResponseConverter(1.0, 0.0), "Time run with MIL on", "min", "Time run with malfunction indicator lamp (MIL) on"),
        new ObdCommand("014E", "414E", 0, new AffineObdResponseConverter(1.0, 0.0), "Time since trouble codes cleared", "min", ""),
        //ToDo: Implement quad affine converter
        new ObdCommand("014F", "414F", 0, new DefaultObdResponseConverter(), "Maximum value for equivalence ratio, oxygen sensor voltage, oxygen sensor current, and intake manifold absolute pressure", "{, V, mA, kPa}", "A, B, C, D*10"),

        //ToDo: Implement quad affine converter
        new ObdCommand("0150", "4150", 0, new DefaultObdResponseConverter(), "Maximum value for air flow rate from mass air flow sensor", "{g/s,,,}", "   A*10. B, C, and D are reserved for future use"),
        //ToDo: Implement fuel type converter
        new ObdCommand("0151", "4151", 0, new DefaultObdResponseConverter(), "Fuel Type", null, "enum"),
        new ObdCommand("0152", "4152", 0, new AffineObdResponseConverter(100.0/255.0, 0.0), "Ethanol fuel %", "%", ""),
        new ObdCommand("0153", "4153", 0, new AffineObdResponseConverter(0.005, 0.0), "Absolute evaporation system vapor pressure", "kPa", ""),
        new ObdCommand("0154", "4154", 0, new AffineObdResponseConverter(1.0, -32767.0), "Evaporation system vapor pressure", "Pa", ""),
        new ObdCommand("0155", "4155", 0, new DualAffineObdResponseConverter(100.0/128.0, -100.0, 100.0/128.0, -100.0), "Short term secondary oxygen sensor trim bank 1 and bank 3", "{%, %}", ""),
        new ObdCommand("0156", "4156", 0, new DualAffineObdResponseConverter(100.0/128.0, -100.0, 100.0/128.0, -100.0), "Long term secondary oxygen sensor trim bank 1 and bank 3", "{%, %}", ""),
        new ObdCommand("0157", "4157", 0, new DualAffineObdResponseConverter(100.0/128.0, -100.0, 100.0/128.0, -100.0), "Short term secondary oxygen sensor trim bank 2 and bank 4", "{%, %}", ""),
        new ObdCommand("0158", "4158", 0, new DualAffineObdResponseConverter(100.0/128.0, -100.0, 100.0/128.0, -100.0), "Long term secondary oxygen sensor trim bank 2 and bank 4", "{%, %}", ""),
        new ObdCommand("0159", "4159", 0, new AffineObdResponseConverter(10.0, 0.0), "Fuel rail pressure (absolute)", "kPa", ""),
        new ObdCommand("015A", "415A", 0, new AffineObdResponseConverter(100.0/255.0, 0.0), "Relative accelerator pedal position", "%", ""),
        new ObdCommand("015B", "415B", 0, new AffineObdResponseConverter(100.0/255.0, 0.0), "Hybrid battery pack remaining life", "%", ""),
        new ObdCommand("015C", "415C", 0, new AffineObdResponseConverter(1.0, -40.0), "Engine oil temperature", "UNIT", "°C"),
        new ObdCommand("015D", "415D", 0, new AffineObdResponseConverter(1.0/128.0, -210.0), "Fuel injection timing", "°", ""),
        new ObdCommand("015E", "415E", 0, new AffineObdResponseConverter(0.05, 0.0), "Engine fuel rate", "l/h", ""),
        //ToDo: Implement emission requirement converter
        new ObdCommand("015F", "415F", 0, new AffineObdResponseConverter(1.0, 0.0), "Emission requirements to which vehicle is designed", null, "Bit encoded"),


        //ToDo: Implement bit mask converter
        new ObdCommand("0160", "4160", 0, new DefaultObdResponseConverter(), "PIDs supported [61 - 80]", null, "Bit mask"),
        new ObdCommand("0161", "4161", 0, new AffineObdResponseConverter(1.0, 0.0), "NAME", "UNIT", ""),
        new ObdCommand("0162", "4162", 0, new AffineObdResponseConverter(1.0, 0.0), "NAME", "UNIT", ""),
        new ObdCommand("0163", "4163", 0, new AffineObdResponseConverter(1.0, 0.0), "NAME", "UNIT", ""),
        new ObdCommand("0164", "4164", 0, new AffineObdResponseConverter(1.0, 0.0), "NAME", "UNIT", ""),
        new ObdCommand("0165", "4165", 0, new AffineObdResponseConverter(1.0, 0.0), "NAME", "UNIT", ""),
        new ObdCommand("0166", "4166", 0, new AffineObdResponseConverter(1.0, 0.0), "NAME", "UNIT", ""),
        new ObdCommand("0167", "4167", 0, new AffineObdResponseConverter(1.0, 0.0), "NAME", "UNIT", ""),
        new ObdCommand("0168", "4168", 0, new AffineObdResponseConverter(1.0, 0.0), "NAME", "UNIT", ""),
        new ObdCommand("0169", "4169", 0, new AffineObdResponseConverter(1.0, 0.0), "NAME", "UNIT", ""),
        new ObdCommand("016A", "416A", 0, new AffineObdResponseConverter(1.0, 0.0), "NAME", "UNIT", ""),
        new ObdCommand("016B", "416B", 0, new AffineObdResponseConverter(1.0, 0.0), "NAME", "UNIT", ""),
        new ObdCommand("016C", "416C", 0, new AffineObdResponseConverter(1.0, 0.0), "NAME", "UNIT", ""),
        new ObdCommand("016D", "416D", 0, new AffineObdResponseConverter(1.0, 0.0), "NAME", "UNIT", ""),
        new ObdCommand("016E", "416E", 0, new AffineObdResponseConverter(1.0, 0.0), "NAME", "UNIT", ""),
        new ObdCommand("016F", "416F", 0, new AffineObdResponseConverter(1.0, 0.0), "NAME", "UNIT", ""),

        new ObdCommand("0170", "4170", 0, new AffineObdResponseConverter(1.0, 0.0), "NAME", "UNIT", ""),
        new ObdCommand("0171", "4171", 0, new AffineObdResponseConverter(1.0, 0.0), "NAME", "UNIT", ""),
        new ObdCommand("0172", "4172", 0, new AffineObdResponseConverter(1.0, 0.0), "NAME", "UNIT", ""),
        new ObdCommand("0173", "4173", 0, new AffineObdResponseConverter(1.0, 0.0), "NAME", "UNIT", ""),
        new ObdCommand("0174", "4174", 0, new AffineObdResponseConverter(1.0, 0.0), "NAME", "UNIT", ""),
        new ObdCommand("0175", "4175", 0, new AffineObdResponseConverter(1.0, 0.0), "NAME", "UNIT", ""),
        new ObdCommand("0176", "4176", 0, new AffineObdResponseConverter(1.0, 0.0), "NAME", "UNIT", ""),
        new ObdCommand("0177", "4177", 0, new AffineObdResponseConverter(1.0, 0.0), "NAME", "UNIT", ""),
        new ObdCommand("0178", "4178", 0, new AffineObdResponseConverter(1.0, 0.0), "NAME", "UNIT", ""),
        new ObdCommand("0179", "4179", 0, new AffineObdResponseConverter(1.0, 0.0), "NAME", "UNIT", ""),
        new ObdCommand("017A", "417A", 0, new AffineObdResponseConverter(1.0, 0.0), "NAME", "UNIT", ""),
        new ObdCommand("017B", "417B", 0, new AffineObdResponseConverter(1.0, 0.0), "NAME", "UNIT", ""),
        new ObdCommand("017C", "417C", 0, new AffineObdResponseConverter(1.0, 0.0), "NAME", "UNIT", ""),
        new ObdCommand("017D", "417D", 0, new AffineObdResponseConverter(1.0, 0.0), "NAME", "UNIT", ""),
        new ObdCommand("017E", "417E", 0, new AffineObdResponseConverter(1.0, 0.0), "NAME", "UNIT", ""),
        new ObdCommand("017F", "417F", 0, new AffineObdResponseConverter(1.0, 0.0), "NAME", "UNIT", ""),

        
        //ToDo: Implement bit mask converter
        new ObdCommand("0180", "4180", 0, new DefaultObdResponseConverter(), "PIDs supported [81 - A0]", null, "Bit mask"),
        new ObdCommand("0181", "4181", 0, new AffineObdResponseConverter(1.0, 0.0), "NAME", "UNIT", ""),
        new ObdCommand("0182", "4182", 0, new AffineObdResponseConverter(1.0, 0.0), "NAME", "UNIT", ""),
        new ObdCommand("0183", "4183", 0, new AffineObdResponseConverter(1.0, 0.0), "NAME", "UNIT", ""),
        new ObdCommand("0184", "4184", 0, new AffineObdResponseConverter(1.0, 0.0), "NAME", "UNIT", ""),
        new ObdCommand("0185", "4185", 0, new AffineObdResponseConverter(1.0, 0.0), "NAME", "UNIT", ""),
        new ObdCommand("0186", "4186", 0, new AffineObdResponseConverter(1.0, 0.0), "NAME", "UNIT", ""),
        new ObdCommand("0187", "4187", 0, new AffineObdResponseConverter(1.0, 0.0), "NAME", "UNIT", ""),

        //ToDo: Implement bit mask converter
        new ObdCommand("01A0", "41A0", 0, new DefaultObdResponseConverter(), "PIDs supported [A1 - C0]", null, "Bit mask"),

        //ToDo: Implement bit mask converter
        new ObdCommand("01C0", "41C0", 0, new DefaultObdResponseConverter(), "PIDs supported [C1 - E0]", null, "Bit mask"),

        //ToDo: Implement bit mask converter
        new ObdCommand("01E0", "41E0", 0, new DefaultObdResponseConverter(), "PIDs supported [E1 - FF]", null, "Bit mask")
    };
    
    private static Map<String, ObdCommand> obdCommandMap = initializeObdCommandMap();
    
    private static final Map<String, ObdCommand> initializeObdCommandMap() {
        HashMap<String, ObdCommand> map = new HashMap<String, ObdCommand>();
        for (ObdCommand cmd : DICTIONARY_ENTRIES_AT) {
            map.put(cmd.getCommand(), cmd);
        }
        for (ObdCommand cmd : DICTIONARY_ENTRIES_01) {
            map.put(cmd.getCommand(), cmd);
        }
        return map;
    }
    
    public static ObdCommand[] getMode01Commands() {
        return DICTIONARY_ENTRIES_01;
    }

    public static ObdCommand[] getATCommands() {
        return DICTIONARY_ENTRIES_AT;
    }
    
    public static Map<String, ObdCommand> getObdCommandMap() {
        return obdCommandMap;
    }
}
