package com.bam.controller;

import com.bam.bean.BamUser;
import com.bam.bean.Force;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SalesforceAuthController {

    @Autowired
    private Force force;

    @Autowired
    private OAuth2RestTemplate restTemplate;

    @RequestMapping(path="/test")
    public ResponseEntity<String> test(OAuth2Authentication principal) {
//        String token = restTemplate.getAccessToken().toString();
//        Authentication auth = principal.getUserAuthentication();

        return ResponseEntity.ok(principal.toString());
    }

    @RequestMapping(path="/userget", method = RequestMethod.GET)
    public ResponseEntity<String> accounts(OAuth2Authentication principal) {
        System.out.println("Called userget");
        BamUser bu = null;
        try {
            bu = force.getCurrentBamUser(principal);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("bu=" + bu);
        return ResponseEntity.ok(bu == null ? "null" : bu.toString());
    }
}
