package authoring.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
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
        return loadProperties(convertEnumToStringArray(properties), filePath);
    }
    
    public static String[] alphabeticallyLoadProperties (String[] properties, String filePath) throws IOException {
        String[] sortedProperties = properties.clone();
        Arrays.sort(sortedProperties);
        return loadProperties(sortedProperties, filePath);
    }

    @SuppressWarnings("rawtypes")
    public static String[] alphabeticallyLoadProperties (Enum[] properties, String filePath) throws IOException {
        return alphabeticallyLoadProperties(convertEnumToStringArray(properties), filePath);
    }
    
    @SuppressWarnings("rawtypes")
    public static String[] convertEnumToStringArray(Enum[] enums) {
        String[] ret = new String[enums.length];
        for (int i = 0; i < enums.length; i++) {
            ret[i] = enums[i].toString();
        }
        return ret;
    }
}
