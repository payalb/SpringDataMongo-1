package com.example.demo.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class Article {

	@Id
	public ObjectId id;
	public UUID authorId;
	private LocalDate date;
	private String title;
	private byte[] text;
	private List<Comment> comments;
	private long commentCount;
}
