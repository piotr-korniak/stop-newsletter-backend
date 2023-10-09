package com.stopnewsletter.backend.notion;

import com.stopnewsletter.backend.notion.dto.NotionList;
import com.stopnewsletter.backend.notion.dto.NotionObject;
import com.stopnewsletter.backend.notion.dto.NotionSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Component
public class NotionClient {

    private final String version;
    private final String authorization;
    private final WebClient webClient;

    public NotionClient( @Value( "${notion.baseUrl}") String baseUrl,
                         @Value( "${notion.version}") String version,
                         @Value( "${notion.authorization}") String authorization) {
       this.webClient= WebClient.create( "https://"+ baseUrl);
       this.version= version;
       this.authorization= "secret_"+ authorization;
    }

    public List<NotionObject> getBlockChildren( UUID blockId) {
        return webClient
                .get()
                .uri( "/blocks/"+ blockId+ "/children")
                .headers( httpHeaders-> {
                    httpHeaders.add( "Notion-Version", version);
                    httpHeaders.add( "Authorization", authorization);})
                .retrieve()
                .bodyToMono( NotionList.class)
                .block()
                .getResult();
    }

    public List<NotionSource> postDatabaseQuery( UUID blockId) {
        return webClient
                .post()
                .uri( "/databases/"+ blockId+ "/query")
                .headers( httpHeaders-> {
                    httpHeaders.add( "Notion-Version", version);
                    httpHeaders.add( "Authorization", authorization);})
                .retrieve()
                .bodyToMono( SourceList.class)
                .block()
                .getResult();
    }

}
