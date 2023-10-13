package com.stopnewsletter.backend.blog;

import com.stopnewsletter.backend.catalog.source.SourceFacade;
import com.stopnewsletter.backend.catalog.source.SourceQueryRepository;
import com.stopnewsletter.backend.catalog.source.dto.SourceDto;
import com.stopnewsletter.backend.content.post.BlogAuthorQueryRepository;
import com.stopnewsletter.backend.content.post.BlogCategoryQueryRepository;
import com.stopnewsletter.backend.content.post.BlogTagQueryRepository;
import com.stopnewsletter.backend.content.post.PostQueryRepository;
import com.stopnewsletter.backend.content.post.dto.BlogCategoryDto;
import com.stopnewsletter.backend.content.post.dto.BlogTagDto;
import com.stopnewsletter.backend.content.post.dto.BlogAuthorDto;
import com.stopnewsletter.backend.content.post.dto.PostDto;
import com.stopnewsletter.backend.database.TenantContext;
import com.stopnewsletter.backend.scene.SqlSceneRepository;
import com.stopnewsletter.backend.source.SourceType;
import com.stopnewsletter.backend.wordpress.WordPressClient;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@Component
@Order( 3)
public class PostWarmUp implements ApplicationListener<ContextRefreshedEvent> {

    private final SqlSceneRepository scenes;
    private final SourceQueryRepository sources;
    private final BlogTagQueryRepository tags;
    private final BlogCategoryQueryRepository categories;
    private final BlogAuthorQueryRepository authors;
    private final PostQueryRepository posts;
    private final SourceFacade source;

    public PostWarmUp( final SqlSceneRepository scenes,
                       final BlogTagQueryRepository tags,
                       final BlogCategoryQueryRepository categories,
                       final BlogAuthorQueryRepository authors,
                       final SourceQueryRepository sources,
                       final PostQueryRepository posts,
                       final SourceFacade source) {
        this.scenes= scenes;
        this.sources= sources;
        this.tags= tags;
        this.categories= categories;
        this.authors= authors;
        this.posts= posts;
        this.source= source;
    }

    @Override
    public void onApplicationEvent( ContextRefreshedEvent event) {

        scenes.findAll().forEach( scene-> {
            TenantContext.setCatalogId( scene.getCode());
            sources.findAllByTypeAndActive( SourceType.WP, Boolean.TRUE)
                    .forEach( blog-> readBlog( scene.getCode(), WordPressClient.create( blog.getUrl()), blog));
        });

    }

    private void readBlog( String scene, WordPressClient wpc, SourceDto blog){

        TenantContext.setCatalogId( scene);
        Optional.ofNullable( blog.getUpdate())
            .map( wpc::getPosts)
            .orElse( wpc.getPosts())
            .filter( wpPost-> {
                TenantContext.setCatalogId( scene);
                if( posts.existsBySourceIdAndPostId( blog.getId(), wpPost.getId())){
                    if( blog.getUpdate().before( wpPost.getDate()))
                        source.update( blog.getId(), wpPost.getDate());
                    return false;
                }
                return true;
            })
            .flatMap( wpPost-> {
                TenantContext.setCatalogId( scene);

                var file= new Object(){ String extension="";};
                var wpMedia= wpc.getMedia( wpPost.getMedia())
                   .flatMap( wordPressMedia-> {
                       file.extension= wordPressMedia.getFileExtension();
                       return wpc.getImage( wordPressMedia.getUrl());
                   });

                var wpTags= Flux.fromIterable( wpPost.getTags())
                    .flatMap( tag-> {
                        return tags.findByIdAndBlogId( tag, blog.getId())
                            .map( Mono::just)
                                .orElse( wpc.getTag( tag)
                                            .map( wpTag-> BlogTagDto.create()
                                                .id( wpTag.getId())
                                                .blogId( blog.getId())
                                                .name( wpTag.getName())
                                            ));
                    }).collectList();

                var wpCategories= Flux.fromIterable( wpPost.getCategories())
                    .flatMap( category-> {
                        return categories.findByIdAndBlogId( category, blog.getId())
                            .map( Mono::just)
                                .orElse( wpc.getCategory( category)
                                            .map( wpCategory-> BlogCategoryDto.create()
                                                .id( wpCategory.getId())
                                                .blogId( blog.getId())
                                                .name( wpCategory.getName())
                                            ));
                    }).collectList();

                var wpAuthor= Optional.ofNullable( wpPost.getAuthor().getName())
                    .map( author-> Mono.just( (BlogAuthorDto)BlogAuthorDto.create()
                                                .id( wpPost.getAuthor().getId())
                                                .name( wpPost.getAuthor().getName())
                                                .blogId( blog.getId())))
                    .orElse( authors.findByIdAndBlogId( wpPost.getAuthor().getId(), blog.getId())
                        .map( Mono::just)
                        .orElse( wpc.getUser( wpPost.getAuthor().getId())
                                .map( wpUser-> BlogAuthorDto.create()
                                                .id( wpUser.getId())
                                                .blogId( blog.getId())
                                                .name( wpUser.getName())))
                        .onErrorResume( throwable -> {
                            return Mono.just( BlogAuthorDto.create()
                                                .id( wpPost.getAuthor().getId())
                                                .blogId( blog.getId())
                                                .name( "<< hidden name >>"));
                    }));

                return Mono.zip( wpTags, wpCategories, wpAuthor, wpMedia)
                    .map( wp-> {
                        TenantContext.setCatalogId( scene);
                        source.addPost( PostDto.create()
                                        .postId( new PostId( wpPost.getId(), blog.getId()))
                                        .blog( blog)
                                        .title( wpPost.getTitle())
                                        .date( wpPost.getDate())
                                        .tags( wp.getT1())
                                        .categories( wp.getT2())
                                        .author( List.of( wp.getT3())))
                                .ifPresent( post->  saveImage( wp.getT4(),
                                        blog.getId()+ "/"+ post.getId()+ file.extension)
                                        .subscribe());
                        return Mono.empty();
                    });
            })
            .subscribeOn( Schedulers.boundedElastic())
            .subscribe();
    }

    private Mono<Void> saveImage(byte[] imageBytes, String savePath) {
        Path filePath= new File( "D:/Dane/Stop/"+ savePath).toPath();

        return Mono.fromCallable(()-> {
            try {
                Files.createDirectories( filePath.getParent());
                Files.write( filePath, imageBytes);
            } catch (IOException e) {
                throw new RuntimeException( "Error while saving the file: " + e.getMessage());
            }
            return null;
        });
    }





}
