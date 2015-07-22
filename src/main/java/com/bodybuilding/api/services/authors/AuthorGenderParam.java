package com.bodybuilding.api.services.authors;

import com.bodybuilding.api.jackson.JacksonFactory;
import com.bodybuilding.content.model.author.AuthorCategoryName;
import com.bodybuilding.content.model.author.AuthorGender;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by Ray.Wilkett on 6/11/2014.
 */
public class AuthorGenderParam {
    private static Logger log = LoggerFactory.getLogger(AuthorDTOs.class);
    private AuthorGender authorGender;

    public AuthorGenderParam(){

    }

    public AuthorGenderParam(AuthorGender authorGender){
        this.authorGender = authorGender;
    }

    public void setAuthorGender(AuthorGender value){
        this.authorGender = value;
    }

    public AuthorGender getAuthorGender(){
        return this.authorGender;
    }

    public static AuthorGenderParam valueOf(String json){
        ObjectMapper om = JacksonFactory.getInstance().createDefaultObjectMapper();
        try {
            AuthorGender g = om.readValue(json, new TypeReference<AuthorGender>() {});
            return new AuthorGenderParam(g);
        } catch (IOException e) {
            log.warn("Could not convert json to AuthorGender " + json, e);
        }
        return new AuthorGenderParam();
    }
}
