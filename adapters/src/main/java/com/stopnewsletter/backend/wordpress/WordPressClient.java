package com.stopnewsletter.backend.wordpress;

import com.stopnewsletter.backend.wordpress.jax.WordPressCategory;
import com.stopnewsletter.backend.wordpress.jax.WordPressPost;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WordPressClient {
    private final WebClient webClient;

    public static final SimpleDateFormat RRMDTX=
            new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss");

    private WordPressClient( String baseUrl) {
        this.webClient= WebClient.create( baseUrl);
    }

    public static WordPressClient create( String baseUrl) {
        return new WordPressClient( baseUrl);
    }

    public Flux<WordPressPost> getPosts() {
        return webClient
                .get()
                .uri( "wp-json/wp/v2/posts")
                .retrieve()
                .bodyToFlux( WordPressPost.class);
    }

    public Flux<WordPressPost> getPosts( Date date) {
        return webClient
                .get()
                .uri( uriBuilder -> uriBuilder
                        .path( "wp-json/wp/v2/posts")
                        .queryParam( "after", RRMDTX.format( date))
                        .queryParam( "order", "asc")
                        .build())
                .retrieve()
                .bodyToFlux( WordPressPost.class);
    }

    public Mono<WordPressCategory> getCategory( int category) {
        return webClient
                .get()
                .uri( "wp-json/wp/v2/categories/"+ category)
                .retrieve()
                .bodyToMono( WordPressCategory.class);
    }

    public Mono<WordPressCategory> getUser( int user) {
        return webClient
                .get()
                .uri( "wp-json/wp/v2/users/"+ user)
                .retrieve()
                .bodyToMono( WordPressCategory.class);
    }
}
