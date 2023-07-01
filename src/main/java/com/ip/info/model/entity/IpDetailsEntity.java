package com.ip.info.model.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(as = ImmutableIpDetailsEntity.class)
public interface IpDetailsEntity {
    String ip();
    String city();
    String region();
    String country();
    String loc();
    String org();
    String postal();
    String timezone();
    String readme();
}
