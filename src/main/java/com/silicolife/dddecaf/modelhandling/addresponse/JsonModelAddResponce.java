package com.silicolife.dddecaf.modelhandling.addresponse;

import java.io.BufferedWriter;
import java.io.StringWriter;
import java.util.Map;

import com.silicolife.dddecaf.utils.IAddResponse;

import pt.uminho.ceb.biosystems.mew.biocomponents.container.Container;
import pt.uminho.ceb.biosystems.mew.biocomponents.container.io.writers.JSONWriter;

public class JsonModelAddResponce implements IAddResponse{

	@Override
	public void addToResponse(Map<String, Object> response, Container container, Map<String, Object> parameters)
			throws Exception {
		
		
		StringWriter w = new StringWriter();
		BufferedWriter bw = new BufferedWriter(w);
		
		JSONWriter writer = new JSONWriter(bw, container);
		writer.writeToFile();
		response.put("model", w.toString());
		
		
	}

}
