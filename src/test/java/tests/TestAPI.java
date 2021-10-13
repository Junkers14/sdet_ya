package tests;

import com.google.gson.Gson;
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

        Content getResult = Request.Get("https://reqres.in/api/users")
                .execute().returnContent();
        String strFromJSON = getResult.asString();
        Gson gson = new Gson();
        Data getJSONData = gson.fromJson(strFromJSON,Data.class);
        JSONParser(getJSONData, ConfigurationProperties.getProperty("user1"), ConfigurationProperties.getProperty("email1") );


        getResult = Request.Get("https://reqres.in/api/users?page=2")
                .execute().returnContent();
        strFromJSON = getResult.asString();
        getJSONData = gson.fromJson(strFromJSON,Data.class);
        JSONParser(getJSONData, ConfigurationProperties.getProperty("user2"), ConfigurationProperties.getProperty("email2") );

    }

    public void JSONParser(Data gsonData, String usr, String email){

        for (UserList data : gsonData.data) {
            systemLog.loggerTestOutput(data.id + " | " + data.email + " | " + data.first_name + " | " + data.last_name + " | " + data.avatar);
            if (
                    ((data.first_name+" "+data.last_name).equals(usr)) &&
                            (data.email.equals(email))
            ) systemLog.loggerTestOutputWarning("The User " + usr + " has an email address " + email);

        }
    }



}
