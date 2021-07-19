package com.server.mothercare.lisners;

import com.server.mothercare.entities.post.Like;
import com.server.mothercare.entities.post.Blog;
import com.server.mothercare.entities.post.Comment;
import com.server.mothercare.services.SseService;
import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BlogUpdateEventListenerImpl implements PostUpdateEventListener {
//    public static final BlogUpdateEventListenerImpl INSTANCE =
//            new BlogUpdateEventListenerImpl();

    @Autowired
    SseService sseService;
    @Override
    public void onPostUpdate(PostUpdateEvent postUpdateEvent) {
        final Object entity = postUpdateEvent.getEntity();

        if (entity instanceof Blog) {
            sseService.process(entity, "Blog", "update");
        }
        else if (entity instanceof Comment) {
            sseService.process(entity, "Comment", "update");
        }
        else if (entity instanceof Like) {
            sseService.process(entity, "Like", "update");
        }
    }

    @Override
    public boolean requiresPostCommitHanding(EntityPersister entityPersister) {
        return false;
    }


}
