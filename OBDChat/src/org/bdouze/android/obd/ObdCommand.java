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
    
    public String cleanupResponse(final String response) {
        String cleanResponse = response;
        
        String header = getResponseHeader();
        if (null != cleanResponse 
                && cleanResponse.length() > 0 
                && cleanResponse.startsWith(">")) {
            cleanResponse = cleanResponse.substring(1);
        }
        if (null != header && header.length() > 0 
                && null != cleanResponse 
                && cleanResponse.length() > 0 
                && cleanResponse.startsWith(header)) {
            cleanResponse = cleanResponse.substring(header.length());
        }
        if (null != cleanResponse 
                && cleanResponse.length() > 0 
                && cleanResponse.endsWith(">")) {
            cleanResponse = cleanResponse.substring(0, cleanResponse.length() -1);
        }
        cleanResponse = cleanResponse.trim();

        return cleanResponse;
    }
    
    
}
