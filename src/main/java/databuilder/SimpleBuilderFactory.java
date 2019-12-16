package databuilder;

import com.flipkart.databuilderframework.engine.DataBuilder;
import com.flipkart.databuilderframework.engine.DataBuilderFactory;
import com.flipkart.databuilderframework.engine.DataBuilderFrameworkException;
import com.flipkart.databuilderframework.engine.DataBuilderMetadataManager;
import com.flipkart.databuilderframework.model.DataBuilderMeta;
import com.google.inject.Inject;
import com.google.inject.Injector;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.reflections.Reflections;

import java.util.Set;

@Slf4j
public class SimpleBuilderFactory implements DataBuilderFactory {

    private final DataBuilderMetadataManager metadataManager;
    private final Injector injector;

    public SimpleBuilderFactory(Injector injector) {
        this.metadataManager = new DataBuilderMetadataManager();
        this.injector = injector;
        Reflections reflections = new Reflections("databuilder");
        Set<Class<? extends DataBuilder>> builders = reflections.getSubTypesOf(DataBuilder.class);
        for(val builder : builders) {
            try {
                metadataManager.register(builder);
            } catch (DataBuilderFrameworkException e) {
                log.error(e.getMessage());
            }
        }
    }

    @Override
    public DataBuilder create(DataBuilderMeta dataBuilderMeta) {
        try {
            DataBuilder builder =  injector.getInstance(metadataManager.getDataBuilderClass(dataBuilderMeta.getName()));
            builder.setDataBuilderMeta(dataBuilderMeta);
            return builder;
        } catch (Exception e) {
            throw new DataBuilderError(e.getMessage());
        }
    }

    @Override
    public DataBuilderFactory immutableCopy() {
        throw new IllegalAccessError("Copy not supported on this class");
    }
}

