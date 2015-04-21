package authoring.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * Parses a properties file given a String[] or Enum[] of properties and a file path.
 * 
 * @author Natalie
 *
 */
public class PropertiesFileParser {
    public static String[] loadProperties (String[] properties, String filePath) throws IOException {
        String[] ret = new String[properties.length];
        InputStream inputStream =
                PropertiesFileParser.class.getResourceAsStream(filePath);
        Properties props = new Properties();
        props.load(inputStream);
        inputStream.close();

        for (int i = 0; i < properties.length; i++) {
            ret[i] = props.getProperty(properties[i]);
        }
        return ret;
    }

    @SuppressWarnings("rawtypes")
    public static String[] loadProperties (Enum[] properties, String filePath) throws IOException {
        String[] stringProperties = new String[properties.length];
        for (int i = 0; i < properties.length; i++) {
            stringProperties[i] = properties[i].toString();
        }
        return loadProperties(stringProperties, filePath);
    }
}
