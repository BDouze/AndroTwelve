package org.bdouze.android.signalqualitytracker;

import java.util.Locale;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParser;

import android.os.AsyncTask;
import android.util.Xml;

public abstract class OpenCellIdCellLocator extends
        AsyncTask<CellInfo, Integer, String> {

    public abstract void cellLocated(String location);

    @Override
    protected String doInBackground(CellInfo... params) {
        String location = null;

        int paramCount = params.length;
        if (paramCount > 0) {
            CellInfo cellInfo = params[0];

            String urlStr = String
                    .format(Locale.US,
                            "http://www.opencellid.org/cell/get?mcc=%1$d&mnc=%2$d&cellid=%4$d&lac=%3$d&fmt=xml",
                            cellInfo.getMobileCountryCode(),
                            cellInfo.getMobileNetworkCode(),
                            cellInfo.getLocalAreaCode(),
                            cellInfo.getCellId());

            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(urlStr);
            try {
                HttpResponse response = client.execute(request);
                // location = EntityUtils.toString(response.getEntity());

                /* sample OpenCellID cell/get? response:
                <rsp stat="ok">
                    <cell lat="43.5588383671461" mcc="208" lon="3.9505932404736" cellId="" nbSamples="27" mnc="20" lac="20121" range="50000"/>
                </rsp>                 
                */

                // Tutorial on XML parsing on android:
                // http://developer.android.com/training/basics/network-ops/xml.html

                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES,
                        false);
                parser.setInput(response.getEntity().getContent(), null);
                parser.nextTag();
                parser.require(XmlPullParser.START_TAG, null, "rsp");
                String res = parser.getAttributeValue(null, "stat");
                if (res.equalsIgnoreCase("ok")) {
                    parser.nextTag();
                    parser.require(XmlPullParser.START_TAG, null, "cell");
                    String lat = parser.getAttributeValue(null, "lat");
                    // String mcc = parser.getAttributeValue(null, "mcc");
                    String lon = parser.getAttributeValue(null, "lon");
                    String cellId = parser.getAttributeValue(null, "cellId");
                    // String nbSamples = parser.getAttributeValue(null,
                    // "nbSamples");
                    // String mnc = parser.getAttributeValue(null, "mnc");
                    // String lac = parser.getAttributeValue(null, "lac");
                    // String range = parser.getAttributeValue(null, "range");

                    StringBuilder sb = new StringBuilder();
                    sb.append(lat);
                    sb.append(',');
                    sb.append(lon);
                    if (null == cellId || 0 == cellId.length()) {
                        sb.append(" (LAC only)");
                    }

                    location = sb.toString();
                }

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
