package com.stopnewsletter.backend.shared;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.ResourceLoader;

import java.nio.charset.StandardCharsets;

@AllArgsConstructor
public class DataLoader {

    private final ResourceLoader loader;

    @SneakyThrows
    public String[] readLines( String path) {
        return new String( loader
                .getResource("classpath:"+ path)
                .getInputStream().readAllBytes(), StandardCharsets.UTF_8)
                .split( "; *["+ System.lineSeparator()+ "]*");
    }

    public String[] getFields( String row){
        String[] result= row.split( "(?<!\\\\), *");

        for( int n=0; n<result.length; n++)	// nie zmienia jak nie trzeba
            result[n]= result[n].replaceAll( "\\\\,", ",");
        return result;
    }


}
