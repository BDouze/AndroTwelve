/**
 * 
 */
package org.bdouze.android.obd;

/**
 * @author fabriceb
 *
 */
public class ObdTransaction {

    /**
     * 
     */
    public ObdTransaction(String command, IObdResponseConverter formater) {
        this.command = command;
        this.formater = formater;
        this.response = null;
    }

    private final String command;
    private String response;
    private IObdResponseConverter formater;
    
    public String getResponse() {
        return response;
    }
    
    public String getTextResponse() {
        return formater.convertToString(response);
    }

    public double getNumericalResponse() {
        return formater.convertToDouble(response);
    }
    
    public void setResponse(String response) {
        this.response = response;
    }
    
    public String getCommand() {
        return command;
    }
}
