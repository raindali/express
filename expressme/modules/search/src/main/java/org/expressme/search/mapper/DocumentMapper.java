package org.expressme.search.mapper;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.expressme.search.SearchConfigException;
import org.expressme.search.SearchException;
import org.expressme.search.SearchableId;
import org.expressme.search.SearchableProperty;
import org.expressme.search.Store;

/**
 * Mapping object with Lucene Document.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 * 
 * @param <T> Generic object type.
 */
public class DocumentMapper<T> {

    public static final int MAX_CHARS = 255;

    final Class<T> clazz;

    private final String id;
    private final FieldMapper idMapper;
    private final List<String> propertyList;
    private final Map<String, FieldMapper> map = new HashMap<String, FieldMapper>();
    private final String[] searchableProperties;
    private final BooleanClause.Occur[] occurs;

    public String getId() {
        return id;
    }

    public FieldMapper getIdMapper() {
        return idMapper;
    }

    public List<String> getPropertyList() {
        return propertyList;
    }

    public Map<String, FieldMapper> getMap() {
        return map;
    }

    public String[] getSearchableProperties() {
        return searchableProperties;
    }

    public BooleanClause.Occur[] getOccurs() {
        return occurs;
    }

    public BooleanClause.Occur[] getOccurs(int len) {
        if (occurs.length==len)
            return occurs;
        BooleanClause.Occur[] os = new BooleanClause.Occur[len];
        for (int i=0; i<len; i++)
            os[i] = BooleanClause.Occur.SHOULD;
        return os;
    }

    public DocumentMapper(Class<T> clazz) {
        this.clazz = clazz;
        checkConstructor();
        List<Method[]> props = findGettersAndSetters();
        this.idMapper = findIdFieldMapper(props);
        this.id = getGetterName(idMapper.getterMethod);
        List<FieldMapper> propMappers = findPropFieldMappers(props);
        List<String> searchablePropertyList = new ArrayList<String>();
        for (FieldMapper mapper : propMappers) {
            String name = getGetterName(mapper.getterMethod);
            this.map.put(name, mapper);
            if (mapper.index==org.apache.lucene.document.Field.Index.ANALYZED || mapper.index==org.apache.lucene.document.Field.Index.NOT_ANALYZED) {
                searchablePropertyList.add(name);
            }
        }
        this.searchableProperties = searchablePropertyList.toArray(new String[searchablePropertyList.size()]);
        this.occurs = new BooleanClause.Occur[searchableProperties.length];
        for (int i=0; i<this.occurs.length; i++)
            this.occurs[i] = BooleanClause.Occur.SHOULD;
        // for quickly get keySet:
        this.propertyList = new ArrayList<String>();
        this.propertyList.addAll(map.keySet());
    }

    List<FieldMapper> findPropFieldMappers(List<Method[]> props) {
        List<FieldMapper> mappers = new ArrayList<FieldMapper>(props.size());
        for (Method[] ms : props) {
            SearchableProperty sp = ms[0].getAnnotation(SearchableProperty.class);
            if (sp!=null) {
                mappers.add(new FieldMapper(ms[0], ms[1], sp.store(), sp.index(), sp.boost()));
            }
        }
        return mappers;
    }

    FieldMapper findIdFieldMapper(List<Method[]> props) {
        FieldMapper mapper = null;
        for (Method[] ms : props) {
            SearchableId id = ms[0].getAnnotation(SearchableId.class);
            if (id!=null) {
                if (mapper!=null)
                    throw new SearchConfigException("Cannot mark more than one @SearchableId in one bean.");
                mapper = new FieldMapper(ms[0], ms[1], Store.YES, org.expressme.search.Index.NOT_ANALYZED, 1.0f);
            }
        }
        if (mapper==null)
            throw new SearchConfigException("No @SearchableId found.");
        return mapper;
    }

    List<Method[]> findGettersAndSetters() {
        Method[] methods = clazz.getMethods();
        List<Method[]> props = new ArrayList<Method[]>();
        for(Method method : methods) {
            if(isGetter(method)) {
                Method setter = findSetterByGetter(method, methods);
                if(setter==null)
                    throw new SearchConfigException("No setter method found while getter method '" + method.getName() + "()' has Searchable annotation.");
                props.add(new Method[] { method, setter });
            }
        }
        return props;
    }

    void checkConstructor() {
        try {
            clazz.newInstance();
        }
        catch (Exception e) {
            throw new SearchConfigException("Cannot instanciate class '" + clazz.getName() + "', missing public default constructor?", e);
        }
    }

