package com.stopnewsletter.backend.database;

import com.stopnewsletter.backend.scene.SceneService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@Order( 0)
public class DatabaseWarmUp implements ApplicationListener<ContextRefreshedEvent> {

    private final SceneService scenes;
    public DatabaseWarmUp( SceneService scenes) {
        this.scenes= scenes;
        System.err.println( "Uruchamiam się...");
    }

    @Override
    public void onApplicationEvent( @NonNull ContextRefreshedEvent event) {

        scenes.createScene( "pl", "Poland");
        scenes.createScene( "uk", "United Kingdom");
    }
}
