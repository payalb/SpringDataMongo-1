package com.example.demo.service;

import org.springframework.data.mongodb.core.MongoAction;
import org.springframework.data.mongodb.core.WriteConcernResolver;
import org.springframework.stereotype.Component;

import com.mongodb.WriteConcern;

@Component
public class NewsWriteConcernResolver implements WriteConcernResolver{

	@Override
	public WriteConcern resolve(MongoAction action) {
		if(action.getCollectionName().equals("article")) {
			return WriteConcern.JOURNALED;
		}
		return WriteConcern.UNACKNOWLEDGED;
	}

}
