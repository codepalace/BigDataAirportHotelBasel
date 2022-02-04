package tech.codepalace.model;

import tech.codepalace.view.frames.LoginUser;

public class LogicModelLogin {
	
	protected UserAHB userAHB;
	protected LoginUser loginUser;
	
	public LogicModelLogin(LoginUser loginUser,  UserAHB userAHB) {
		this.loginUser = loginUser;
		this.userAHB = userAHB;
		
		
	}

}
