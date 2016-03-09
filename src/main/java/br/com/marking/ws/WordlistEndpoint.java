/*
 *
 */
package br.com.marking.ws;

import br.com.marking.entity.Word;
import br.com.marking.rest.Wordlist;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Singleton;
import javax.enterprise.event.Observes;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * @author Marcos Pinheiro
 * 
 */
@Singleton
@ServerEndpoint("/word")
public class WordlistEndpoint {
    
    private static final ConcurrentMap<String, Session> SESSIONS = new ConcurrentHashMap<>();
    
    @OnOpen
    public void open(Session session) {
        SESSIONS.put(session.getId(), session);
        System.out.println("session opened " + session.getId());
    }
    
    @OnClose
    public void close(Session session) {
        SESSIONS.remove(session.getId());
        System.out.println("session closed " + session.getId());
    }
    
    public void onPut(@Observes @Wordlist Word word) {
        System.out.println("receive");
        
        SESSIONS.forEach((id, session) -> {
            try {
                session.getBasicRemote().sendText(word.getWord());
            } 
            catch (IOException ex) {
                Logger.getLogger(WordlistEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            }
        });        
    }
}
