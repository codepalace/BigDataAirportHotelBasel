package tech.codepalace.controller;

import tech.codepalace.model.LogicModelFundSachen;
import tech.codepalace.model.UserAHB;
import tech.codepalace.view.frames.Fundsachen;

public class FundsachenController {
	
	private Fundsachen fundSachen;
	UserAHB userAHB;
	LogicModelFundSachen logicModelFundSachen;
	
	
	public FundsachenController(Fundsachen fundSachen, UserAHB userAHB, LogicModelFundSachen logicModelFundSachen) {
		this.fundSachen = fundSachen; 
		this.userAHB = userAHB;
		this.logicModelFundSachen = logicModelFundSachen;
		
	}

}
