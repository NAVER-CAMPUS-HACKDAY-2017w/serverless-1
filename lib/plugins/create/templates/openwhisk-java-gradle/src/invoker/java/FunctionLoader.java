import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class FunctionLoader {

    private  Method mainMethod;

    public static void main(String args[]) throws Exception {
        String handler  = args[0];
        String data     = args[1];

        FunctionLoader functionLoader = new FunctionLoader();
        functionLoader.load(handler);

        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(data);

        JsonObject message = jsonElement.getAsJsonObject();
        JsonObject output = functionLoader.invokerHandler(message);
        if(output == null) {
            throw new NullPointerException("The action returned null");
        }

        System.out.println(output);
    }

    private void load(String entrypoint) throws ClassNotFoundException, NoSuchMethodException {
        Class<?> mainClass = Class.forName(entrypoint);
        Method m = mainClass.getMethod("main", new Class[]{ JsonObject.class });
        m.setAccessible(true);
        int modifiers = m.getModifiers();

        if (m.getReturnType() != JsonObject.class || !Modifier.isStatic(modifiers) || !Modifier.isPublic(modifiers)) {
            throw new NoSuchMethodException("main");
        }

        this.mainMethod = m;
    }

    private JsonObject invokerHandler(JsonObject arg) throws Exception {
        return (JsonObject) mainMethod.invoke(null, arg);
    }
}
