package com.server.mothercare.security.config;

import com.server.mothercare.lisners.*;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.event.spi.PostDeleteEventListener;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

@Component
public class HibernateListenerConfigurer {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Autowired
    PostInsertEventListener blogInsertEventListener;

    @Autowired
    PostDeleteEventListener blogDeleteEventListener;

    @Autowired
    PostUpdateEventListener blogUpdateEventListener;
    @PostConstruct
    protected void init() {
        SessionFactoryImpl sessionFactory = emf.unwrap(SessionFactoryImpl.class);
        EventListenerRegistry registry = sessionFactory.getServiceRegistry().getService(EventListenerRegistry.class);
        registry.getEventListenerGroup(EventType.POST_INSERT).appendListeners(blogInsertEventListener);
        registry.getEventListenerGroup(EventType.POST_UPDATE).appendListeners(blogUpdateEventListener);
        registry.getEventListenerGroup(EventType.POST_DELETE).appendListeners(blogDeleteEventListener);

    }
}