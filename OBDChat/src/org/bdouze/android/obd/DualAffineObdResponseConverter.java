/**
 * 
 */
package org.bdouze.android.obd;

import android.util.Log;

/**
 * @author fabriceb
 *
 * Dual Affine conversion: {y1, y2} = {a1*x1 + b1, a2*x2 + b2} 
 */
public final class DualAffineObdResponseConverter implements IObdResponseConverter {

    private static final String TAG = "DualAffineObdResponseConverter";
    private static final boolean D = true;
    
    private final double a1;
    private final double b1;
    private final double a2;
    private final double b2;
    
    /**
     * 
     */
    public DualAffineObdResponseConverter(double a1, double b1, double a2, double b2) {
        super();
        this.a1 = a1;
        this.b1 = b1;
        this.a2 = a2;
        this.b2 = b2;
    }


    /* (non-Javadoc)
     * @see org.bdouze.android.obd.IObdResponseConverter#convertToString(java.lang.String)
     */
    @Override
    public String convertToString(String response) {
        double[] y = convertToDoubleArray(response);
        return "{" + Double.toString(y[0]) + ", " + Double.toString(y[1]) + "}" ;
    }

    /* (non-Javadoc)
     * @see org.bdouze.android.obd.IObdResponseConverter#convertToDouble(java.lang.String)
     */
    @Override
    public double convertToDouble(String response) {
        double[] y = convertToDoubleArray(response);
        return y[0];        
    }

    /* (non-Javadoc)
     * @see org.bdouze.android.obd.IObdResponseConverter#convertToDoubleArray(java.lang.String)
     */
    @Override
    public double[] convertToDoubleArray(String response) {
        double[] y = new double[2];
        y[0] = Double.NaN;
        y[1] = Double.NaN;
        String r1 = null;
        String r2 = null;
        
        int l = response.length();
        
        if (l>0) {
            if (0 == (l & 0x1))  {
                r1 = response.substring(0, l / 2);
                r2 = response.substring(l / 2, l);

                if (null != r1) {
                    try {
                        long x = Long.parseLong(r1, 16);
                        y[0] = a1*x + b1;
                    } catch (NumberFormatException e) {
                        if(D) Log.e(TAG, "Failed to convert first half of OBD response value to number : " + response);
                    }
                } else {
                    if(D) Log.e(TAG, "Cannot convert empty first half of OBD response to number : " + response);
                }
        
                if (null != r2) {
                    try {
                        long x = Long.parseLong(r2, 16);
                        y[1] = a2*x + b2;
                    } catch (NumberFormatException e) {
                        if(D) Log.e(TAG, "Failed to convert second half of OBD response value to number : " + response, e);
                    }
                } else {
                    if(D) Log.e(TAG, "Cannot convert empty second half of OBD response to number : " + response);
                }
          } else {
              if(D) Log.e(TAG, "Failed to split OBD response value into 2 parts of equal size : " + response);              
          }          
        } else {
            if(D) Log.e(TAG, "Cannot convert empty OBD response to number : " + response);
        }
        
        return y;
    }
    
    /* (non-Javadoc)
     * @see org.bdouze.android.obd.IObdResponseConverter#isNumericalsupported()
     */
    public boolean isNumericalsupported() {
        return true;
    }

    /* (non-Javadoc)
     * @see org.bdouze.android.obd.IObdResponseConverter#isNumericalArray()
     */
    @Override
    public boolean isNumericalArray() {
        return false;
    }
}
