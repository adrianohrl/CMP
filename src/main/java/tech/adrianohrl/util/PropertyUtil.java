package tech.adrianohrl.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Locale;
import java.util.Properties;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class PropertyUtil {
    
    private static final String PROPERTY_FILENAME = "stile.properties";
    
    private PropertyUtil() {
    }
    
    public static String getResourceConfigFolderPath() {
        ClassLoader classLoader = PropertyUtil.class.getClassLoader();
        URL url = classLoader.getResource("config");
        if (url == null) {
            String projectName = PropertyUtil.getProperty("project.name");
            System.err.println("The " + projectName + " project is not configured properly: the \"config\" folder does not exist.");
        }
        return url != null ? url.getFile() : "";
    }
    
    public static Locale getDefaultLocale() {
        String language = PropertyUtil.getProperty("general.default.locale.language");
        String country = PropertyUtil.getProperty("general.default.locale.country");
        return new Locale(language, country);
    }
    
    public static String getPathToSave() {
        String filepath = PropertyUtil.getProperty("general.default.filepath");
        return !filepath.startsWith("/") ? System.getProperty("user.home")  + "/" + filepath : filepath;
    }
    
    public static String getProperty(String property) {
        Properties properties = new Properties();
        ClassLoader classLoader = PropertyUtil.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(PROPERTY_FILENAME);
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            String projectName = PropertyUtil.getProperty("project.name");
            throw new RuntimeException("The " + projectName + " project is not configured properly: the \"config.properties\" file does not exist.");
        }
        return properties.getProperty(property);
    }
    
}
