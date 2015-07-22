package com.bodybuilding.api.services.authors;

import com.bodybuilding.api.BBComAPIResponse;
import com.bodybuilding.api.exception.ApiException;
import com.bodybuilding.api.services.BodybuildingService;
import com.bodybuilding.api.type.ReturnCode;
import com.bodybuilding.api.APIResponse;
import com.bodybuilding.content.model.author.Author;
import com.bodybuilding.content.model.author.AuthorCategoryName;
import com.bodybuilding.content.model.author.AuthorGender;
import com.bodybuilding.content.model.dto.AboutAuthorDTO;
import com.bodybuilding.content.model.dto.AuthorCategoryDTO;
import com.bodybuilding.content.model.dto.AuthorDTO;
import com.bodybuilding.content.model.dto.AuthorsStatsDTO;
import com.bodybuilding.content.model.exceptions.BusinessException;
import com.bodybuilding.content.model.util.SortBy;
import com.bodybuilding.content.model.util.SortOrder;
import com.bodybuilding.content.model.util.SortProperties;
import com.bodybuilding.content.services.IAuthorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wordnik.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.ws.WebServiceProvider;
import java.util.List;


@Service("authors")
@WebServiceProvider
@Path("/authors")
@Api(value = "/authors", description = "Authors API Properties and Methods")
@Produces(MediaType.APPLICATION_JSON)
public class AuthorService extends BodybuildingService{

    @Autowired
    IAuthorService authorsService;

    private Logger log = LoggerFactory.getLogger(AuthorService.class);

    protected void setAuthorsService(IAuthorService authorsService){
        this.authorsService = authorsService;
    }

    @GET
    @Path("/getAuthorByUrl")
    @Produces( { MediaType.APPLICATION_JSON })
    @ApiOperation(value = "Find an author by URL", response = APIResponse.class)
    public APIResponse getAuthorByUrl(@ApiParam(value = "The URL to search on", required = true) @QueryParam("url") String url) throws ApiException{
        if(null == url){
            throw new ApiException("url is a required parameter.", ReturnCode.MISSING_REQUIRED_PARAMETER);
        }

        AuthorDTO author = null;

        try {
            author = authorsService.getAuthorByUrl(url);
        } catch (BusinessException e) {
            throw new ApiException("Error finding Article" + e.getMessage(), ReturnCode.GENERAL_ERROR);
        }

        if(author == null){
            return new BBComAPIResponse("No author found for url");
        }

        return new BBComAPIResponse(author);
    }


    @GET
    @Path("/getAuthorsByCategory")
    @Produces( { MediaType.APPLICATION_JSON })
    @ApiOperation(value = "Find an author by category and gender", response = APIResponse.class)
    public APIResponse getAuthorsByCategory(@ApiParam(value = "The category to search for Authors", required = true) @QueryParam("category") AuthorCategoryNameParam category,
                                            @ApiParam(value = "The gender of Authors to search for", required = true) @QueryParam("gender") AuthorGenderParam gender) throws ApiException{


        List<AuthorDTO> authors = null;

        try {
            authors = authorsService.getAuthorsByCategory(category.getAuthorCategoryName(),gender.getAuthorGender());
        } catch (BusinessException e) {
            throw new ApiException("Error finding Authors" + e.getMessage(), ReturnCode.GENERAL_ERROR);
        }

        if(authors == null){
            return new BBComAPIResponse("No author found for category and gender");
        }

        return new BBComAPIResponse(authors);

    }

    @GET
    @Path("/getAuthorsByGender")
    @Produces( { MediaType.APPLICATION_JSON })
    @ApiOperation(value = "Find an author by gender", response = APIResponse.class)
    public APIResponse getAuthorsByGender(@ApiParam(value = "The gender of Authors to search for", required = true)
                                          @QueryParam("gender") AuthorGenderParam gender) throws ApiException{
//        AuthorGender authorGender = AuthorGender.valueOf(gender);

        List<AuthorDTO> authors = null;

        try {
            authors = authorsService.getAuthorsByGender(gender.getAuthorGender());
        } catch (BusinessException e) {
            throw new ApiException("Error finding Authors" + e.getMessage(), ReturnCode.GENERAL_ERROR);
        }

        if(authors == null){
            return new BBComAPIResponse("No author found for gender");
        }

        return new BBComAPIResponse(authors);
    }

