package com.WebApp.EmailServerClient.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;

import java.io.InputStream;
import java.util.Set;

public class SchemaValidator {
    public static ObjectMapper Validate(String SchemaPath, String ToValidate) throws JsonProcessingException {
        InputStream schemaAsStream = MainController.class.getClassLoader().getResourceAsStream(SchemaPath);
        JsonSchema schema = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7).getSchema(schemaAsStream);
        ObjectMapper om = new ObjectMapper();
        om.setPropertyNamingStrategy(PropertyNamingStrategy.UPPER_CAMEL_CASE);
        JsonNode jsonNode = om.readTree(ToValidate);

        Set<ValidationMessage> errors = schema.validate(jsonNode);
        String errorsCombined = "";
        for (ValidationMessage error : errors) {
            errorsCombined = errorsCombined.concat(error.toString() + "\n");
        }

        if (errors.size() > 0)
            throw new RuntimeException("Please fix your json! " + errorsCombined);

        return om;
    }
}
