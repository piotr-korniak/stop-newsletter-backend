package com.stopnewsletter.backend.source;

public enum SourceType {
    ST {    // Spotify
        @Override public <T> T accept( SourceTypeVisitor<T> visitor) {
            return visitor.visitSt();
        }
    },
    UD {    // Udemy
        @Override public <T> T accept( SourceTypeVisitor<T> visitor) {
            return visitor.visitUd();
        }
    },
    WP {    // WordPress
        @Override public <T> T accept( SourceTypeVisitor<T> visitor) {
            return visitor.visitWp();
        }
    },
    YT {    // YouTube
        @Override public <T> T accept(SourceTypeVisitor<T> visitor) {
            return visitor.visitYt();
        }
    };

    public abstract <T> T accept( SourceTypeVisitor<T> visitor);

    public interface SourceTypeVisitor<T> {
        T visitSt();
        T visitUd();
        T visitWp();
        T visitYt();
    }
}
