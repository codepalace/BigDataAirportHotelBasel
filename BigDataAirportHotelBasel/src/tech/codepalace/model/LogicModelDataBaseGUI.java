package tech.codepalace.model;

public class LogicModelDataBaseGUI extends LogicModel {
	
	private String appCalled;
	
	public LogicModelDataBaseGUI(String appCalled) {
		
		this.appCalled = appCalled;
	}
	
	
	public String getAppCalled() {
		return this.appCalled;
	}

}
