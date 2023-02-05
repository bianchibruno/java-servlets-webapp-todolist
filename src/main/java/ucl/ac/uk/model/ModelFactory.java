package ucl.ac.uk.model;

import java.io.File;
import java.io.IOException;

public class ModelFactory {
    private static Model model;

    //This is used to make sure we just generate one Model.
    public static Model getModel() throws IOException {
        if (model == null) {
            model = new Model();
            model.readMainFile();
        }
        return model;
    }
}
