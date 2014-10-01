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
