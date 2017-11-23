package com.serverless;

import com.google.gson.JsonObject;

public class Handler {
    public static JsonObject main(JsonObject args) {
        String name = args.getAsJsonPrimitive("name").getAsString();
        JsonObject response = new JsonObject();
        response.addProperty("payload", "Hello, " + name);
        return response;
    }

}
