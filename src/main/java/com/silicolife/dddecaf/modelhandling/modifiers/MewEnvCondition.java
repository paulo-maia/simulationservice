package com.silicolife.dddecaf.modelhandling.modifiers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.silicolife.dddecaf.rest.IdTranslation.IdTranslationRest;
import com.silicolife.dddecaf.utils.IModifyModelParameter;

import pt.uminho.ceb.biosystems.mew.biocomponents.container.Container;
import pt.uminho.ceb.biosystems.mew.biocomponents.container.components.ReactionConstraintCI;
import pt.uminho.ceb.biosystems.mew.core.model.components.EnvironmentalConditions;
import pt.uminho.ceb.biosystems.mew.core.model.components.ReactionConstraint;
import pt.uminho.ceb.biosystems.mew.utilities.datastructures.map.MapUtils;

public class MewEnvCondition implements IModifyModelParameter {

	public static final String MEDIUM_KEY 		= "medium";
	public static final String MEASUREMENTS_KEY = "measurements";

	public MewEnvCondition() {
	}

	@Override
	public void modify(Container container, Map<String, Object> parameters) throws Exception {
		
		if(parameters.containsKey(MEASUREMENTS_KEY)){
			
			Map<String, Double> chebiMeasure = new HashMap<>();
			List<Object> listOfMeasurements = (List<Object>) parameters.get(MEASUREMENTS_KEY);
			for(Object measurement : listOfMeasurements){
				Map<String,Object> measurementMap = (Map<String, Object>) measurement;
				String chebi = (String) measurementMap.get("id");
				Double value = (Double) measurementMap.get("measurement");
				chebiMeasure.put(chebi.replaceAll("chebi:", ""), value);
			}
			
			Map<String, Object> biggIds = IdTranslationRest.translateIDsFromChebiToBiGG(new ArrayList<>(chebiMeasure.keySet()));
			
			
			Map<String,Double> lowerBounds = new HashMap<>();
			Map<String,List<String>> ids = (Map<String, List<String>>) biggIds.get("ids");
			for(String chebi : ids.keySet()){
				for(String bigg : ids.get(chebi)){
					bigg = bigg+"_e";
					if(container.getMetabolites().containsKey(bigg)){
						String drain = container.getMetaboliteToDrain().get(bigg);
						if(drain!=null && !drain.isEmpty()){
							lowerBounds.put(drain, chebiMeasure.get(chebi));
							break;
						}else{
							System.out.println("No drain reaction for metabolite ["+bigg+"]");
						}
					}
				}
			}
			
			EnvironmentalConditions envCond = new EnvironmentalConditions();
			for(Entry<String,Double> constraint : lowerBounds.entrySet()){
				ReactionConstraintCI original = container.getDefaultEC().get(constraint.getKey());
				envCond.addReactionConstraint(constraint.getKey(), new ReactionConstraint(constraint.getValue(),original.getUpperLimit()));
			}
			
			parameters.put("MEW_ENV_COND", envCond);
			
			MapUtils.prettyPrint(lowerBounds);			
		}
	}

}
