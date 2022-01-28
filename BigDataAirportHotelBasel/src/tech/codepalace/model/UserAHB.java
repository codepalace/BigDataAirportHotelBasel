package tech.codepalace.model;

/**
 * 
 * @author Antonio Estevez Gonzalez
 * 
 * @description UserAHB Class it will be used to configure the user data and to obtain certain properties of the logged user.
 *
 */

public class UserAHB {
	
protected String userName, abbkuerzungMA, privilege, urlDataBase;
	
	
	public String getUrlDataBase() {
		return urlDataBase;
	}

	public void setUrlDataBase(String urlDataBase) {
		this.urlDataBase = urlDataBase;
	}


	public String getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAbbkuerzungMA() {
		return abbkuerzungMA;
	}

	public void setAbbkuerzungMA(String abbkuerzungMA) {
		this.abbkuerzungMA = abbkuerzungMA;
	}

}