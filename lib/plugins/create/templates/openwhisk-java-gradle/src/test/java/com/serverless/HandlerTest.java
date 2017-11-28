package com.serverless;

import com.google.gson.JsonObject;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HandlerTest {
    @Test
    public void testFunction() {
        JsonObject object = new JsonObject();
        object.addProperty("name", "World");
        JsonObject main = Handler.main(object);
        assertThat(main.toString()).isEqualTo("{\"payload\":\"Hello, World\"}");
    }
}
