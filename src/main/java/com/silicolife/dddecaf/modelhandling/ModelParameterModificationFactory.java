package com.silicolife.dddecaf.modelhandling;

import java.util.Map;

import com.silicolife.dddecaf.utils.IModifyModelParameter;

import pt.uminho.ceb.biosystems.mew.biocomponents.container.Container;

public class ModelParameterModificationFactory {

	public Map<String, IModifyModelParameter> registry;
	
	static{
		
	}
	
	public Container execute(Container container, Map<String,Object> parameters){
		Container newContainer = new Container(container);
		
		for(IModifyModelParameter modifier : registry.values()){
			modifier.modify(newContainer, parameters);
		}
		
		return newContainer;
	}
	
}
