package com.test.replicationtest.oauth.info;

public interface OAuth2UserInfo {

    String getProvider();
    String getProviderId();
    String getName();
    String getEmail();

}
