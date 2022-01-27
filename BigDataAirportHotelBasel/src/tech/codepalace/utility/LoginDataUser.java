package tech.codepalace.utility;


/**
 * 
 * @author Antonio Estevez Gonzalez
 * 
 * @description
 * Class to set the Login Data, included the DataBase URL, User privilege, etc. 
 *
 */
public class LoginDataUser {

	protected String urlPropertieName, userNamePropertieName, 
	passwordPropertieName, passwordEnteredByTheUser,
	 privilegePropertieName, abkuerzungPropertieName;

	public String getPasswordEnteredByTheUser() {
		return passwordEnteredByTheUser;
	}

	public void setPasswordEnteredByTheUser(String passwordEnteredByTheUser) {
		this.passwordEnteredByTheUser = passwordEnteredByTheUser;
	}

	public String getPrivilegePropertieName() {
		return privilegePropertieName;
	}

	public void setPrivilegePropertieName(String privilegePropertieName) {
		this.privilegePropertieName = privilegePropertieName;
	}

	public String getAbkuerzungPropertieName() {
		return abkuerzungPropertieName;
	}

	public void setAbkuerzungPropertieName(String abkuerzungPropertieName) {
		this.abkuerzungPropertieName = abkuerzungPropertieName;
	}

	public String getUrlPropertieName() {
		return urlPropertieName;
	}


	public void setUrlPropertieName(String urlPropertieName) {
		this.urlPropertieName = urlPropertieName;
	}

	public String getUserNamePropertieName() {
		return userNamePropertieName;
	}

	public void setUserNamePropertieName(String userNamePropertieName) {
		this.userNamePropertieName = userNamePropertieName;
	}

	public String getPasswordPropertieName() {
		return passwordPropertieName;
	}

	public void setPasswordPropertieName(String passwordPropertieName) {
		this.passwordPropertieName = passwordPropertieName;
	}
	
}
