package com.examples.cmdlinerunner.beans;

import org.springframework.stereotype.Component;

@Component
public class ApplicationExternalComponent {

    public String getComponentValue(){
        return "{ \"serviceId\": \"srv-90210\", \"name\": \"UserAuthenticationService\", \"version\": \"2.4.1\", \"status\": \"operational\", \"protocol\": \"HTTPS/REST\", \"endpoint\": \"https://example.com\", \"port\": 443, \"dependencies\": [ \"DatabaseService\", \"RedisCacheService\" ], \"metadata\": { \"environment\": \"production\", \"region\": \"us-east-1\", \"lastUpdated\": \"2026-11-06T08:00:00Z\" }}";
    }
}
