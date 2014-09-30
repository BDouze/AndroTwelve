/**
 * 
 */
package org.bdouze.android.obd;

/**
 * @author fabriceb
 *
 */
public final class DefaultObdResponseConverter implements IObdResponseConverter {

    /**
     * 
     */
    private static final long serialVersionUID = -6572613021090668092L;

    /**
     * 
     */
    public DefaultObdResponseConverter() {
    }

    /* (non-Javadoc)
     * @see org.bdouze.android.obd.IObdResponseConverter#convertToString(java.lang.String)
     */
    @Override
    public String convertToString(String response) {
        return response;
    }

    /* (non-Javadoc)
     * @see org.bdouze.android.obd.IObdResponseConverter#convertToDouble(java.lang.String)
     */
    @Override
    public double convertToDouble(String response) {
        return Double.NaN;
    }

    /* (non-Javadoc)
     * @see org.bdouze.android.obd.IObdResponseConverter#convertToDoubleArray(java.lang.String)
     */
    @Override
    public double[] convertToDoubleArray(String response) {
        return null;
    }

    /* (non-Javadoc)
     * @see org.bdouze.android.obd.IObdResponseConverter#isNumericalsupported()
     */
    public boolean isNumericalsupported() {
        return false;
    }

    /* (non-Javadoc)
     * @see org.bdouze.android.obd.IObdResponseConverter#isNumericalArray()
     */
    @Override
    public boolean isNumericalArray() {
        return false;
    }
    
    
}
