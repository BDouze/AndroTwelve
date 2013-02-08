package org.bdouze.android.signalqualitytracker;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;

public abstract class OpenCellIdCellLocator extends
        AsyncTask<CellInfo, Integer, String> {

    public abstract void cellLocated(String location);

    @Override
    protected String doInBackground(CellInfo... params) {
        String location = null;

        int paramCount = params.length;
        if (paramCount > 0) {
            CellInfo cellInfo = params[0];

            String urlStr = "http://www.opencellid.org/cell/get" +
                    "?mcc=" + cellInfo.getMobileCountryCode() +
                    "&mnc=" + cellInfo.getMobileNetworkCode() +
                    "&cellid=" + cellInfo.getCellId() +
                    // "&lac=" + cellInfo.getLocalAreaCode() +
                    // "&fmt=xml";
                    "&fmt=txt";

            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(urlStr);
            try {
                HttpResponse response = client.execute(request);
                location = EntityUtils.toString(response.getEntity());
            } catch (Exception e) {
                location = e.toString();
            }
        }

        return location;
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        // TODO
    }

    @Override
    protected void onPostExecute(String result) {
        cellLocated(result);
    }

}
