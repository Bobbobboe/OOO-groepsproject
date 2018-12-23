package model.db;

import java.io.*;
import java.util.Properties;

public class PropertiesFileLoader {
    private static final String EVALUATIONPROPERTIESFILE = "src/model/db/evaluation.properties";

    public static Properties loadEvalutationProperties() {
        Properties properties = new Properties();
        try {
            InputStream inputStream = new FileInputStream(EVALUATIONPROPERTIESFILE);
            properties.load(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public static void saveEvaluationProperties(Properties properties) {
        try {
            OutputStream out = new FileOutputStream(EVALUATIONPROPERTIESFILE);
            properties.store(out, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
