package com.silicolife.dddecaf.modelhandling;

import java.util.HashMap;
import java.util.Map;

import com.silicolife.dddecaf.utils.IAddResponse;

import pt.uminho.ceb.biosystems.mew.biocomponents.container.Container;

public class AddToResponseFactory {

	public Map<String, IAddResponse> registry;

	public Map<String, Object> execute(Container container, Map<String, Object> parameters) {
		Map<String, Object> response = new HashMap<>();
		for (IAddResponse modifier : registry.values()) {
			modifier.addToResponse(response, container, parameters);
		}
		
		return response;
	}
}
