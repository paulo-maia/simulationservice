package com.silicolife.dddecaf.modelhandling.addresponse;

import java.util.HashMap;
import java.util.Map;

import com.silicolife.dddecaf.utils.IAddResponse;

import pt.uminho.ceb.biosystems.mew.biocomponents.container.Container;
import pt.uminho.ceb.biosystems.mew.core.model.components.EnvironmentalConditions;
import pt.uminho.ceb.biosystems.mew.core.model.converters.ContainerConverter;
import pt.uminho.ceb.biosystems.mew.core.model.steadystatemodel.ISteadyStateModel;
import pt.uminho.ceb.biosystems.mew.core.simulation.components.FluxValueMap;
import pt.uminho.ceb.biosystems.mew.core.simulation.components.GeneticConditions;
import pt.uminho.ceb.biosystems.mew.core.simulation.components.SimulationProperties;
import pt.uminho.ceb.biosystems.mew.core.simulation.components.SimulationSteadyStateControlCenter;
import pt.uminho.ceb.biosystems.mew.solvers.SolverType;

public class SimulationAddResponce implements IAddResponse{

	public static String METHOD_TYPE = "simulation-method";
	
	
	Map<String,String> methodsNameConvertion = new HashMap<>();
	
	public SimulationAddResponce(){
		methodsNameConvertion.put("fba", SimulationProperties.FBA);
	}

	
	FluxValueMap result(Container container, Map<String, Object> parameters, String method) throws Exception {
		
		ISteadyStateModel model = ContainerConverter.convert(container);
		
		EnvironmentalConditions environmentalConditions  = (EnvironmentalConditions) parameters.get("MEW_ENV_COND");
		GeneticConditions geneticConditions = (GeneticConditions) parameters.get("MEW_GEN_COND");
		
		
		SimulationSteadyStateControlCenter cc = new SimulationSteadyStateControlCenter(environmentalConditions, geneticConditions, model, method);
		cc.setSolver(SolverType.CPLEX);
		cc.setMaximization(true);
		return cc.simulate().getFluxValues();
	}
	
	private String getMewMethodIdFromDDDECAF(Map<String, Object> parameters) {
		String ddcaffId = (String) parameters.get(METHOD_TYPE);
		return methodsNameConvertion.get(ddcaffId);

	}
	
	@Override
	public void addToResponse(Map<String, Object> response, Container container, Map<String, Object> parameters) throws Exception {
		
		String method = getMewMethodIdFromDDDECAF(parameters);
		if(method !=  null){
			response.put("fluxes", result(container, parameters, method));
		}
		
	}

}
