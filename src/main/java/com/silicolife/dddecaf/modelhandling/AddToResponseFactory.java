package com.silicolife.dddecaf.modelhandling;

import java.util.HashMap;
import java.util.Map;

import com.silicolife.dddecaf.modelhandling.addresponse.JsonModelAddResponce;
import com.silicolife.dddecaf.modelhandling.addresponse.SimulationAddResponce;
import com.silicolife.dddecaf.utils.IAddResponse;

import pt.uminho.ceb.biosystems.mew.biocomponents.container.Container;

public class AddToResponseFactory {

	private static AddToResponseFactory instance;
	
	public static AddToResponseFactory getInstance(){
		if(instance == null){
			instance = new AddToResponseFactory();
			instance.putAddResponse("simulation_methods", new SimulationAddResponce());
//			instance.putAddResponse("json_model", new JsonModelAddResponce());
		}
		return instance;
	}
	
	private AddToResponseFactory(){
		registry = new HashMap<>();
	}
	
	public Map<String, IAddResponse> registry;

	public Map<String, Object> execute(Container container, Map<String, Object> parameters) throws Exception{
		Map<String, Object> response = new HashMap<>();
		for (IAddResponse modifier : registry.values()) {
			modifier.addToResponse(response, container, parameters);
		}
		
		return response;
	}
	
	public void putAddResponse(String id, IAddResponse r){
		registry.put(id, r);
	}
}
