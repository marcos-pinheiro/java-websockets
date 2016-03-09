/*
 *
 */
package br.com.marking.rest;

import br.com.marking.entity.Word;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Any;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 * @author Marcos Pinheiro
 * 
 */
@Stateless
@Path("/words")
public class WorlistResource {
    
    @Inject @Wordlist
    private Event<Word> event;
    
    @GET
    public Response put(@QueryParam("param") String param) {
        event.fire(new Word(param));
        return Response.ok(param).build();
    }    
}
