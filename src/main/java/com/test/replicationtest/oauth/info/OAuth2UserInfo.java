package com.test.replicationtest.oauth.info;

import java.util.Map;

public interface OAuth2UserInfo {

    String getProvider();
    String getProviderId();
    String getName();
    String getEmail();

    Map<String, Object> getAttributes();
}
