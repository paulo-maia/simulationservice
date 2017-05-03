package com.silicolife.dddecaf.utils;

import java.util.Map;

import pt.uminho.ceb.biosystems.mew.biocomponents.container.Container;

public interface IAddResponse {

	void addToResponse(Map<String,Object> response, Container container, Map<String,Object> parameters);
}
