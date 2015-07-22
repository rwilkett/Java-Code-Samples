package com.bodybuilding.api.providers;

import com.bodybuilding.content.model.author.AuthorCategoryName;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * Created by Ray.Wilkett on 6/10/2014.
 */
public class AuthorCategoryNameConverterProvider implements ParamConverterProvider {

    private final AuthorCategoryNameParamConverter converter = new AuthorCategoryNameParamConverter();

    @Override
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
        if(AuthorCategoryName.class == rawType){
            return (ParamConverter<T>) converter;
        }else{
            return null;
        }
    }
}
