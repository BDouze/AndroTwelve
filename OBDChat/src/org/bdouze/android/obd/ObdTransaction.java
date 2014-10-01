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
