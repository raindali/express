package org.expressme.search.mapper;

import java.lang.reflect.Method;

import org.apache.lucene.document.Field;
import org.expressme.search.Index;
import org.expressme.search.SearchConfigException;
import org.expressme.search.Store;
import org.expressme.search.converter.Converter;
import org.expressme.search.converter.ConverterFactory;


/**
 * Mapping a JavaBean property with a Lucene Field.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
class FieldMapper {

    final Method getterMethod; // its getter method
    final Method setterMethod; // its setter method
    final Converter converter;

    final Field.Index index;
    final Field.Store store;
    final float boost;

    public FieldMapper(Method getterMethod, Method setterMethod,
            Store store, Index index,
            float boost)
    {
        this.getterMethod = getterMethod;
        this.setterMethod = setterMethod;
        this.converter = getConverter(getterMethod.getReturnType());
        this.index = toLuceneIndex(index);
        this.store = toLuceneStore(store);
        this.boost = boost;
    }

    Converter getConverter(Class<?> clazz) {
        Converter c = ConverterFactory.getInstance().getConverter(clazz);
        if (c==null)
            throw new SearchConfigException("Cannot find converter for property of type '" + clazz.getName() + "'");
        return c;
    }

    String convertToString(Object obj) {
        return converter.toString(obj);
    }

    Object convertToObject(String s) {
        return converter.toObject(s);
    }

    Field.Index toLuceneIndex(Index index) {
        if(index==Index.ANALYZED)
            return Field.Index.ANALYZED;
        if(index==Index.NOT_ANALYZED)
            return Field.Index.NOT_ANALYZED;
        if(index==Index.NO)
            return Field.Index.NO;
        throw new IllegalArgumentException("Enum value not found in '" + Index.class.getName() + "'");
    }

    Field.Store toLuceneStore(Store store) {
        if(store==Store.YES)
            return Field.Store.YES;
        if(store==Store.NO)
            return Field.Store.NO;
        throw new IllegalArgumentException("Enum value not found in '" + Store.class.getName() + "'");
    }
}
