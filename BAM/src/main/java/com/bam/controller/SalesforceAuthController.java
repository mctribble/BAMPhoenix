package com.bam.controller;

import com.bam.bean.BamUser;
import com.bam.bean.Force;
import com.bam.service.BamUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/rest/api/v1/Salesforce/")
public class SalesforceAuthController {

    @Autowired
    private Force force;

    @Autowired
    private BamUserService bamUserService;

    @Autowired
    private OAuth2RestTemplate restTemplate;

    /**
     * Gets the user's Salesforce information and populates the Bam database with that information.
     * @param principal
     * @return The BamUser object.
     */
    @RequestMapping(value="salesforceSync", method=RequestMethod.GET)
    public ResponseEntity<BamUser> salesforceSync(OAuth2Authentication principal) {
        BamUser bamUser = force.getCurrentBamUser(principal);
        bamUserService.addOrUpdateUser(bamUser);
        return ResponseEntity.ok(bamUser);
    }

    @RequestMapping(value="logout", method=RequestMethod.GET)
    public ModelAndView logout(OAuth2Authentication principal, HttpServletRequest req, HttpServletResponse resp) {

        String salesforceUrl = "https://revature--int1.cs17.my.salesforce.com";
        String revokeUrl = salesforceUrl + "/services/oauth2/revoke";
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("token", restTemplate.getAccessToken().toString());

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(revokeUrl, request, String.class);


            principal.getUserAuthentication().setAuthenticated(false);

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            if (auth != null) {
                new SecurityContextLogoutHandler().logout(req, resp, auth);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return new ModelAndView("redirect:" + salesforceUrl + "/secur/logout.jsp");
    }

}
