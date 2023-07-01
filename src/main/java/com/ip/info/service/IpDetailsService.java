package com.ip.info.service;

import com.ip.info.model.entity.IpDetailsEntity;
import com.ip.info.utils.IpDetailsUtils;
import com.ip.info.webclient.IpDetailsWebclient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class IpDetailsService {

    @Autowired
    private IpDetailsUtils ipDetailsUtils;

    @Autowired
    private IpDetailsWebclient ipDetailsWebclient;

    public Mono<IpDetailsEntity> fetchDetails(String ip) {
        return (StringUtils.isNotBlank(ip) ?
                ipDetailsWebclient.getIpDetails(ip) :
                ipDetailsWebclient.getIp()
                        .map(myIp -> ipDetailsWebclient.getIpDetails(myIp)).flatMap(jsonNodeMono -> jsonNodeMono))
                .map(jsonNode -> ipDetailsUtils.convertJsonToIpDetailsEntity(jsonNode));


    }
}
