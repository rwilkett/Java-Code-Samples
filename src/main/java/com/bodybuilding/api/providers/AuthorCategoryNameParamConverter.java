package com.bodybuilding.api.providers;

import com.bodybuilding.api.jackson.JacksonFactory;
import com.bodybuilding.content.model.author.AuthorCategoryName;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import org.codehaus.jackson.map.ObjectMapper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * Created by Ray.Wilkett on 6/10/2014.
 */
public class AuthorCategoryNameParamConverter implements ParamConverter<AuthorCategoryName> {


    @Override
    public AuthorCategoryName fromString(String value) throws IllegalArgumentException {
        AuthorCategoryName authorCategoryName = AuthorCategoryName.valueOf(value);
        return authorCategoryName;
    }

    @Override
    public String toString(AuthorCategoryName value) throws IllegalArgumentException {
        return value.toString();
    }
}
