package com.bodybuilding.api.services.authors;

import com.bodybuilding.alertpersistence.entities.AlertType;
import com.bodybuilding.api.jackson.JacksonFactory;
import com.bodybuilding.content.model.dto.AuthorDTO;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
 * Wrapper Class for JSON encoded AuthorDTO objects
 * for conversion to and from JSON by REST API
 * Created by Ray.Wilkett on 6/4/2014.
 */
public class AuthorDTOs {
    private List<AuthorDTO> authorDTOs;
    private static Logger log = LoggerFactory.getLogger(AuthorDTOs.class);

    public AuthorDTOs(){

    }

    public AuthorDTOs(List<AuthorDTO> authorDTOs){
        this.authorDTOs = authorDTOs;
    }

    public List<AuthorDTO> getAuthorDTOs(){
        return this.authorDTOs;
    }

    public void setAuthorDTOs(List<AuthorDTO> authorDTOs){
        this.authorDTOs = authorDTOs;
    }

    public static AuthorDTOs valueOf(String json){
        ObjectMapper om = JacksonFactory.getInstance().createDefaultObjectMapper();
        try {
            List<AuthorDTO> authorDTOList = om.readValue(json, new TypeReference<List<AuthorDTO>>() {});
            return new AuthorDTOs(authorDTOList);
        } catch (IOException e) {
            log.warn("Could not convert json to list " + json, e);
        }
        return new AuthorDTOs();
    }
}
