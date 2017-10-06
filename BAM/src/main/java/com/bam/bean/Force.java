package com.bam.bean;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Force {

    private static final String REST_VERSION = "40.0";

    @Bean
    private OAuth2RestTemplate oAuth2RestTemplate(OAuth2ProtectedResourceDetails detail, OAuth2ClientContext context) {
        return new OAuth2RestTemplate(detail, context);
    }

    @Autowired
    private OAuth2RestTemplate restTemplate;

    private static String restUrl(OAuth2Authentication principal, String url) {
        HashMap<String, Object> details = (HashMap<String, Object>) principal.getUserAuthentication().getDetails();
        HashMap<String, String> urls = (HashMap<String, String>) details.get("urls");
        return urls.get(url).replace("{version}", REST_VERSION);
    }

    public BamUser getCurrentBamUser(OAuth2Authentication auth) {
        HashMap<String, String> details = (HashMap<String, String>) auth.getUserAuthentication().getDetails();
        String query = "SELECT Id, Name, FirstName, LastName, Email," +
                "UserRole.Id, UserRole.Name FROM User WHERE Id = '"
                + details.get("user_id") + "'";

        String response = executeSalesforceQuery(auth, query);

        return parseSalesforceQueryResponse(response).get(0);
    }

    private String executeSalesforceQuery(OAuth2Authentication auth, String query) {
        String url = restUrl(auth, "query") + "?q={q}";

        Map<String, String> params = new HashMap<>();
        params.put("q", query);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class, params);
        return responseEntity.getBody();
    }

//      this.userId = userId;
//		this.fName = fName;
//		this.mName = mName;
//		this.lName = lName;
//		this.email = email;
//		this.pwd = pwd;
//		this.role = role;
//		this.batch = batch;
//		this.phone = phone;
//		this.phone2 = phone2;
//		this.skype = skype;
//		this.pwd2 = pwd2;
//      assignForceID = AssignForceID;
    private List<BamUser> parseSalesforceQueryResponse(String response) {
        List<BamUser> bamUsers = new ArrayList<>();
        JsonObject object = new Gson().fromJson(response, JsonElement.class).getAsJsonObject();
        JsonArray arr = object.getAsJsonArray("records");

        arr.forEach(jsonElement -> {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            BamUser bamUser = new BamUser();
//            bamUser.setUserId(jsonObject.get("Id").getAsInt());
            bamUser.setfName(jsonObject.get("FirstName").getAsString());
            bamUser.setlName(jsonObject.get("LastName").getAsString());
            bamUser.setEmail(jsonObject.get("Email").getAsString());

            JsonObject jsonRole = jsonObject.get("UserRole").getAsJsonObject();
            String roleName = jsonRole.get("Name").getAsString();
            int roleId = 1;
            if (roleName.equalsIgnoreCase("Trainers")) {
                roleId = 2;
            }
            bamUser.setRole(roleId);
            bamUsers.add(bamUser);
        });

        restTemplate.getAccessToken();

        return bamUsers;
    }
}
