package databuilder;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.flipkart.databuilderframework.engine.Utils;
import com.flipkart.databuilderframework.model.DataAdapter;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

@Slf4j
@Singleton
public class MapperUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    static  {

        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        registerSubTypes(mapper, "databuilder");
    }

    protected static void registerSubTypes(ObjectMapper mapper, String prefix) {
        Reflections reflections = new Reflections(prefix);
        reflections.getSubTypesOf(DataAdapter.class)
                .forEach(dataClass -> {
                    mapper.registerSubtypes(new NamedType(dataClass, Utils.name(dataClass)));
                    log.debug("Registered data: {} with name: {}", dataClass, Utils.name(dataClass));
                });
    }

    public static <T> T deserialize(byte[] data, Class<T> valueType) {
        if (data == null) {
            return null;
        }
        try {
            return mapper.readValue(data, valueType);
        } catch (Exception e) {
            return null;
        }
    }

    public static byte[] serialize(Object data) {
        if (data == null) {
            return null;
        }
        try {
            return mapper.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public static ObjectMapper getMapper() {
        return mapper;
    }

}