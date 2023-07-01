package com.ip.info.webclient;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.text.MessageFormat;
import java.util.Map;
import java.util.function.Consumer;

@Component
public class IpDetailsWebclient {

    private static final String API_HOST = "apiHost";
    private static final String URI = "uri";
    private static final String GET_IP_API_KEY = "get_ip";
    private static final String GET_IP_API_DETAILS_KEY = "get_ip_details";

    @Value("#{${apiInfoMap:null}}")
    private Map<String, Map<String, String>> apiInfoMap;

    @Value("#{${apiHeaderMap:null}}")
    private Map<String, Map<String, String>> apiHeaderMap;

    @Autowired
    private WebClient webClient;

    private String getUrl(String apiKey) {
        return apiInfoMap.get(apiKey).get(API_HOST);
    }

    private String getUri(String apiKey, Object... args) {
        return args != null ?
                MessageFormat.format(apiInfoMap.get(apiKey).get(URI), args) :
                apiInfoMap.get(apiKey).get(URI);
    }

    private java.net.URI constructPath(String url, String path, MultiValueMap<String, String> queryStringMap) {
        return UriComponentsBuilder.fromHttpUrl(url).path(path).queryParams(queryStringMap).build().toUri();
    }

    private Consumer<HttpHeaders> getRequestHeaders(String apiKey) {
        Map<String, String> headers = (apiHeaderMap != null && apiHeaderMap.get(apiKey) != null) ?
                apiHeaderMap.get(apiKey) :
                Map.of(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return httpHeaders -> headers.forEach(httpHeaders::add);
    }

    public Mono<String> getIp() {
        return webClient.get()
                .uri(uriBuilder -> constructPath(getUrl(GET_IP_API_KEY), getUri(GET_IP_API_KEY), null))
                .headers(getRequestHeaders(GET_IP_API_KEY))
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(String.class));
    }

    public Mono<JsonNode> getIpDetails(String ip) {
        return webClient.get()
                .uri(uriBuilder -> constructPath(getUrl(GET_IP_API_DETAILS_KEY), getUri(GET_IP_API_DETAILS_KEY, ip), null))
                .headers(getRequestHeaders(GET_IP_API_DETAILS_KEY))
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(JsonNode.class));
    }

}
