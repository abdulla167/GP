package com.server.mothercare.lisners;


import com.server.mothercare.entities.post.Blog;
import com.server.mothercare.entities.post.Comment;
import com.server.mothercare.entities.post.Like;
import com.server.mothercare.services.SseService;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class BlogInsertEventListenerImpl implements  PostInsertEventListener {

//    public static final BlogInsertEventListenerImpl INSTANCE =
//            new BlogInsertEventListenerImpl();

    @Autowired
    private SseService sseService;


    @Override
    public  void onPostInsert(PostInsertEvent postInsertEvent) {
        final Object entity = postInsertEvent.getEntity();

        if (entity instanceof Blog) {
            sseService.process(entity, "Blog", "insert");
        }
        else if (entity instanceof Comment) {
            sseService.process(entity, "Comment", "insert");
        }
        else if (entity instanceof Like) {
            sseService.process(entity, "Like", "insert");
        }
    }

    @Override
    public boolean requiresPostCommitHanding(EntityPersister entityPersister) {
        return false;
    }


}
