package com.server.mothercare.lisners;

import com.server.mothercare.entities.Like;
import com.server.mothercare.entities.post.Blog;
import com.server.mothercare.entities.post.Comment;
import com.server.mothercare.services.SseService;
import org.hibernate.event.spi.PostDeleteEvent;
import org.hibernate.event.spi.PostDeleteEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BlogDeleteEventListenerImpl implements PostDeleteEventListener {

//    public static final BlogDeleteEventListenerImpl INSTANCE =
//            new BlogDeleteEventListenerImpl();

    @Autowired
    SseService sseService;
    @Override
    public void onPostDelete(PostDeleteEvent postDeleteEvent) {

        final Object entity = postDeleteEvent.getEntity();

        if (entity instanceof Blog) {
            sseService.process(entity, "Blog", "delete");
        }
        else if (entity instanceof Comment) {
            sseService.process(entity, "Comment", "delete");
        }
        else if (entity instanceof Like) {
            sseService.process(entity, "Like", "delete");
        }
    }

    @Override
    public boolean requiresPostCommitHanding(EntityPersister entityPersister) {
        return false;
    }


}
