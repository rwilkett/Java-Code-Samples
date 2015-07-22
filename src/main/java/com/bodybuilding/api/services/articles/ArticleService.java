package com.bodybuilding.api.services.articles;

import com.bodybuilding.api.BBComAPIResponse;
import com.bodybuilding.api.exception.ApiException;
import com.bodybuilding.api.services.BodybuildingService;
import com.bodybuilding.api.type.ReturnCode;
import com.bodybuilding.api.APIResponse;
import com.bodybuilding.content.model.article.Article;
import com.bodybuilding.content.model.dto.SubSectionDTO;
import com.bodybuilding.content.model.exceptions.BusinessException;
import com.bodybuilding.content.services.IArticleService;
import com.bodybuilding.content.services.impl.ArticlesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wordnik.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.ws.WebServiceProvider;
import java.util.ArrayList;
import java.util.List;

@Service("articles")
@WebServiceProvider
@Path("/articles")
@Api(value = "/articles", description = "Articles API Properties and Methods")
@Produces(MediaType.APPLICATION_JSON)
public class ArticleService extends BodybuildingService {

    @Autowired
    IArticleService articlesService;

    private Logger log = LoggerFactory.getLogger(ArticleService.class);


    protected void setArticlesService(ArticlesService articlesService){
        this.articlesService = articlesService;
    }

    @GET
    @Path("/findArticleById")
    @Produces( { MediaType.APPLICATION_JSON })
    @ApiOperation(value = "Find an article by ID")
    public APIResponse findArticleById(@ApiParam(value = "ID of article to find", required = true) @QueryParam(value = "id") Integer id) throws ApiException{
        if (null == id) {
            throw new ApiException("id is a required parameter. ", ReturnCode.MISSING_REQUIRED_PARAMETER);
        }
        Article article = null;
        try {
            article = articlesService.findArticleById(id);
        } catch (BusinessException e) {
            throw new ApiException("Error finding Article " + e.getMessage(), ReturnCode.GENERAL_ERROR);
        }
        if (article== null) {
            return new BBComAPIResponse("No articles found for ids " + id);
        }
        return new BBComAPIResponse(article);
    }

    @GET
    @Path("/findArticlesByIds")
    @Produces( { MediaType.APPLICATION_JSON })
    @ApiOperation(value = "Find multiple articles by their respective ID's", notes = "Multiple ids can be provided in a comma separated string")
    public APIResponse findArticlesByIds(@ApiParam(value="id", required = true) @QueryParam(value="id") String id) throws ApiException{
        if (null == id) {
            throw new ApiException("ids is a required parameter. ", ReturnCode.MISSING_REQUIRED_PARAMETER);
        }

        List<Integer> intIds = new ArrayList<Integer>();

        String[] ids = id.split(",");
        for (int i = 0; i < ids.length; i++) {
            intIds.add(Integer.parseInt(ids[i]));
        }

        List<Article> articles= null;
        try {
            articles = articlesService.findArticlesByIds(intIds);
        } catch (BusinessException e) {
            throw new ApiException("Error finding Article " + e.getMessage(), ReturnCode.GENERAL_ERROR);
        }
        if (articles== null) {
            return new BBComAPIResponse("No articles found for ids ");
        }
        return new BBComAPIResponse(articles);

    }

