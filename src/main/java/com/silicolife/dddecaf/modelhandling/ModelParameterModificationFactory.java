package com.silicolife.dddecaf.modelhandling;

import java.util.HashMap;
import java.util.Map;

import com.silicolife.dddecaf.utils.IModifyModelParameter;

import pt.uminho.ceb.biosystems.mew.biocomponents.container.Container;

public class ModelParameterModificationFactory {

	private static ModelParameterModificationFactory instance;
	
	public static ModelParameterModificationFactory getInstance() {
		if(instance == null){
			instance = new ModelParameterModificationFactory();
		}
		return instance;
	}
	
	private ModelParameterModificationFactory(){
		registry = new HashMap<>();
	}
	
	public Map<String, IModifyModelParameter> registry;
	
	public Container execute(Container container, Map<String,Object> parameters) throws Exception{
		Container newContainer = new Container(container);
		
		for(IModifyModelParameter modifier : registry.values()){
			modifier.modify(newContainer, parameters);
		}
		
		return newContainer;
	}

	
	public void putModelParameterModification(String id, IModifyModelParameter info){
		registry.put(id, info);
	}
}
