package com.stopnewsletter.backend.notion;

import com.stopnewsletter.backend.catalog.source.SourceFacade;
import com.stopnewsletter.backend.catalog.source.dto.SourceDto;
import com.stopnewsletter.backend.database.TenantContext;
import com.stopnewsletter.backend.notion.dto.NotionObject;
import com.stopnewsletter.backend.notion.dto.NotionSource;
import com.stopnewsletter.backend.scene.Scene;
import com.stopnewsletter.backend.scene.SqlSceneRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order( 1)
public class NotionWarmUp implements ApplicationListener<ContextRefreshedEvent> {

    private final NotionClient notion;
    private final SqlSceneRepository scenes;
    private final SourceFacade facade;

    public NotionWarmUp( final NotionClient notion,
                         final SqlSceneRepository scenes,
                         final SourceFacade facade){
        this.notion= notion;
        this.scenes= scenes;
        this.facade= facade;
    }

    @Override
    public void onApplicationEvent( ContextRefreshedEvent event) {

        for( Scene scene: scenes.findAll()){
            TenantContext.setCatalogId( scene.getCode());

            for( NotionObject block: notion.getBlockChildren( scene.getNotion())){
                if( !block.getType().isType( NotionType.CD))
                    continue;   // only databases

                for( NotionSource source: notion.postDatabaseQuery( block.getId())){

                    facade.save( SourceDto.create()
                            .id( source.getId())
                            .type( source.getType())
                            .name( source.getName())
                            .url( source.getUrl())
                            .active( source.getActive()));
                }
            }
        }


    }
}
