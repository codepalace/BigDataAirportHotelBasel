package tech.codepalace.model;

public class LogicModelDataBaseGUI extends LogicModel {
	
	private String appCalled;
	private String guiCaller;
	
	public LogicModelDataBaseGUI(String appCalled, String guiCaller) {
		
		this.appCalled = appCalled;
		this.guiCaller = guiCaller;
	}
	
	
	public String getAppCalled() {
		return this.appCalled;
	}
	
	public String getGUICaller() {
		return this.guiCaller;
	}

}
