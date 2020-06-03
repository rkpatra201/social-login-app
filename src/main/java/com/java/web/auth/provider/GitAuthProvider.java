package com.java.web.auth.provider;

import com.java.model.AuthMetadata;
import com.java.model.Provider;
import com.java.model.User;
import com.java.utils.ServletUtils;
import com.java.web.auth.registry.AuthMetadataRegistry;
import com.java.web.auth.registry.AuthStateRegistry;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.UUID;

public class GitAuthProvider implements IAuthProvider{

    @Override
    public void authenticate(ServletRequest request, ServletResponse response) throws IOException {
        AuthMetadata authMetadata = AuthMetadataRegistry.getInstance().getAuthMetadata(Provider.GITHUB);
        if(authMetadata.getCodeUrl() == null)
        {
            String codeUrlPattern = authMetadata.getAuthorizationUrl()+"?client_id="+authMetadata.getClientId()+"&redirect_uri="+authMetadata.getCallbackUrl()+"&scope=user+repo"+"&state=%s";
            authMetadata.setCodeUrl(codeUrlPattern);
        }

        if(authMetadata.getCodeUrl()!=null)
        {
            String state = UUID.randomUUID().toString();
            String codeUrl = String.format(authMetadata.getCodeUrl(), state);
            AuthStateRegistry.getInstance().addState(state,Provider.GITHUB);
            ServletUtils.redirect(response,codeUrl);
        }
    }
}
