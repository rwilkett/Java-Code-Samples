package com.bodybuilding.api.services.authors;

import com.bodybuilding.api.jackson.JacksonFactory;
import com.bodybuilding.content.model.author.AuthorCategoryName;
import com.bodybuilding.content.model.author.AuthorGender;
import com.bodybuilding.content.model.dto.AuthorDTO;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * Created by Ray.Wilkett on 6/10/2014.
 */
public class AuthorCategoryNameParam {
    private static Logger log = LoggerFactory.getLogger(AuthorDTOs.class);
    private AuthorCategoryName authorCategoryName;

    public AuthorCategoryNameParam(){

    }

    public AuthorCategoryNameParam(AuthorCategoryName authorCategoryName){
        this.authorCategoryName = authorCategoryName;
    }

    public void setAuthorCategoryName(AuthorCategoryName value){
        this.authorCategoryName = value;
    }

    public AuthorCategoryName getAuthorCategoryName(){
        return this.authorCategoryName;
    }



    public static AuthorCategoryNameParam valueOf(String json){
        ObjectMapper om = JacksonFactory.getInstance().createDefaultObjectMapper();
        try {
            AuthorCategoryName acn = om.readValue(json, new TypeReference<AuthorCategoryName>() {});
            return new AuthorCategoryNameParam(acn);
        } catch (IOException e) {
            log.warn("Could not convert json to AuthorCategoryNameParam " + json, e);
        }
        return new AuthorCategoryNameParam();
    }
}
