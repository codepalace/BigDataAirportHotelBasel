package tech.codepalace.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * 
 * @author Antonio Estevez Gonzalez
 * @description Useful class to write properties to our configuration file
 *
 */
public class PropertiesWriter {
	
	
		//Variable that contains the directory path of our application
		private String projectDirectoryString = System.getProperty("user.dir");
		
		//to verify the existence of the config.properties file.
		private File configurationFile = new File(projectDirectoryString + File.separator + "config.properties");
		
		
		//for the PropertyName and PropertyValue. Used to save the information in the config.properties file.
		protected String propertyName, propertyValue;
		
		//Instance of Properties
		private Properties prop = new Properties();
		
		
	public PropertiesWriter() {}
	
	
	
	
/**
 * 	
 * @param propertyName
 * @param propertyValue
 * @description method to write properties properties into the file config.properties
 */
public void writeProperties(String propertyName, String propertyValue) {
		
		this.propertyName = propertyName;
		this.propertyValue = propertyValue;

		
	/* We check if the file has already been created. */
if (configurationFile.exists()) {

	/* In case file exist, we call InputStream and we pass the File name */
		try (InputStream input = new FileInputStream(projectDirectoryString + File.separator + "config.properties")) {

	       
			
	        // load a properties file
	        prop.load(input);
	        
	     
	        // set the properties value
            prop.setProperty(this.propertyName, this.propertyValue);
	       
	       

	    } catch (IOException ex) {
	        ex.printStackTrace();
	    }
		
		
		//Overwrite the file containing a new property. For doing this we use OutPutStream and we pass to the arguments FileOutputStream with the Path and name of the File.
		try (OutputStream output = new FileOutputStream(projectDirectoryString + File.separator + "config.properties")) {


	        // save properties to project root folder
	        prop.store(output, null);


	    } catch (IOException io) {
	        io.printStackTrace();
	    }
	

			
		}else {
			
			/*
			 No existe el archivo de configuración, creamos una nuevo con la primera propiedad 
			 y el valor que recibe el método.
			 */
			
			try (OutputStream output = new FileOutputStream(projectDirectoryString + File.separator + "config.properties")) {

	            

				// set the properties value
	            prop.setProperty(this.propertyName, this.propertyValue);

	            // save properties to project root folder
	            prop.store(output, null);

	            System.out.println("A new propertie was saved to the File: " + this.propertyName);

	        } catch (IOException io) {
	            io.printStackTrace();
	        }
		}


	}
	
	
	
	

}
