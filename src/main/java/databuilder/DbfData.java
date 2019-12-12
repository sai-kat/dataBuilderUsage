package databuilder;

import com.flipkart.databuilderframework.model.Data;
import com.flipkart.databuilderframework.model.DataAdapter;

public abstract class DbfData<T extends Data> extends DataAdapter<T> {
    protected DbfData(Class<T> tClass) {
        super(tClass);
    }
}
