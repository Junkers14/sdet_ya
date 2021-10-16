package tests;

import com.google.gson.Gson;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import config.ConfigurationProperties;
import log_workers.SystemLog;

import static org.testng.Assert.assertThrows;


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
    String api_url;
    List<String> cfgUsrName = new ArrayList<String>();
    List<String> cfgUsrEmail = new ArrayList<String>();
    int totalUsers;


    @BeforeMethod
    public void getConfig(){


        try {
        api_url = ConfigurationProperties.getProperty("api_url");
        totalUsers = Integer.parseInt(ConfigurationProperties.getProperty("totalUsers"));
            for (int i=1; i<=totalUsers; i++){
                cfgUsrName.add(ConfigurationProperties.getProperty("user"+i));
                cfgUsrEmail.add(ConfigurationProperties.getProperty("email"+i));
            }
        } catch (Exception e)
        {
          systemLog.loggerAPIOutputWarning(e.toString());
        }


    }

    @Test
    public void testAPICase() throws IOException {
        systemLog = new SystemLog();
        Data getData = getRequest(api_url);
        if (getData!=null)
        for (int currentPage = getData.page; currentPage <= getData.total_pages; currentPage++)
             {
                 for (int i=0; i<cfgUsrName.size(); i++){
                     try{
                         jsonParser(getRequest(api_url+"?page="+currentPage),
                                 cfgUsrName.get(i),
                                 cfgUsrEmail.get(i));
                     }catch (Exception e)
                     {
                         systemLog.loggerAPIOutputWarning(e.toString());
                     }

                 }

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
                /**
                 *  проверка в лог
                 */
                if (((userListData.first_name + " " + userListData.last_name).equals(usr)) &&
                        (userListData.email.equals(email))) systemLog.loggerAPIOutputWarning(
                        "The User " + usr + " has an email address " + email);

                /**
                 *  проверка через assertEquals
                 */
                if ((userListData.first_name + " " + userListData.last_name).equals(usr))
                {
                    Assert.assertEquals(email,userListData.email);
                }
            }
    }



}
