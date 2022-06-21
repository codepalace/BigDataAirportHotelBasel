package tech.codepalace.model;

/**
 * 
 * @author Antonio Estevez Gonzalez
 * 
 * @description UserAHB Class it will be used to configure the user data and to obtain certain properties of the logged user.
 *
 */

public class UserAHB {
	
protected String userName, password, abkuerzungMA, privilege, urlDataBase, urlDataBaseBackup, dbName;
	
	
	public String getPassword() {
	return password;
}

public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

public void setPassword(String password) {
	this.password = password;
}

	public String getUrlDataBase() {
		return urlDataBase;
	}
	
	public String getUrlDataBaseBackup() {
		return urlDataBaseBackup;
	}

	public void setUrlDataBase(String urlDataBase) {
		this.urlDataBase = urlDataBase;
	}
	
	public void seturlDataBaseBackup(String urlDataBaseBackup) {
		this.urlDataBaseBackup = urlDataBaseBackup;
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

	public String getAbkuerzungMA() {
		return abkuerzungMA;
	}

	public void setAbkuerzungMA(String abbkuerzungMA) {
		this.abkuerzungMA = abbkuerzungMA;
	}

}