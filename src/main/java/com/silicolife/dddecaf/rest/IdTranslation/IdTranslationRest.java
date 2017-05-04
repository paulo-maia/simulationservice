package com.silicolife.dddecaf.rest.IdTranslation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class IdTranslationRest {
	
	static{
		// Only one time
		Unirest.setObjectMapper(new ObjectMapper() {
			private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
			= new com.fasterxml.jackson.databind.ObjectMapper();
			
			public <T> T readValue(String value, Class<T> valueType) {
				try {
					return jacksonObjectMapper.readValue(value, valueType);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
			
			public String writeValue(Object value) {
				try {
					return jacksonObjectMapper.writeValueAsString(value);
				} catch (JsonProcessingException e) {
					throw new RuntimeException(e);
				}
			}
		});	
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map<String,Object> translateIDsFromTo(List<String> ids, String from, String to) throws UnirestException{
		HttpResponse<Map> response = Unirest.post("https://api.dd-decaf.eu/idmapping/query")
				.header("accept", "application/json")
				.body(buildJSONBody(from, to, ids))
				.asObject(Map.class);
			
		System.out.println(response.getBody().toString());
		return response.getBody();		
	}
	
	public static Map<String, Object> translateIDsFromChebiToBiGG(List<String> ids) throws UnirestException{
		return translateIDsFromTo(ids, "chebi", "bigg");
	}
	
	public static String buildJSONBody(String from, String to, List<String> ids){
		String json = "{\"ids\":[";
		for(int i=0; i<ids.size();i++){
			String id = ids.get(i);
			json = json+"\""+id+"\"";
			if(i<ids.size()-1){
				json += ",";
			}
		}
		json+="], \"type\": \"Metabolite\", \"dbFrom\":\""+from+"\", \"dbTo\":\""+to+"\"}";
		return json;
	}
	
	public static void main(String[] args) {
		List<String> set = new ArrayList<String>();
		set.add("30838");
		set.add("12965");
		
		System.out.println(buildJSONBody("chebi", "bigg", set));
	}
}
