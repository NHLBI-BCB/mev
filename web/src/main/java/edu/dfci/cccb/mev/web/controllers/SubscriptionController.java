package edu.dfci.cccb.mev.web.controllers;

import edu.dfci.cccb.mev.web.domain.Subscriber;
import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.social.google.api.Google;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;

/**
 * Created by levk on 3/6/17.
 */
@RestController
@Scope("request")
@RequestMapping("/subscriber")
@Log4j
public class SubscriptionController {
    @Inject Environment environment;
    @Inject @Named ("subscriberEm" ) private EntityManager db;
    private @Inject Provider<Google> gPlus;
    private static final Set<String> ALLOWED = new HashSet<>(asList ("lev.v.kuznetsov@gmail.com", "apartensky@gmail.com"));

    @RequestMapping (method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void subscribe (@RequestBody Subscriber s) {
        db.getTransaction().begin();
        try {
            db.persist(s);
        }finally {
            db.getTransaction().commit();
        }
    }

    @RequestMapping (method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void unsubscribe (@RequestBody Subscriber s) {
        db.remove(db.find(Subscriber.class, s.email()));
    }

    @RequestMapping (method = RequestMethod.GET)
    public Collection<Subscriber> subscribers () {
        log.debug("Subscriber GET env"+environment);
        if (environment.getProperty("spring_profiles_active").contains("test")
            || environment.getProperty("spring_profiles_active").contains("local")
            || new HashSet<>(ALLOWED).removeAll(gPlus.get().plusOperations().getGoogleProfile().getEmailAddresses()))
            return db.createQuery("from " + Subscriber.class.getName()).getResultList();
        else throw new IllegalStateException("YOU BEEN A BAD MONKEY");
    }
}
