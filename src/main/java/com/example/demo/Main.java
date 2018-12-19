package com.example.demo;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.example.demo.dao.ArticleRepository;
import com.example.demo.dto.Article;
import com.example.demo.dto.Comment;
import com.example.demo.service.NewsWriteConcernResolver;
import com.mongodb.ReadPreference;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(Main.class);
		MongoTemplate template = ctx.getBean(MongoTemplate.class);
		template.setWriteConcernResolver( ctx.getBean(NewsWriteConcernResolver.class));
		template.getCollection("article").withReadPreference(ReadPreference.secondary());
		ArticleRepository rep = ctx.getBean(ArticleRepository.class);
		//createArticle(template);
		//fetchArticle(template);
		//fetchDataUsingRepository(rep);
	//	addCommentOnAnArticle(template);
		addColumnCountComments(template);
	}
	
	/************************************************************************
	 * Push and pop things on array
	 **************************************************************************/
//Running this again will add 1 more comment
	private static void addCommentOnAnArticle(MongoOperations template) {
		Comment comment =new Comment();
		comment.setAuthor("Payal");
		comment.setDate(LocalDate.now());
		comment.setText("Thanks! Glad to be here!");
		template.upsert(Query.query(Criteria.where("title").is("Welcome!")), new Update().push("comments", comment), Article.class);
	}

	//1st comment removed!
	private static void removeCommentFromArticle(MongoOperations template) {
		Comment comment =new Comment();
		comment.setAuthor("Payal");
		comment.setDate(LocalDate.now());
		comment.setText("Thanks! Glad to be here!");
		template.upsert(Query.query(Criteria.where("title").is("Welcome!")), new Update().pop("comments", Update.Position.FIRST), Article.class);
	}
	
	/*******************************************************************
	 * Count number of comments on an article: inc
	 * Update is atomic. Threadsafe. Can use optimistic locking!
	 *****************************************************************/
	private static void addColumnCountComments(MongoOperations template) {
		Comment comment =new Comment();
		comment.setAuthor("Payal");
		comment.setDate(LocalDate.now());
		comment.setText("Thanks again!");
		template.upsert(Query.query(Criteria.where("title").is("Welcome!")), new Update().push("comments",comment).inc("commentCount", 1), Article.class);
	}
	
	
	
	
	
	private static void fetchDataUsingRepository(ArticleRepository rep) {

		List<Article> article = rep.findByTitle("Welcome!");
		System.out.println(article.get(0));

	}

	private static void fetchArticle(MongoOperations template) {
		Article article = template.findOne(Query.query(Criteria.where("title").is("Welcome!")), Article.class);
		System.out.println(article);
	}

	private static void createArticle(MongoOperations template) {
		Article article = new Article();
		article.setAuthorId(UUID.fromString("0a736db3-27d0-40db-a3a2-5a6766389683"));
		article.setDate(LocalDate.now());
		article.setText("Welcome to the news channel".getBytes());
		article.setTitle("Welcome!");
		template.save(article); // save doing upsert
	}

}
