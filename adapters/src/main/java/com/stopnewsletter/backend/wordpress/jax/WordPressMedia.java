package com.stopnewsletter.backend.wordpress.jax;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
public class WordPressMedia {

    private String url;
    private String fileExtension;

    @JsonProperty( "media_details")
    public void setMediaDetails( Object map) throws JsonProcessingException {
        var mapper= new ObjectMapper();
        MediaDetails wordPressMedia= mapper.readValue( mapper.writeValueAsString( map), MediaDetails.class);

        fileExtension= wordPressMedia.getExtension();
        if( wordPressMedia.sizes.containsKey( "medium"))
            url= wordPressMedia.sizes.get( "medium").getUrl();
    }

    @Setter
    @JsonIgnoreProperties( ignoreUnknown= true)
    public static class MediaDetails {
        private String file;
        private Map<String, Sizes> sizes;

        public String getExtension( ){
            int lastIndexOf= file.lastIndexOf(".");
            return lastIndexOf==-1? "": file.substring( lastIndexOf);
        }

        @Setter
        @JsonIgnoreProperties( ignoreUnknown= true)
        public static class Sizes {

            @JsonProperty( "source_url")
            private String url;



            public String getUrl() {
                url.replaceFirst( "/", "");
                return url;
            }
        }
    }
}
