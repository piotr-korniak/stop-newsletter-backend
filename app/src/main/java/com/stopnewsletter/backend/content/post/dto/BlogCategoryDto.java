package com.stopnewsletter.backend.content.post.dto;

import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

public interface BlogCategoryDto {
    static Proxy create() {
        return new Proxy();
    }

    public int getId();
    public UUID getBlogId();
    public String getName();



    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements BlogCategoryDto {

        private int id;
        private UUID blogId;
        private String name;

        @Override
        public int getId() {
            return id;
        }

        @Override
        public UUID getBlogId() {
            return blogId;
        }

        @Override public String getName() {
            return name;
        }
    }
}
