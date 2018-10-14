import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class YandexTranslate {
    private static final String URL = "https://translate.yandex.net/api/v1.5/tr.json/translate";
    private static final String API_KEY = "trnsl.1.1.20180825T180449Z.b377b409bf49076b.264dccf68baace33e4144c7309d143acab05261d";
    private HttpClient client = HttpClients.createDefault();
    private HttpPost httpPost = new HttpPost(URL);

    public String translate(String text) throws IOException {
        List<NameValuePair> params = new ArrayList<NameValuePair>(3);
        params.add(new BasicNameValuePair("lang", "en-ru"));
        params.add(new BasicNameValuePair("key", API_KEY));
        params.add(new BasicNameValuePair("text", text));
        httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

        HttpResponse response = client.execute(httpPost);

        String result;
        if (response.getStatusLine().getStatusCode() == 200) {
            HttpEntity entity = response.getEntity();
            JSONObject jsonObject = new JSONObject(EntityUtils.toString(entity));
            String translateText = jsonObject.get("text").toString();
            int i1 = translateText.indexOf("[");
            int i2 = translateText.indexOf("]");
            result = translateText.substring(i1 + 2, i2 - 1);
        } else if (response.getStatusLine().getStatusCode() == 401){
            result = "Invalid API key";
        } else if (response.getStatusLine().getStatusCode() == 402){
            result = "Blocked API key";
        } else if (response.getStatusLine().getStatusCode() == 404){
            result = "Exceeded the daily limit on the amount of translated text";
        } else if (response.getStatusLine().getStatusCode() == 413){
            result = "Exceeded the maximum text size";
        } else if (response.getStatusLine().getStatusCode() == 422){
            result = "The text cannot be translated";
        } else if (response.getStatusLine().getStatusCode() == 501){
            result = "The specified translation direction is not supported";
        } else {
            result = "Unknown error";
        }

        return result;
    }
}
