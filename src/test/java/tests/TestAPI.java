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
    class Data{
        public int page;
        public int per_page;
        public int total;
        public int total_pages;
        public List<UserList> data;
    }

    class UserList {
        public int id;
        public String email;
        public String first_name;
        public String last_name;
        public String avatar;
    }


    @Test
    public void testAPICase() throws IOException {
        systemLog = new SystemLog();

        jsonParser(getRequest("https://reqres.in/api/users"),
                ConfigurationProperties.getProperty("user1"),
                ConfigurationProperties.getProperty("email1") );

        jsonParser(getRequest("https://reqres.in/api/users?page=2"),
                ConfigurationProperties.getProperty("user2"),
                ConfigurationProperties.getProperty("email2") );
    }

    public Data getRequest (String url) throws IOException{
        try {
            Content getResult = Request.Get(url)
                    .execute().returnContent();
            String strFromJSON = getResult.asString();
            Gson gson = new Gson();
            return gson.fromJson(strFromJSON,Data.class);
        } catch (ClientProtocolException e) {
            systemLog.loggerTestOutputWarning(e.toString());
        }
        return null;
    }


    public void jsonParser(Data gsonData, String usr, String email){

        if (gsonData!=null) {
            for (UserList data : gsonData.data) {
                systemLog.loggerTestOutput(data.id + " | " + data.email +
                        " | " + data.first_name + " | " + data.last_name + " | " + data.avatar);
                if (((data.first_name + " " + data.last_name).equals(usr)) &&
                        (data.email.equals(email))) systemLog.loggerTestOutputWarning(
                        "The User " + usr + " has an email address " + email);
            }
        }
        else{
            systemLog.loggerTestOutputWarning("JSON DATA ERROR!");
        }
    }



}
