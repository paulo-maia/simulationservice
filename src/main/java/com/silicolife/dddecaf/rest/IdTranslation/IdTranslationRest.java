package com.silicolife.dddecaf.rest.IdTranslation;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequestWithBody;

public class IdTranslationRest {
	
	HttpRequestWithBody service;
	
	
	public IdTranslationRest(String uri){
		service = Unirest.post("http://localhost:8080/model/iJO1366/");
	}
}
