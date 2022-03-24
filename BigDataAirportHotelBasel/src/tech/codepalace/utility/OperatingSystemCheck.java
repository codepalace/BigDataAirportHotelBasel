package tech.codepalace.utility;

public class OperatingSystemCheck {
	
	public enum OperatingSystem {
        WINDOWS, LINUX, MAC, SOLARIS
    };
    
    private static OperatingSystem operatingSystem = null;
    
    public static OperatingSystem getOparatingSystem() {
        if (operatingSystem == null) {
            String operSys = System.getProperty("os.name").toLowerCase();
            if (operSys.contains("win")) {
            	operatingSystem = OperatingSystem.WINDOWS;
            } else if (operSys.contains("nix") || operSys.contains("nux")
                    || operSys.contains("aix")) {
            	operatingSystem = OperatingSystem.LINUX;
            } else if (operSys.contains("mac")) {
            	operatingSystem = OperatingSystem.MAC;
            } else if (operSys.contains("sunos")) {
            	operatingSystem = OperatingSystem.SOLARIS;
            }
        }
        return operatingSystem;
    }
    
    
    
    

}
