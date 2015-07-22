package com.bodybuilding.api.services.authors;

import com.bodybuilding.api.APIResponse;
import com.bodybuilding.api.exception.ApiException;
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
import com.bodybuilding.content.services.impl.AuthorsService;
import org.junit.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Created by Ray.Wilkett on 6/4/2014.
 */
public class AuthorsUnitTest {
    @Mock
    private AuthorsService authorsServiceMock;
    private AuthorService authorService;
    private AuthorDTO authorDTO;
    private AuthorDTO authorDTO2;
    private AuthorDTO authorDTO3;
    private AuthorDTO authorDTO4;

    private String url = "http://www.bodybuilding.com/fun/maia.htm";
    private String emailAddress = "maia@inch-aweigh.com";
    private String authorName = "Maia Appleby";
    private SortProperties sortCategoryAscending = new SortProperties(SortBy.CATEGORY, SortOrder.ASCENDING);
    private List<AuthorDTO> authorDTOList;

    @Before
    public void Setup(){
        MockitoAnnotations.initMocks(this);
        authorService = new AuthorService();
        authorService.setAuthorsService(authorsServiceMock);
        createAuthorDTOs();
    }

    private void createAuthorDTOs(){
        authorDTO = new AuthorDTO(1, authorName, emailAddress, url, "Female");
        authorDTO2 = new AuthorDTO(15, "John Berardi", null, "http://www.bodybuilding.com/fun/berardi.htm", "Male");
        authorDTO3 = new AuthorDTO(20, "Monica Brant", "monica@monicabrant.com", "http://www.bodybuilding.com/fun/monica.htm", "Female");
        authorDTO4 = new AuthorDTO(35, "John Davies", "coachdavies@renegadetraining.com", "http://www.bodybuilding.com/fun/renegade.htm", "Male");
        authorDTOList = new ArrayList<AuthorDTO>();
        authorDTOList.add(authorDTO);
        authorDTOList.add(authorDTO2);
        authorDTOList.add(authorDTO3);
        authorDTOList.add(authorDTO4);
    }

    @After
    public void after() {
        Mockito.reset(authorsServiceMock);
    }

    @Test
    public void testGetAuthorByName(){
        try {
            when(authorsServiceMock.getAuthorByName(eq(authorName), refEq(sortCategoryAscending))).thenReturn(authorDTO);
            APIResponse response = authorService.getAuthorByName(authorName, "ASCENDING", "CATEGORY");
            Assert.assertNotNull(response.getData());
        }
        catch (ApiException ex){
            Assert.fail(ex.getMessage());
        }
        catch(BusinessException be){
            Assert.fail(be.getMessage());
        }
    }

    @Test
    public void testGetAuthorByURL(){
        try{
            when(authorsServiceMock.getAuthorByUrl(url)).thenReturn(authorDTO);
            APIResponse response = authorService.getAuthorByUrl(url);
            Assert.assertNotNull(response.getData());
        }
        catch (ApiException ex){
            Assert.fail(ex.getMessage());
        }
        catch(BusinessException be){
            Assert.fail(be.getMessage());
        }
    }

    @Test
    public void testGetAuthorsByCategory(){
        try{
            AuthorCategoryName authorCategoryName = AuthorCategoryName.valueOf("transformations");
            AuthorGender gender = AuthorGender.MEN;
            AuthorCategoryNameParam authorCategoryParam = new AuthorCategoryNameParam(authorCategoryName);
            AuthorGenderParam authorGenderParam = new AuthorGenderParam(gender);

            when(authorsServiceMock.getAuthorsByCategory(authorCategoryName, gender)).thenReturn(authorDTOList);
            APIResponse response = authorService.getAuthorsByCategory(authorCategoryParam, authorGenderParam);
            List<AuthorDTO> authorDTOs = (List<AuthorDTO>) response.getData();
            Assert.assertTrue(authorDTOs.size() == 4);
        }
        catch (ApiException ex){
            Assert.fail(ex.getMessage());
        }
        catch(BusinessException be){
            Assert.fail(be.getMessage());
        }
    }

    @Test
    public void testGetAuthorsByGender(){
        try{
            AuthorGender gender = AuthorGender.MEN;
            AuthorGenderParam authorGenderParam = new AuthorGenderParam(gender);

            when(authorsServiceMock.getAuthorsByGender(gender)).thenReturn(authorDTOList);
            APIResponse response = authorService.getAuthorsByGender(authorGenderParam);
            List<AuthorDTO> authorDTOs = (List<AuthorDTO>) response.getData();
            Assert.assertTrue(authorDTOs.size() == 4);
        }
        catch (ApiException ex){
            Assert.fail(ex.getMessage());
        }
        catch(BusinessException be){
            Assert.fail(be.getMessage());
        }
    }

    @Test
    public void testGetAuthorsStats(){
        try{
            AuthorCategoryDTO authorCategoryDTO = new AuthorCategoryDTO();
            authorCategoryDTO.setAuthorGender(AuthorGender.MEN);
            authorCategoryDTO.setCategoryName(AuthorCategoryName.athletes);

            AuthorsStatsDTO authorsStatsDTO = new AuthorsStatsDTO();
            authorsStatsDTO.setAuthorList(authorDTOList);

            AuthorDTOs authors = new AuthorDTOs(authorDTOList);

            when(authorsServiceMock.getAuthorsStats(authorCategoryDTO, authorDTOList)).thenReturn(authorsStatsDTO);
            APIResponse response = authorService.getAuthorsStats(authorCategoryDTO, authors);
            AuthorsStatsDTO stats = (AuthorsStatsDTO)response.getData();
            Assert.assertTrue(stats.getAuthorList().size() == 4);

        }
        catch (ApiException ex){
            Assert.fail(ex.getMessage());
        }
        catch(BusinessException be){
            Assert.fail(be.getMessage());
        }
    }

    @Test
    public void testCollectAboutAuthorInfo(){
        try{
            Author author = new Author();
            author.setId(1);
            author.setName(authorName);
            author.setGender("Male");
            author.setAuthorPageUrl( "http://www.bodybuilding.com/fun/berardi.htm");

            AboutAuthorDTO aboutAuthorDTO = new AboutAuthorDTO(author, 5);

            when(authorsServiceMock.collectAboutAuthorInfo(author)).thenReturn(aboutAuthorDTO);
            APIResponse response = authorService.collectAboutAuthorInfo(author);
            AboutAuthorDTO about = (AboutAuthorDTO)response.getData();

            Assert.assertTrue(about.getArticlesByAuthorCount().intValue() == 5);
        }
        catch (ApiException ex){
            Assert.fail(ex.getMessage());
        }
        catch(BusinessException be){
            Assert.fail(be.getMessage());
        }
    }

    @Test
    public void testGetSimpleAuthorByName(){
        try {
            Author author = new Author();
            author.setId(1);
            author.setName(authorName);
            author.setGender("Male");
            author.setAuthorPageUrl( "http://www.bodybuilding.com/fun/berardi.htm");

            when(authorsServiceMock.getSimpleAuthorByName(eq(authorName))).thenReturn(author);
            APIResponse response = authorService.getSimpleAuthorByName(authorName);
            Assert.assertNotNull(response.getData());
        }
        catch (ApiException ex){
            Assert.fail(ex.getMessage());
        }
        catch(BusinessException be){
            Assert.fail(be.getMessage());
        }
    }
}
