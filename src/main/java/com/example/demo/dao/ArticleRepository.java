package com.example.demo.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.dto.Article;

public interface ArticleRepository extends MongoRepository<Article, ObjectId>{

	List<Article> findByTitle(String title);

}
