package com.stopnewsletter.backend.wordpress;

import com.stopnewsletter.backend.wordpress.jax.WordPressAttribute;
import com.stopnewsletter.backend.wordpress.jax.WordPressMedia;
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
    public Mono<WordPressAttribute> getTag( int tag) {
        return webClient
                .get()
                .uri( "wp-json/wp/v2/tags/"+ tag)
                .retrieve()
                .bodyToMono( WordPressAttribute.class);
    }


    public Mono<WordPressAttribute> getCategory(int category) {
        return webClient
                .get()
                .uri( "wp-json/wp/v2/categories/"+ category)
                .retrieve()
                .bodyToMono( WordPressAttribute.class);
    }

    public Mono<WordPressAttribute> getUser( int user) {
        return webClient
                .get()
                .uri( "wp-json/wp/v2/users/"+ user)
                .retrieve()
                .bodyToMono( WordPressAttribute.class);
    }

    public Mono<WordPressMedia> getMedia( int media) {
        return webClient
                .get()
                .uri( "wp-json/wp/v2/media/"+ media)
                .retrieve()
                .bodyToMono( WordPressMedia.class);
    }

    public Mono<byte[]> getImage(String path) {
        return webClient
                .get()
                .uri( path)
                .retrieve()
                .bodyToMono( byte[].class);
    }
}
