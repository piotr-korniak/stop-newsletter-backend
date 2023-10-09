package com.stopnewsletter.backend.content.post.dto;

import com.stopnewsletter.backend.blog.PostId;
import com.stopnewsletter.backend.catalog.source.dto.SourceDto;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

public interface PostDto {
    static Proxy create() {
        return new Proxy();
    }

    public PostId getId();
    public SourceDto getBlog();
    public List<BlogCategoryDto> getCategories();
   public String getTitle();
    public List<BlogAuthorDto> getAuthors();
    public Date getDate();

    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements PostDto {

        private PostId postId;
        private SourceDto blog;
        private List<BlogCategoryDto> categories;
        private String title;
        private List<BlogAuthorDto> author;
        private Date date;

        @Override public PostId getId() {
            return postId;
        }

        @Override public SourceDto getBlog() {
            return blog;
        }

        @Override public List<BlogCategoryDto> getCategories() {
            return categories;
        }

        @Override public String getTitle() {
            return title;
        }

        @Override public List<BlogAuthorDto> getAuthors() {
            return author;
        }

        @Override public Date getDate() {
            return date;
        }

    }
}
