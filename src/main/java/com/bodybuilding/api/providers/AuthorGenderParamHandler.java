package com.bodybuilding.api.providers;

import com.bodybuilding.content.model.author.AuthorGender;
import org.apache.cxf.jaxrs.ext.ParameterHandler;

/**
 * Created by Ray.Wilkett on 6/10/2014.
 */
public class AuthorGenderParamHandler implements ParameterHandler<AuthorGender> {
    @Override
    public AuthorGender fromString(String s) {
       if(s == "MEN"){
           return AuthorGender.MEN;
       }

        if(s == "WOMEN"){
            return AuthorGender.WOMEN;
        }

        return AuthorGender.NONE;
    }
}
