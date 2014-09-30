/**
 * 
 */
package org.bdouze.android.obd;

import java.io.Serializable;

/**
 * @author fabriceb
 *
 */
public final class ObdCommand implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4141817998316362837L;

    /**
     * 
     */
    private final String command;
    private final String responseHeader;
    private final int expectedResponseFrameCount;
    private final IObdResponseConverter converter;
    private final String name;
    private final String unit;
    private final String description;
    
    /**
     * 
     */
    public ObdCommand(
            String command, 
            String responseHeader,
            int expectedResponseFrameCount,
            IObdResponseConverter converter,
            String name, 
            String unit,
            String description) {
        super();
        this.command = command;
        this.responseHeader = responseHeader;
        this.expectedResponseFrameCount = expectedResponseFrameCount;
        if (null != converter) {
            this.converter = converter;
        } else {
            this.converter = new DefaultObdResponseConverter();
        }
        this.name = name;
        this.unit = unit;
        this.description = description;
    }

    public String getCommand() {
        return command;
    }
    public String getResponseHeader() {
        return responseHeader;
    }
    public int getExpectedResponseFrameCount() {
        return expectedResponseFrameCount;
    }
    public IObdResponseConverter getConverter() {
        return converter;
    }
    public String getName() {
        return name;
    }
    public String getUnit() {
        return unit;
    }
    public String getDescription() {
        return description;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
    
}
