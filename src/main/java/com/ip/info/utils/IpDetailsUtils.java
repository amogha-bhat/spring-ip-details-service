package com.ip.info.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ip.info.model.entity.IpDetailsEntity;
import org.springframework.stereotype.Component;

@Component
public class IpDetailsUtils {

    private ObjectMapper objectMapper = new ObjectMapper();

    public IpDetailsEntity convertJsonToIpDetailsEntity(JsonNode jsonNode) {
        return objectMapper.convertValue(jsonNode, IpDetailsEntity.class);
    }

    public JsonNode convertIpDetailsEntityToJson(IpDetailsEntity ipDetailsEntity) {
        return objectMapper.convertValue(ipDetailsEntity, JsonNode.class);
    }
}
