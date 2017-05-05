package com.silicolife.dddecaf.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.silicolife.dddecaf.modelhandling.AddToResponseFactory;
import com.silicolife.dddecaf.modelhandling.ModelParameterModificationFactory;

import pt.uminho.ceb.biosystems.mew.biocomponents.container.Container;
import pt.uminho.ceb.biosystems.mew.biocomponents.container.components.InvalidBooleanRuleException;
import pt.uminho.ceb.biosystems.mew.biocomponents.container.io.readers.JSONReader;
import pt.uminho.ceb.biosystems.mew.core.model.converters.ContainerConverter;
import pt.uminho.ceb.biosystems.mew.core.model.steadystatemodel.ISteadyStateModel;
import pt.uminho.ceb.biosystems.mew.core.simulation.components.SimulationProperties;
import pt.uminho.ceb.biosystems.mew.core.simulation.components.SimulationSteadyStateControlCenter;
import pt.uminho.ceb.biosystems.mew.core.simulation.components.SteadyStateSimulationResult;
import pt.uminho.ceb.biosystems.mew.solvers.SolverType;

@RestController()
public class SimulationController {

	@RequestMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}

	@RequestMapping("/methods")
	public List<String> getMethods() {
		List<String> methods = new ArrayList<String>();
		methods.add("FBA");
		methods.add("pFBA");
		return methods;
	}

	public Container getModelFromID(String id) throws IOException, InvalidBooleanRuleException {
		ClassPathResource cp = new ClassPathResource("modelRepo/"+id+".json");

		JSONReader reader = new JSONReader(cp.getFile(), "");

		return new Container(reader);
	}

	@RequestMapping(value = "modeltest/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Double> simulateModel(@PathVariable("id") String id, @RequestBody Map<String, Object> input)
			throws Exception {

		Container container = getModelFromID(id);

		Set<String> ext = container.searchReactionById(Pattern.compile(".*_b"));
		container.removeMetabolites(ext);

		ContainerConverter conv = new ContainerConverter(container);

		ISteadyStateModel model = conv.convert();

		SimulationSteadyStateControlCenter cc = new SimulationSteadyStateControlCenter(null, null, model, SimulationProperties.FBA);
		cc.setSolver(SolverType.CPLEX3);
		cc.setMaximization(true);

		cc.setFBAObjSingleFlux(model.getBiomassFlux(), 1.0);

		SteadyStateSimulationResult res = cc.simulate();
		return res.getFluxValues();
	}

	@RequestMapping(value = "model/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> model(@PathVariable("id") String id, @RequestBody Map<String, Object> input)
			throws Exception {
		Container container = getModelFromID(id);

		Set<String> ext = container.searchReactionById(Pattern.compile(".*_b"));
		container.removeMetabolites(ext);
		
		ModelParameterModificationFactory.getInstance().execute(container, input);
		return AddToResponseFactory.getInstance().execute(container, input);
	}

}
