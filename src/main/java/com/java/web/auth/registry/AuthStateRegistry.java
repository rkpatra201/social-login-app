package com.java.web.auth.registry;

import com.java.model.Provider;

import java.util.HashMap;
import java.util.Map;

public class AuthStateRegistry {

    private static final AuthStateRegistry INSTANCE = new AuthStateRegistry();
    private static final Map<String, Provider> stateProviderMap = new HashMap<>();

    private AuthStateRegistry()
    {
        // no-code
    }
    public static final AuthStateRegistry getInstance()
    {
        return INSTANCE;
    }

    public Provider getProvider(String state)
    {
        return stateProviderMap.get(state);
    }

    public void addState(String state, Provider provider)
    {
        stateProviderMap.put(state,provider);
    }
}
