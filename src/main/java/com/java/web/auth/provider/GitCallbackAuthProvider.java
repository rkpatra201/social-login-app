package com.java.web.auth.provider;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.model.AuthMetadata;
import com.java.model.Provider;
import com.java.model.User;
import com.java.utils.AppUtils;
import com.java.utils.JsonUtils;
import com.java.utils.ServletUtils;
import com.java.web.auth.registry.AuthMetadataRegistry;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GitCallbackAuthProvider extends SocialCallBackAuthProvider {
    @Override
    public void authenticate(ServletRequest request, ServletResponse response) throws IOException {
        super.setProvider(Provider.GITHUB);
        super.authenticate(request, response);
    }

    @Override
    protected void retrieveAccessToken() throws IOException {
        AuthMetadata authMetadata = AuthMetadataRegistry.getInstance().getAuthMetadata(Provider.GITHUB);
        String code = getHttpServletRequest().getParameter("code");
        String state = getHttpServletRequest().getParameter("state");
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(authMetadata.getAccessTokenUrl())
                .queryParam("redirect_uri", authMetadata.getCallbackUrl())
                .queryParam("client_id", authMetadata.getClientId())
                .queryParam("client_secret", authMetadata.getClientSecret())
                .queryParam("code", code)
                .queryParam("state", state);
        RestTemplate restTemplate = new RestTemplate();
        String accessTokenInfo = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, null, String.class).getBody();
        super.setAccessToken(accessTokenInfo);
    }

    @Override
    protected void retrieveSocialUserMeta() throws IOException {

        String accessToken = null;
        if (getAccessToken().contains("access_token=")) {
            accessToken = getAccessToken().split("=")[1].split("&")[0];
        }

        AuthMetadata authMetadata = AuthMetadataRegistry.getInstance().getAuthMetadata(Provider.GITHUB);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(authMetadata.getUserInfoUrl());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        String userMetadata = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, httpEntity, String.class).getBody();
        super.setSocialUserMeta(userMetadata);
    }

    @Override
    protected void saveSocialUserMeta() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode =mapper.readTree(getSocialUserMeta());

        String userName = jsonNode.get("login").asText();
        String email = jsonNode.get("email").asText();
        User user = getUserService().findUserByEmail(email);
        Map<Provider, String> meta = new HashMap<>();
        meta.put(Provider.GITHUB, getSocialUserMeta());
        if (user != null) {
            // update metadata
            user.setName(userName);
            user.setEmail(email);
            user.getMeta().putAll(meta);
            getUserService().updateUser(user, user.getId());
        } else {
            user = new User();
            user.setName(userName);
            user.setEmail(email);
            user.setPhone(AppUtils.generateString(10));
            user.setPassword(AppUtils.generateString(6));
            user.getMeta().putAll(meta);
            getUserService().registerUser(user);
            user = getUserService().findUserByEmail(email); // this will have the id now.
        }
        super.setUser(user);// to be used for session creation.
    }
}