    @GET
    @Path("/getAuthorsStats")
    @Produces( { MediaType.APPLICATION_JSON })
    @ApiOperation(value = "Get statistics on authors' articles", response = APIResponse.class)
    public APIResponse getAuthorsStats(@ApiParam(value = "The category of authors", required = true) @QueryParam("authorCategory") AuthorCategoryDTO authorCategory,
                                       @ApiParam(value = "The authors to get stats for", required = true) @QueryParam("authors") AuthorDTOs authors) throws ApiException{

        AuthorsStatsDTO authorsStats = null;

        try{
            authorsStats = authorsService.getAuthorsStats(authorCategory, authors.getAuthorDTOs());
        }catch (BusinessException e){
            throw new ApiException("Error finding Authors' stats" + e.getMessage(), ReturnCode.GENERAL_ERROR);
        }

        if(authorsStats == null)
        {
            return  new BBComAPIResponse("No stats found for authors");
        }

        return new BBComAPIResponse(authorsStats);
    }

    @GET
    @Path("/collectAboutAuthorInfo")
    @Produces( { MediaType.APPLICATION_JSON })
    @ApiOperation(value = "Get about author information", response = APIResponse.class)
    public APIResponse collectAboutAuthorInfo(@ApiParam(value = "The author to collect info for", required = true) @QueryParam("author") Author author) throws ApiException{
        AboutAuthorDTO aboutAuthorDTO = null;

        try{
            aboutAuthorDTO = authorsService.collectAboutAuthorInfo(author);
        }catch (BusinessException e){
            throw new ApiException("Error getting AboutAuthor information" + e.getMessage(), ReturnCode.GENERAL_ERROR);
        }

        if(aboutAuthorDTO == null)
        {
            return  new BBComAPIResponse("No about author information found");
        }

        return new BBComAPIResponse(aboutAuthorDTO);
    }


    @GET
    @Path("/getAuthorByName")
    @Produces({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Find an author by name")
    public APIResponse getAuthorByName(@ApiParam(value = "Author's Name", required = true) @QueryParam("name") String name,
                                       @ApiParam(value = "Ascending or Descending Sort Order", allowableValues = "ASCENDING,DESCENDING") @QueryParam("sortOrder") String sortOrder,
                                       @ApiParam(value = "The Property to sorty the results by", allowableValues = "CATEGORY,NAME,DATE,NONE") @QueryParam("sortBy") String sortBy) throws ApiException{
        SortProperties sortProperties = new SortProperties(SortBy.NONE, SortOrder.ASCENDING);

        if(sortOrder != null)
        {
            sortProperties.setSortOrder(SortOrder.valueOf(sortOrder));
        }

        if(sortBy != null)
        {
            sortProperties.setSortBy(SortBy.valueOf(sortBy));
        }

        if(name == null){
            throw new ApiException("name is a required parameter", ReturnCode.MISSING_REQUIRED_PARAMETER);
        }

        AuthorDTO author = null;

        try{
            author = authorsService.getAuthorByName(name, sortProperties);
        }
        catch (BusinessException e){
            throw new ApiException("Error finding Author" + e.getMessage(), ReturnCode.GENERAL_ERROR);
        }

        if(author == null){
            return new BBComAPIResponse("No author found for name");
        }

        return new BBComAPIResponse(author);
    }

    @GET
    @Path("/getSimpleAuthorByName")
    @Produces( { MediaType.APPLICATION_JSON })
    @ApiOperation(value = "Find an author by Name", response = APIResponse.class)
    public APIResponse getSimpleAuthorByName(@ApiParam(value = "The name of the author to search for", required = true) @QueryParam("name") String name) throws ApiException{
        if(null == name){
            throw new ApiException("url is a required parameter.", ReturnCode.MISSING_REQUIRED_PARAMETER);
        }

        Author author = null;

        try {
            author = authorsService.getSimpleAuthorByName(name);
        } catch (BusinessException e) {
            throw new ApiException("Error finding Author" + e.getMessage(), ReturnCode.GENERAL_ERROR);
        }

        if(author == null){
            return new BBComAPIResponse("No author found for name");
        }

        return new BBComAPIResponse(author);
    }

}