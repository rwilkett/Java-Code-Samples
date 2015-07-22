package com.bodybuilding.api.services.articles;

import com.bodybuilding.api.APIResponse;
import com.bodybuilding.api.exception.ApiException;
import com.bodybuilding.content.model.article.Article;
import com.bodybuilding.content.model.dto.SubSectionDTO;
import com.bodybuilding.content.model.exceptions.BusinessException;
import com.bodybuilding.content.services.impl.ArticlesService;
import org.junit.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Created by Ray.Wilkett on 6/6/2014.
 */
public class ArticlesUnitTest {
    @Mock
    private ArticlesService articlesServiceMock;
    private ArticleService articleService;
    private Article article;
    private Article article2;
    private Article article3;
    private Article article4;
    private List<Article> listOfArticles = new ArrayList<Article>();
    private List<Integer> articleIdList = Arrays.asList(10,11,444,445);
    private String articleIdString="10,11,444,445";

    @Before
    public void Setup(){
        MockitoAnnotations.initMocks(this);
        articleService = new ArticleService();
        articleService.setArticlesService(articlesServiceMock);
        createMockArticles();
    }

    private void createMockArticles(){

        article = new Article();
        article.setId(10);
        article.setWriter("John Berardi");
        article.setName("Recipes For Success - Part 1!");
        article.setURL("http://www.bodybuilding.com/fun/berardi38.htm");
        listOfArticles.add(article);

        article2 = new Article();
        article2.setId(11);
        article2.setWriter("John Berardi");
        article2.setName("Recipes For Success - Part 2!");
        article2.setURL("http://www.bodybuilding.com/fun/berardi39.htm");
        listOfArticles.add(article2);

        article3 = new Article();
        article3.setId(444);
        article3.setWriter("Carmen Garcia");
        article3.setName("Women Who Fear Pumping Iron!");
        article3.setURL("http://www.bodybuilding.com/fun/carmen11.htm");
        listOfArticles.add(article3);

        article4 = new Article();
        article4.setId(445);
        article4.setWriter("Carmen Garcia");
        article4.setName("Get Great Glutes!");
        article4.setURL("http://www.bodybuilding.com/fun/carmen10.htm");
        listOfArticles.add(article4);
    }

    @After
    public void after() {
        Mockito.reset(articlesServiceMock);
    }

    @Test
    public void testFindArticleById(){
        try{
            when(articlesServiceMock.findArticleById(10)).thenReturn(article);
            APIResponse response = articleService.findArticleById(10);

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
    public void testFindArticlesByIds(){
        try{
            when(articlesServiceMock.findArticlesByIds(articleIdList)).thenReturn(listOfArticles);
            APIResponse response = articleService.findArticlesByIds(articleIdString);

            List<Article> results = (List<Article>)response.getData();
            Assert.assertTrue(results.size() == 4);
        }
        catch (ApiException ex){
            Assert.fail(ex.getMessage());
        }
        catch(BusinessException be){
            Assert.fail(be.getMessage());
        }
    }

    @Test
    public void testFindArticleByURL(){
        try{
            when(articlesServiceMock.findArticlesByUrl("http://www.bodybuilding.com/fun/berardi38.htm")).thenReturn(article);
            APIResponse response = articleService.findArticlesByUrl("http://www.bodybuilding.com/fun/berardi38.htm");

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
    public void testGetSubSection(){
        try{
            SubSectionDTO subSectionDTO = new SubSectionDTO();
            when(articlesServiceMock.getSubSection("Recipes", "NAME")).thenReturn(subSectionDTO);
            APIResponse response = articleService.getSubSection("Recipes", "NAME");

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
    public void testGetSubSectionHeader(){
        try{
            SubSectionDTO subSectionDTO = new SubSectionDTO();
            when(articlesServiceMock.getSubSectionHeader("Recipes")).thenReturn(subSectionDTO);
            APIResponse response = articleService.getSubSectionHeader("Recipes");

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
    public void testGetArticleURLs(){
        try{
            List<String> urls = new ArrayList<String>();
            when(articlesServiceMock.getArticleURLs()).thenReturn(urls);
            APIResponse response = articleService.getArticleURLs();

            Assert.assertNotNull(response.getData());
        }
        catch (ApiException ex){
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void testGetArticleByUrlFromDB(){
        try{
            when(articlesServiceMock.getArticleByUrlFromDB("http://www.bodybuilding.com/fun/berardi38.htm")).thenReturn(article);
            APIResponse response = articleService.getArticleByUrlFromDB("http://www.bodybuilding.com/fun/berardi38.htm");

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