    public String getIdValue(T object) {
        return invokeGetter(object, idMapper);
    }

    public Document object2Document(T object) {
        Document doc = new Document();
        // create field for id:
        doc.add(createField(object, id, idMapper));
        // create field for property:
        for(String name : propertyList) {
            FieldMapper fm = map.get(name);
            doc.add(createField(object, name, fm));
        }
        return doc;
    }

    Field createField(Object object, String name, FieldMapper fm) {
        String value = invokeGetter(object, fm);
        Field f = new Field(name, value, fm.store, fm.index);
        f.setBoost(fm.boost);
        return f;
    }

    String invokeGetter(Object object, FieldMapper fm) {
        try {
            Object obj = fm.getterMethod.invoke(object);
            return fm.convertToString(obj);
        }
        catch (IllegalArgumentException e) {
            throw new SearchException("Cannot invoke method: " + fm.getterMethod, e);
        }
        catch (IllegalAccessException e) {
            throw new SearchException("Cannot invoke method: " + fm.getterMethod, e);
        }
        catch (InvocationTargetException e) {
            throw new SearchException("Cannot invoke method: " + fm.getterMethod, e);
        }
        catch (RuntimeException e) {
            throw new SearchException(e);
        }
    }

    public T documentToObject(Document doc, Analyzer analyzer, Highlighter highlighter) throws IOException, InvalidTokenOffsetsException {
        T t;
        try {
            t = clazz.newInstance();
        }
        catch(InstantiationException e) {
            throw new SearchException("Cannot instaniate class: " + clazz.getName(), e);
        }
        catch(IllegalAccessException e) {
            throw new SearchException("Cannot instaniate class: " + clazz.getName(), e);
        }
        // set id:
        setByField(t, idMapper, doc.get(id));
        // set properties:
        for(String name : propertyList) {
            FieldMapper fm = map.get(name);
            String value = doc.get(name);
            if(value!=null) {
                String v = value;
                if (fm.index==Field.Index.ANALYZED && highlighter!=null) {
                    // create highlight:
                    v = highlighter.getBestFragment(analyzer, name, value);
                    if (v==null)
                        v = value.length() > MAX_CHARS ?
                                value.substring(0, MAX_CHARS)
                                : value;
                }
                setByField(t, fm, v);
            }
        }
        return t;
    }

    private void setByField(Object t, FieldMapper fm, String value) {
        if(value!=null) {
            Object obj = fm.convertToObject(value);
            try {
                fm.setterMethod.invoke(t, new Object[] { obj });
            }
            catch(IllegalArgumentException e) {
                throw new SearchException("Cannot invoke method: " + fm.setterMethod, e);
            }
            catch(IllegalAccessException e) {
                throw new SearchException("Cannot invoke method: " + fm.setterMethod, e);
            }
            catch(InvocationTargetException e) {
                throw new SearchException("Cannot invoke method: " + fm.setterMethod, e);
            }
        }
    }

    boolean isGetter(Method method) {
        boolean isId = method.isAnnotationPresent(SearchableId.class);
        boolean isProp = method.isAnnotationPresent(SearchableProperty.class);
        if (isId && isProp)
            throw new SearchConfigException("Cannot use both @SearchableId and @SearchableProperty on the same method.");
        if (!isId && !isProp)
            return false;
        if(method.getParameterTypes().length!=0)
            return false;
        Class<?> ret = method.getReturnType();
        if(ret==void.class)
            return false;
        String name = method.getName();
        if(ret==Boolean.class || ret==boolean.class) {
            if(name.startsWith("is")) {
                return name.length()>=3 && Character.isUpperCase(name.charAt(2));
            }
        }
        return name.startsWith("get") && name.length()>=4 && Character.isUpperCase(name.charAt(3));
    }

    Method findSetterByGetter(Method getter, Method[] all) {
        String name = getGetterName(getter);
        String setterName = "set" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
        for (Method m : all) {
            if (isSetter(m, setterName))
                return m;
        }
        return null;
    }

    boolean isSetter(Method m, String setterName) {
        if (! m.getName().equals(setterName))
            return false;
        if(m.getReturnType()!=void.class)
            return false;
        int mod = m.getModifiers();
        return Modifier.isPublic(mod) && !Modifier.isStatic(mod);
    }

    String getGetterName(Method getter) {
        String name = getter.getName();
        if(name.startsWith("is"))
            name = name.substring(2);
        else
            name = name.substring(3);
        char ch = Character.toLowerCase(name.charAt(0));
        return ch + name.substring(1);
    }
}
