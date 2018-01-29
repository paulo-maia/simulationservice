package com.silicolife.dddecaf.modelhandling;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.silicolife.dddecaf.modelhandling.addresponse.JsonModelAddResponse;
import com.silicolife.dddecaf.modelhandling.addresponse.SimulationAddResponse;
import com.silicolife.dddecaf.utils.IAddResponse;

import pt.uminho.ceb.biosystems.mew.biocomponents.container.Container;

public class AddToResponseFactory {

	private static AddToResponseFactory instance;
	
	public static AddToResponseFactory getInstance(){
		if(instance == null){
			instance = new AddToResponseFactory();
			instance.putAddResponse("fluxes", new SimulationAddResponse());
			instance.putAddResponse("model", new JsonModelAddResponse());
		}
		return instance;
	}
	
	private AddToResponseFactory(){
		registry = new HashMap<>();
	}
	
	public Map<String, IAddResponse> registry;

	public Map<String, Object> execute(Container container, Map<String, Object> parameters) throws Exception{
		Map<String, Object> response = new HashMap<>();
		
		
		List<String> r = (List<String>) parameters.get("to-return");
		for (String modifier : r) {
			IAddResponse o = registry.get(modifier);
			if(o!=null)
				o.addToResponse(response, container, parameters);
		}
		
		return response;
	}
	
	public void putAddResponse(String id, IAddResponse r){
		registry.put(id, r);
	}
}
