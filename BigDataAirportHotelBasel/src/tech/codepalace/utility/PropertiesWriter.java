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
			
		
			//Save the config.properties
			try (OutputStream output = new FileOutputStream(projectDirectoryString + File.separator + "config.properties")) {

	            

				// set the properties value
	            prop.setProperty(this.propertyName, this.propertyValue);

	            // save properties to project root folder
	            prop.store(output, null);


	        } catch (IOException io) {
	            io.printStackTrace();
	        }
		}


	}



	/**
	 * @description Method to modify the Properties in the configuration files.
	 * @param propertyName
	 * @param propertyValue
	 */
	public void modifyAndWriteProperties(String propertyName, String propertyValue) {
		
		this.propertyName = propertyName;
		this.propertyValue = propertyValue;
		
		
		//We open our configuration File
		try (InputStream input = new FileInputStream(projectDirectoryString + File.separator + "config.properties")) {

	       
			
	        //1. load a properties file
	        prop.load(input);
	        
	        //2. Now instead of calling
           //prop.setProperty(this.propertyName, this.propertyValue);
	        
	       //3. The Properties is loaded by prop.load
	       //3. We close the InputStream
	       input.close();
	       
	       

	    } catch (IOException ex) {
	        ex.printStackTrace();
	    }
		
		
		//4.Now we write down the modification
		try (OutputStream output = new FileOutputStream(projectDirectoryString + File.separator + "config.properties")) {

            

			//5.set the properties value
            prop.setProperty(this.propertyName, this.propertyValue);

            //6.save properties to project root folder
            prop.store(output, null);
            
            //7.Close the OutputStream
            output.close();


        } catch (IOException io) {
            io.printStackTrace();
        }
		
	}
	
	
	
	/**
	 * @description Method to remove Properties from configuration File.
	 * @param propertyName
	 */
	public void removeProperties(String propertyName) {
	
		//Set value of propertyName. This is the Properties we want to remove.
		this.propertyName = propertyName;
	
		// We open our configuration File
		try (InputStream input = new FileInputStream(projectDirectoryString + File.separator + "config.properties")) {

			// 1. load a properties file
			prop.load(input);

			//2. remove the properties
			prop.remove(this.propertyName);
			
			//save the modification by the config.properties where some Properties was deleted.
			prop.store(new FileOutputStream(projectDirectoryString + File.separator + "config.properties"), propertyName);
			
			//Close the input
			input.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	
		
	
		 
		
		
	}


}
