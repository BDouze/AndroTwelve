/**
 * 
 */
package org.bdouze.android.obd;

import android.util.Log;

/**
 * @author fabriceb
 *
 * Affine conversion: y = a*x + b 
 */
public final class AffineObdResponseConverter implements IObdResponseConverter {

    private static final String TAG = "AffineObdResponseConverter";
    private static final boolean D = true;
    
    private final double a;
    private final double b;
    
    /**
     * 
     */
    public AffineObdResponseConverter(double a, double b) {
        super();
        this.a = a;
        this.b = b;
    }


    /* (non-Javadoc)
     * @see org.bdouze.android.obd.IObdResponseConverter#convertToString(java.lang.String)
     */
    @Override
    public String convertToString(String response) {
        return Double.toString(convertToDouble(response)) ;
    }

    /* (non-Javadoc)
     * @see org.bdouze.android.obd.IObdResponseConverter#convertToDouble(java.lang.String)
     */
    @Override
    public double convertToDouble(String response) {
        double y = Double.NaN;
        
        if (null != response) {
            try {
                long x = Long.parseLong(response, 16);
                y = a*x + b;
            } catch (NumberFormatException e) {
                if(D) Log.e(TAG, "Failed to convert OBD response value to number : " + response, e);
            }
        }

        return y;
    }

    /* (non-Javadoc)
     * @see org.bdouze.android.obd.IObdResponseConverter#convertToDoubleArray(java.lang.String)
     */
    @Override
    public double[] convertToDoubleArray(String response) {
        double[] res = new double[1];
        res[0] = convertToDouble(response);
        return res;
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
