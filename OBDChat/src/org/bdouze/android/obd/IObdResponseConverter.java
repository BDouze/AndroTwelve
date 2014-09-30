/**
 * 
 */
package org.bdouze.android.obd;

import java.io.Serializable;

/**
 * @author fabriceb
 *
 */
public interface IObdResponseConverter extends Serializable{
    public String convertToString(String response);
    public double convertToDouble(String response);
    public double[] convertToDoubleArray(String response);
    public boolean isNumericalsupported();
    public boolean isNumericalArray();
}
