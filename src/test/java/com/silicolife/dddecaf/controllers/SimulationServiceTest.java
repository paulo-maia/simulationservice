package com.silicolife.dddecaf.controllers;

import java.io.IOException;
import java.io.StringWriter;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import pt.uminho.ceb.biosystems.mew.biocomponents.container.Container;
import pt.uminho.ceb.biosystems.mew.biocomponents.container.components.InvalidBooleanRuleException;
import pt.uminho.ceb.biosystems.mew.biocomponents.container.io.readers.JSONReader;
import pt.uminho.ceb.biosystems.mew.biocomponents.container.io.writers.JSONWriter;

public class SimulationServiceTest {

//	@Test
	public void testPost() throws UnirestException {

		HttpResponse<JsonNode> jsonResponse = Unirest.post("http://localhost:8080/model/iJO1366/")
				.header("accept", "application/json").header("Content-Type", "application/json")
				.body("{\"medium\": [{\"name\": \"dipotassium hydrogen phosphate\", \"id\": \"chebi:131527\", \"concentration\": 5.0}, {\"name\": \"calcium dichloride\", \"id\": \"chebi:3312\", \"concentration\": 0.005}, {\"name\": \"L-glutamic acid\", \"id\": \"chebi:16015\", \"concentration\": 6.0}, {\"name\": \"iron trichloride\", \"id\": \"chebi:30808\", \"concentration\": 1.6}, {\"name\": \"magnesium sulfate heptahydrate\", \"id\": \"chebi:31795\", \"concentration\": 0.5}, {\"name\": \"boric acid\", \"id\": \"chebi:33118\", \"concentration\": 0.5}, {\"name\": \"cobalt dichloride\", \"id\": \"chebi:35696\", \"concentration\": 2.0}, {\"name\": \"aldehydo-D-glucose\", \"id\": \"chebi:42758\", \"concentration\": 27.0}, {\"name\": \"thiamine(2+) dichloride\", \"id\": \"chebi:49105\", \"concentration\": 0.005}, {\"name\": \"copper(II) chloride\", \"id\": \"chebi:49553\", \"concentration\": 1.0}, {\"name\": \"zinc dichloride\", \"id\": \"chebi:49976\", \"concentration\": 2.0}, {\"name\": \"potassium dihydrogen phosphate\", \"id\": \"chebi:63036\", \"concentration\": 3.5}, {\"name\": \"diammonium hydrogen phosphate\", \"id\": \"chebi:63051\", \"concentration\": 3.5}, {\"name\": \"aldehydo-D-glucose\", \"id\": \"chebi:42758\", \"concentration\": 300.0}, {\"name\": \"ammonium sulfate\", \"id\": \"chebi:62946\", \"concentration\": 52.5}], \"simulation-method\": \"fba\", \"map\": \"Central metabolism\", \"measurements\": [{\"name\": \"itaconic acid\", \"unit\": \"mmol\", \"measurement\": 1.05, \"id\": \"chebi:30838\"}, {\"name\": \"aldehydo-D-glucose\", \"unit\": \"mmol\", \"measurement\": -2.5, \"id\": \"chebi:12965\"}], \"genotype-changes\": [\"-aceA,-sucCD,-pykA,-pykF,-pta,+promoter.BBa_J23100:#AB326105:#NP_600058:terminator.BBa_0010\"], \"to-return\": [\"model\", \"fluxes\"]}")
				.asJson();

		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>> TESTE TESTE TESTE <<<<<<<<<<<<<<<<<<<<<<<");
		System.out.println(jsonResponse.getBody().toString());
	}
	
	
//	@Test
	public void testJsonWriter() throws JsonProcessingException, IOException, InvalidBooleanRuleException{
		String path = SimulationController.class.getClassLoader().getResource("modelRepo").getFile();

		System.out.println("path="+path);
		String modelPath = path + "/" + "iJO1366" + ".json";


		JSONReader reader = new JSONReader(modelPath, "");
		Container cont = new Container(reader);
		
		StringWriter w = new StringWriter();
		JSONWriter json = new JSONWriter(w, cont);
		json.writeToFile();
//		System.out.println(w.toString());
		
	}
	

	@Test
	public void testPrintBody(){
		String body = "{\"medium\": [{\"name\": \"dipotassium hydrogen phosphate\", \"id\": \"chebi:131527\", \"concentration\": 5.0}, {\"name\": \"calcium dichloride\", \"id\": \"chebi:3312\", \"concentration\": 0.005}, {\"name\": \"L-glutamic acid\", \"id\": \"chebi:16015\", \"concentration\": 6.0}, {\"name\": \"iron trichloride\", \"id\": \"chebi:30808\", \"concentration\": 1.6}, {\"name\": \"magnesium sulfate heptahydrate\", \"id\": \"chebi:31795\", \"concentration\": 0.5}, {\"name\": \"boric acid\", \"id\": \"chebi:33118\", \"concentration\": 0.5}, {\"name\": \"cobalt dichloride\", \"id\": \"chebi:35696\", \"concentration\": 2.0}, {\"name\": \"aldehydo-D-glucose\", \"id\": \"chebi:42758\", \"concentration\": 27.0}, {\"name\": \"thiamine(2+) dichloride\", \"id\": \"chebi:49105\", \"concentration\": 0.005}, {\"name\": \"copper(II) chloride\", \"id\": \"chebi:49553\", \"concentration\": 1.0}, {\"name\": \"zinc dichloride\", \"id\": \"chebi:49976\", \"concentration\": 2.0}, {\"name\": \"potassium dihydrogen phosphate\", \"id\": \"chebi:63036\", \"concentration\": 3.5}, {\"name\": \"diammonium hydrogen phosphate\", \"id\": \"chebi:63051\", \"concentration\": 3.5}, {\"name\": \"aldehydo-D-glucose\", \"id\": \"chebi:42758\", \"concentration\": 300.0}, {\"name\": \"ammonium sulfate\", \"id\": \"chebi:62946\", \"concentration\": 52.5}], \"simulation-method\": \"fba\", \"map\": \"Central metabolism\", \"measurements\": [{\"name\": \"itaconic acid\", \"unit\": \"mmol\", \"measurement\": 1.05, \"id\": \"chebi:30838\"}, {\"name\": \"aldehydo-D-glucose\", \"unit\": \"mmol\", \"measurement\": -2.5, \"id\": \"chebi:12965\"}], \"genotype-changes\": [\"-aceA,-sucCD,-pykA,-pykF,-pta,+promoter.BBa_J23100:#AB326105:#NP_600058:terminator.BBa_0010\"], \"to-return\": [\"model\", \"fluxes\"]}";
		System.out.println(body);
	}
	

}
