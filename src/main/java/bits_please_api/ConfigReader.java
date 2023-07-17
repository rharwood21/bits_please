package bits_please_api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

class ConfigReader {
    private static final String CONFIG_FILE_NAME = "config.ini";
    private static final String CONFIG_FILE_PATH = getConfigFilePath();
    private Properties properties;

    ConfigReader() {
        properties = new Properties();
        try {
            FileInputStream inputStream = new FileInputStream(CONFIG_FILE_PATH);
            properties.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception if the configuration file cannot be read
        }
    }

    String getAPIKey() {
        return properties.getProperty("api.key");
    }
    String getInitializeClientEndpoint(){
        return properties.getProperty("api.endpoint.initialize-client");
    }
    String getCreateQuestionsEndpoint(){
        return properties.getProperty("api.endpoint.create-questions");
    }
    String getModifyQuestionEndpoint(){
        return properties.getProperty("api.endpoint.modify-question");
    }
    String getRetrieveQuestionEndpoint(){
        return properties.getProperty("api.endpoint.retrieve-question");
    }

    Properties getProperties(){
        return properties;
    }
    void updateConfigFileIfNeeded(String clientUuid) {
        String persistSession = properties.getProperty("user.persist.session");
        if (persistSession != null && persistSession.equalsIgnoreCase("true")) {
            String uuid = properties.getProperty("user.uuid");
            if (uuid == null || uuid.isEmpty()) {
                properties.setProperty("user.uuid", clientUuid);
                try {
                    FileOutputStream outputStream = new FileOutputStream(CONFIG_FILE_PATH);
                    properties.store(outputStream, null);
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    // Handle the exception if the configuration file cannot be written
                }
            }
        }
    }
    private static String getConfigFilePath() {
        Path currentPath = Paths.get("");
        String absolutePath = currentPath.toAbsolutePath().toString();
        return absolutePath + File.separator + CONFIG_FILE_NAME;
    }
}

