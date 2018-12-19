package com.example.demo.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Comment {

	private String author;
	private String text;
	private LocalDate date;
}
