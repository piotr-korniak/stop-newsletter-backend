package com.stopnewsletter.backend.blog;

import com.stopnewsletter.backend.catalog.source.SourceFacade;
import com.stopnewsletter.backend.catalog.source.SourceQueryRepository;
import com.stopnewsletter.backend.catalog.source.dto.SourceDto;
import com.stopnewsletter.backend.content.post.BlogAuthorQueryRepository;
import com.stopnewsletter.backend.content.post.BlogCategoryQueryRepository;
import com.stopnewsletter.backend.content.post.PostQueryRepository;
import com.stopnewsletter.backend.content.post.dto.BlogAuthorDto;
import com.stopnewsletter.backend.content.post.dto.BlogCategoryDto;
import com.stopnewsletter.backend.content.post.dto.PostDto;
import com.stopnewsletter.backend.database.TenantContext;
import com.stopnewsletter.backend.scene.SqlSceneRepository;
import com.stopnewsletter.backend.source.SourceType;
import com.stopnewsletter.backend.wordpress.WordPressClient;
import com.stopnewsletter.backend.wordpress.jax.WordPressPost;
import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Optional;

@Component
@Order( 3)
public class PostWarmUp implements ApplicationListener<ContextRefreshedEvent> {

    private final SqlSceneRepository scenes;
    private final SourceQueryRepository sources;
    private final BlogCategoryQueryRepository categories;
    private final BlogAuthorQueryRepository authors;
    private final PostQueryRepository posts;
    private final SourceFacade source;

    public PostWarmUp(final SqlSceneRepository scenes,
                      final SourceQueryRepository sources,
                      final BlogCategoryQueryRepository categories,
                      final BlogAuthorQueryRepository authors,
                      final PostQueryRepository posts,
                      final SourceFacade source) {
        this.scenes= scenes;
        this.sources= sources;
        this.categories= categories;
        this.authors= authors;
        this.posts= posts;
        this.source= source;
    }
    private void readBlog( String scene, WordPressClient wpc, SourceDto blog){

        System.err.println( "readBlog: "+ blog.getUrl());

        TenantContext.setCatalogId( scene);

        Optional.ofNullable( blog.getUpdate())
            .map( date-> wpc.getPosts( date))
            .orElse( wpc.getPosts())
            .filter( wpPost-> {
                TenantContext.setCatalogId( scene);
                if( posts.existsBySourceIdAndPostId( blog.getId(), wpPost.getId())){
                    if( blog.getUpdate().before( blog.getUpdate()))
                        source.update( blog.getId(), blog.getUpdate());
                    return false;
                }
                return true;
            })
            .flatMap( wpPost-> {
                System.err.println("readPost: " + wpPost.getTitle());
                TenantContext.setCatalogId( scene);

                var wpCategories= Flux.fromIterable( wpPost.getCategories())
                        .flatMap( category-> {
                            //TenantContext.setCatalogId(scene);
                            return categories.findByIdAndBlogId(category, blog.getId())
                                    .map( Mono::just)
                                    .orElse(wpc.getCategory(category)
                                            .map(wpCategory -> BlogCategoryDto.create()
                                                    .id(wpCategory.getId())
                                                    .blogId(blog.getId())
                                                    .name(wpCategory.getName())
                                            ));
                        }).collectList();

                var wpAuthor= Mono.just(wpPost.getAuthor())
                        .flatMap(author -> {
                            //TenantContext.setCatalogId(scene);
                            return authors.findByIdAndBlogId(author, blog.getId())
                                    .map(Mono::just)
                                    .orElse(wpc.getUser(author)
                                            .map(wpUser -> BlogAuthorDto.create()
                                                    .id(wpUser.getId())
                                                    .blogId(blog.getId())
                                                    .name(wpUser.getName())));
                        });

                return Mono.zip( wpCategories, wpAuthor)
                        .map( wp-> {
                            //System.err.println("Author: " + wp.getT2().getName());
                            TenantContext.setCatalogId( scene);
                            savePost(blog, wpPost, wp.getT1(), wp.getT2());
                            return Mono.empty();
                        });
            })
            .subscribeOn(Schedulers.boundedElastic())
            .subscribe();

    }
    @Override
    public void onApplicationEvent( ContextRefreshedEvent event) {

        scenes.findAll().forEach( scene-> {
            TenantContext.setCatalogId( scene.getCode());
            sources.findAllByTypeAndActive( SourceType.WP, Boolean.TRUE).forEach( blog-> {

                readBlog( scene.getCode(), WordPressClient.create( blog.getUrl()), blog);
            });
        });
/*
       for( Scene scene: scenes.findAll()) {
           TenantContext.setCatalogId( scene.getCode());
           for( SourceDto blog: sources.findTypeByActive( SourceType.WP)) { // only WP
               if( !blog.isActive())
                   continue;

               var wordPress= WordPressClient.create( blog.getUrl());
               wordPress.getPosts()
                   .flatMap( wpPost-> {
                       var wpCategories= Flux.fromIterable( wpPost.getCategories())
                               .flatMap( category->{
                                   TenantContext.setCatalogId( scene.getCode());
                                   return categories.findByIdAndBlogId( category, blog.getId())
                                           .map( Mono::just)
                                           .orElse( wordPress.getCategory( category)
                                                   .map( wpCategory-> BlogCategoryDto.create()
                                                           .id( wpCategory.getId())
                                                           .blogId( blog.getId())
                                                           .name( wpCategory.getName())
                                                   ));
                                   }).collectList();

                       var wpAuthor= wordPress.getUser( wpPost.getAuthor())
                               .map( wpUser-> BlogAuthorDto.create()
                                       .id( wpUser.getId())
                                       .blogId( blog.getId())
                                       .name( wpUser.getName()));

                       return Mono.zip( Mono.just( wpPost), wpCategories, wpAuthor).map( wp-> {

                           System.err.println( "Author: "+ wp.getT3().getName());

                           return savePost( blog, wp.getT1(), wp.getT2(), wp.getT3());

                       });

                       return Mono.zip( Mono.just( wpPost), wpCategories, (post, categories)-> {
                                    TenantContext.setCatalogId( scene.getCode());
                                    return posts.save( PostDto.create()
                                            .postId( new PostId( post.getId(), blog.getId()))
                                            .blog( blog)
                                            .title( post.getTitle())
                                            .categories( categories));
                               });
                   }).subscribe();

           }
       }*/
    }



    @Transactional
    public void savePost( SourceDto blog,
                          WordPressPost wpPost,
                          List<BlogCategoryDto> blogCategories,
                          BlogAuthorDto blogAuthor) {

        source.addPost( PostDto.create()
                .postId( new PostId( wpPost.getId(), blog.getId()))
                .blog( blog)
                .title( wpPost.getTitle())
                .date( wpPost.getDate())
                .categories( blogCategories)
                .author( List.of( blogAuthor)));
    }


}
