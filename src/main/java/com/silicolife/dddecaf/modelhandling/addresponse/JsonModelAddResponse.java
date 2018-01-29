package com.silicolife.dddecaf.modelhandling.addresponse;

import java.io.BufferedWriter;
import java.io.StringWriter;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.silicolife.dddecaf.utils.IAddResponse;

import pt.uminho.ceb.biosystems.mew.biocomponents.container.Container;
import pt.uminho.ceb.biosystems.mew.biocomponents.container.io.writers.JSONWriter;

public class JsonModelAddResponse implements IAddResponse{

	@Override
	public void addToResponse(Map<String, Object> response, Container container, Map<String, Object> parameters)
			throws Exception {
		
		
		StringWriter w = new StringWriter();
		BufferedWriter bw = new BufferedWriter(w);
		
		JSONWriter writer = new JSONWriter(bw, container);
		writer.writeToFile(false);
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode map = mapper.readTree(w.toString());
		
		
		response.put("model", map);
		
		
	}

}
