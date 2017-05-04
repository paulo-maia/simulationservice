package com.silicolife.dddecaf.utils;

import java.util.Map;

import pt.uminho.ceb.biosystems.mew.biocomponents.container.Container;

public interface IModifyModelParameter {

	void modify(Container container, Map<String,Object> parameters) throws Exception;
	
}
