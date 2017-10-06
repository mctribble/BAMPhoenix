package com.bam.bean;

import com.google.gson.*;
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
    private static final String ROLE_TRAINER = "00Ei0000000ccV0EAI";

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
        String query = "SELECT Id, Name, FirstName, LastName, Email, Phone, MobilePhone, " +
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

    private List<BamUser> parseSalesforceQueryResponse(String response) {
        List<BamUser> bamUsers = new ArrayList<>();
        JsonObject object = new Gson().fromJson(response, JsonElement.class).getAsJsonObject();
        JsonArray arr = object.getAsJsonArray("records");

        arr.forEach(jsonElement -> {
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            BamUser bamUser = new BamUser();

            bamUser.setfName(jsonObject.get("FirstName").getAsString());
            bamUser.setlName(jsonObject.get("LastName").getAsString());
            bamUser.setEmail(jsonObject.get("Email").getAsString());

            JsonElement phone = jsonObject.get("Phone");
            bamUser.setPhone(phone instanceof JsonNull ? null : phone.getAsString());

            JsonElement mobilePhone = jsonObject.get("MobilePhone");
            bamUser.setPhone2(mobilePhone instanceof JsonNull ? null : mobilePhone.getAsString());

            JsonObject jsonRole = jsonObject.get("UserRole").getAsJsonObject();
            String roleName = jsonRole.get("Id").getAsString();
            int roleId = 1;
            if (roleName.equalsIgnoreCase(ROLE_TRAINER)) {
                roleId = 2;
            }

            bamUser.setRole(roleId);
            bamUsers.add(bamUser);
        });

        restTemplate.getAccessToken();

        return bamUsers;
    }
}
