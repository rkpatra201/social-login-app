package com.java.web.auth.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import com.java.model.AuthMetadata;
import com.java.model.Provider;
import com.java.utils.JsonUtils;

import java.util.HashMap;
import java.util.Map;

public class AuthMetadataRegistry {

    private static final String AUTH_METADATA_FILE_PATH = "/usr/local/content/resources/auth-metadata.json";
    private static final AuthMetadataRegistry INSTANCE = new AuthMetadataRegistry();

    private static Map<Provider, AuthMetadata> authMetadataMap = new HashMap<>();

    static {
        loadAuthMetadataMap();
    }

    private static void loadAuthMetadataMap()
    {
        TypeReference<HashMap<Provider, AuthMetadata>> typeRef
                = new TypeReference<HashMap<Provider, AuthMetadata>>() {};
         authMetadataMap.putAll(JsonUtils.readFile(AUTH_METADATA_FILE_PATH, typeRef));
    }

    private AuthMetadataRegistry()
    {
        // no-code
    }

    public static AuthMetadataRegistry getInstance()
    {
        return INSTANCE;
    }

    public AuthMetadata getAuthMetadata(Provider provider)
    {
        return authMetadataMap.get(provider);
    }
}
