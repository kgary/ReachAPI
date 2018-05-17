package edu.asu.heal.core.api.hal;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.Properties;

public class HALHelperFactory {
    private static HALHelper _theHALGenerator;

    private static Properties properties;

    static {
        try{
            InputStream temp = HALHelperFactory.class.getResourceAsStream("hal.properties");
            properties = new Properties();
            properties.load(temp);
        }catch (IOException ie){
            System.out.println("SOME ERROR IN LOADING MODEL PROPERTIES");
            ie.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static HALHelper getHALGenerator(){
        if(_theHALGenerator == null){
            return HALHelperFactory.initializeHALGenerator(properties.getProperty("model.HALGeneratorAPI"));
        }
        return _theHALGenerator;
    }

    private static HALHelper initializeHALGenerator(String property) {
        try {

            Class<?> HALGeneratorClass = Class.forName(property);
            Constructor<?> HALGeneratorClassConstructor = HALGeneratorClass.getConstructor();
            _theHALGenerator = (HALHelper) HALGeneratorClassConstructor.newInstance();

            return _theHALGenerator;
        } catch (ClassNotFoundException ce) {
            System.out.println(ce.getMessage());
            return null;
        } catch (Exception ex) {
            System.out.println("Exception occurred: " + ex.getMessage());
            return null;
        }
    }
}
