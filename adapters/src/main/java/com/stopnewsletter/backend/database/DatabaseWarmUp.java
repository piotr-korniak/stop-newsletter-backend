package com.stopnewsletter.backend.database;

import com.stopnewsletter.backend.scene.SceneInitializer;
import com.stopnewsletter.backend.scene.SceneService;
import com.stopnewsletter.backend.shared.DataLoader;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ResourceLoader;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@Order( 0)
public class DatabaseWarmUp implements ApplicationListener<ContextRefreshedEvent> {

    private final SceneInitializer scenes;

    public DatabaseWarmUp( final ResourceLoader loader,
                           final SceneService scenes) {

        this.scenes= new SceneInitializer( new DataLoader( loader), scenes);
        System.err.println( "Uruchamiam się...");
    }

    @Override
    public void onApplicationEvent( @NonNull ContextRefreshedEvent event) {
        scenes.init();
    }
}
