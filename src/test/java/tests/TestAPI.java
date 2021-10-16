package tests;

import com.google.gson.Gson;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.List;

import config.ConfigurationProperties;
import log_workers.SystemLog;


public class TestAPI {
    private static SystemLog systemLog;
    static class Data{
        public int page;
        public int per_page;
        public int total;
        public int total_pages;
        public List<UserList> data;
    }

    static class UserList {
        public int id;
        public String email;
        public String first_name;
        public String last_name;
        public String avatar;
    }


    @Test
    public void testAPICase() throws IOException {
        systemLog = new SystemLog();
        Data getData = getRequest(ConfigurationProperties.getProperty("api_url"));

        if (getData!=null)
        for (int currentPage = getData.page; currentPage <= getData.total_pages; currentPage++)
             {
            jsonParser(getRequest(ConfigurationProperties.getProperty("api_url")+"?page="+currentPage),
                    ConfigurationProperties.getProperty("user"+currentPage),
                    ConfigurationProperties.getProperty("email"+currentPage));
             }
        }

    public Data getRequest (String url) throws IOException{
        try {
            Content getResult = Request.Get(url)
                    .execute().returnContent();
            String strFromJSON = getResult.asString();
            Gson gson = new Gson();
            return gson.fromJson(strFromJSON,Data.class);
        } catch (ClientProtocolException e) {
            systemLog.loggerAPIOutputWarning(e.toString());
        }
        return null;
    }



    public void jsonParser(Data gsonData, String usr, String email){
            for (UserList userListData : gsonData.data) {
                if (((userListData.first_name + " " + userListData.last_name).equals(usr)) &&
                        (userListData.email.equals(email))) systemLog.loggerAPIOutputWarning(
                        "The User " + usr + " has an email address " + email);
            }
    }



}
