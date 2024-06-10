package co.istad.dealkh.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.IOException;
import java.util.List;

/**
 * JsonListConverter is a generic JPA attribute converter that converts a list of objects to a JSON string
 * and vice versa. This converter is designed to work with any type of object.
 *
 * <p>This class uses the {@link Converter} annotation to indicate that it is a JPA converter.</p>
 *
 * @param <T> the type of objects in the list
 */
@Converter
public class JsonListConverter<T> implements AttributeConverter<List<T>, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final Class<T> clazz;

    /**
     * Constructs a new JsonListConverter with the specified class type.
     *
     * @param clazz the class type of the objects in the list
     */
    public JsonListConverter(Class<T> clazz) {
        this.clazz = clazz;
    }

    /**
     * Converts a list of objects to a JSON string for database storage.
     *
     * @param attribute the list of objects to convert
     * @return the JSON string representation of the list
     * @throws IllegalArgumentException if an error occurs during conversion
     */
    @Override
    public String convertToDatabaseColumn(List<T> attribute) {
        if (attribute == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting list to JSON string", e);
        }
    }

    /**
     * Converts a JSON string from the database to a list of objects.
     *
     * @param dbData the JSON string from the database
     * @return the list of objects
     * @throws IllegalArgumentException if an error occurs during conversion
     */
    @Override
    public List<T> convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        try {
            return objectMapper.readValue(dbData, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON string to list", e);
        }
    }
}