    @GET
    @Path("/findArticlesByUrl")
    @Produces({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Find articles by URL, specify whether article ought to be removed from Cache", response = APIResponse.class)
    public APIResponse findArticleByUrl(@ApiParam(value = "The URL of the article", required = true)
                                        @QueryParam(value = "url") String url,
                                        @ApiParam(value="Boolean value indicating whether or not the result ought to be cached", required = true)
                                        @QueryParam(value = "isCacheable") boolean isCacheable) throws ApiException{
        if(null == url){
            throw new ApiException("url is a required parameter. ", ReturnCode.MISSING_REQUIRED_PARAMETER);
        }

        Article article = null;

        try {
            article = articlesService.findArticlesByUrl(url, isCacheable);
        } catch (BusinessException e) {
            throw new ApiException("Error finding Article " + e.getMessage(), ReturnCode.GENERAL_ERROR);
        }
        if (article== null) {
            return new BBComAPIResponse("No article found for url");
        }
        return new BBComAPIResponse(article);
    }


    @GET
    @Path("/findArticlesByUrl")
    @Produces({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Find articles by URL", response = APIResponse.class)
    public APIResponse findArticlesByUrl(@ApiParam(value = "url", required = true) @QueryParam(value = "url") String url) throws ApiException{
        if(null == url){
            throw new ApiException("url is a required parameter. ", ReturnCode.MISSING_REQUIRED_PARAMETER);
        }

        Article article = null;

        try {
            article = articlesService.findArticlesByUrl(url);
        } catch (BusinessException e) {
            throw new ApiException("Error finding Article" + e.getMessage(), ReturnCode.GENERAL_ERROR);
        }
        if (article== null) {
            return new BBComAPIResponse("No article found for url ");
        }
        return new BBComAPIResponse(article);
    }


    @GET
    @Path("/getArticleByUrlFromDB")
    @Produces({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Retrieve an article directly from the database by URL")
    public APIResponse getArticleByUrlFromDB(@ApiParam(value = "URL of the article to retrieve", required = true)@QueryParam(value = "url") String url) throws ApiException{
        if(null == url){
            throw new ApiException("url is a required parameter. ", ReturnCode.MISSING_REQUIRED_PARAMETER);
        }

        Article article = null;

        try {
            article = articlesService.getArticleByUrlFromDB(url);
        } catch (BusinessException e) {
            throw new ApiException("Error finding Article " + e.getMessage(), ReturnCode.GENERAL_ERROR);
        }
        if (article== null) {
            return new BBComAPIResponse("No article found for url");
        }
        return new BBComAPIResponse(article);
    }

    @GET
    @Path("/getSubSectionHeader")
    @Produces({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "getSubSectionHeader", response = APIResponse.class, notes = "Retrieve SubSectionHeader by SubSectionName")
    public APIResponse getSubSectionHeader(@ApiParam(value = "Name of the SubSection", required = true) @QueryParam("subSectionName") String subSectionName) throws ApiException{
        if(null == subSectionName){
            throw new ApiException("subSectionName is a required parameter. ", ReturnCode.MISSING_REQUIRED_PARAMETER);
        }

        SubSectionDTO subSectionDTO = null;

        try {
            subSectionDTO = articlesService.getSubSectionHeader(subSectionName);
        } catch (BusinessException e) {
            throw new ApiException("Error finding SubSectionHeader " + e.getMessage(), ReturnCode.GENERAL_ERROR);
        }
        if (subSectionDTO == null) {
            return new BBComAPIResponse("No subSectionHeader found for subSectionName");
        }
        return new BBComAPIResponse(subSectionDTO);

    }


    @GET
    @Path("/getArticleURLs")
    @Produces({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "getArticleURLs")
    public APIResponse getArticleURLs() throws ApiException{
        List<String> articles = null;
        try{
           articles = articlesService.getArticleURLs();
        } catch (Exception e){
            throw new ApiException("Error retrieving articles " + e.getMessage(), ReturnCode.GENERAL_ERROR);
        }

        if(articles == null){
            return new BBComAPIResponse("No articles found");
        }

        return new BBComAPIResponse(articles);
    }

    @GET
    @Path("/getSubSection")
    @Produces({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "getSubSection", response = APIResponse.class, notes = "Retrieve article subsection by name")
    public APIResponse getSubSection(@ApiParam(value = "SubSection Name") @QueryParam("subSectionName") String subSectionName,
                                     @ApiParam(value = "Sort option string", allowableValues = "NAME, DATE, GENDER, AUTHOR, NONE") @QueryParam("articlesSort") String articlesSort) throws ApiException{
        if(null == subSectionName){
            throw new ApiException("subSectionName is a required parameter. ", ReturnCode.MISSING_REQUIRED_PARAMETER);
        }

        if(null == articlesSort){
            throw new ApiException("articlesSort is a required parameter ", ReturnCode.MISSING_REQUIRED_PARAMETER);
        }

        SubSectionDTO subSectionDTO = null;

        try {
            subSectionDTO = articlesService.getSubSection(subSectionName, articlesSort);
        } catch (BusinessException e) {
            throw new ApiException("Error finding Article " + e.getMessage(), ReturnCode.GENERAL_ERROR);
        }
        if (subSectionDTO == null) {
            return new BBComAPIResponse("No subsection found for subSectionName");
        }
        return new BBComAPIResponse(subSectionDTO);
    }



}