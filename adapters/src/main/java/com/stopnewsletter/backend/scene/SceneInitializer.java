package com.stopnewsletter.backend.scene;

import com.stopnewsletter.backend.shared.DataLoader;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class SceneInitializer {

    private final DataLoader loader;
    private final SceneService scenes;

    public void init() {

        for( String line: loader.readLines( "data/scenes.txt")) {
            var fields= loader.getFields( line);
            if( fields[0].startsWith( "---"))
                continue;

            if( scenes.existsByCode( fields[0]))
                continue;

            System.err.println( "UUID: "+ UUID.fromString( fields[2]));

            scenes.createScenes( new Scene()
                    .setCode( fields[0])
                    .setName( fields[1])
                    .setNotion( UUID.fromString( fields[2])));
        }
    }
}
