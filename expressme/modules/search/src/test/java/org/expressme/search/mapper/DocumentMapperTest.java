package org.expressme.search.mapper;

import org.expressme.search.SearchConfigException;
import org.expressme.search.SearchableBean;
import org.junit.Test;

public class DocumentMapperTest {

    @Test(expected=SearchConfigException.class)
    public void testDocumentMapperWithoutDefaultConstructor() {
        new DocumentMapper<SearchableBeanWithoutDefaultConstructor>(SearchableBeanWithoutDefaultConstructor.class);
    }

    @Test(expected=SearchConfigException.class)
    public void testDocumentMapperWithoutId() {
        new DocumentMapper<SearchableBeanWithoutId>(SearchableBeanWithoutId.class);
    }

    @Test(expected=SearchConfigException.class)
    public void testDocumentMapperWithTwoIds() {
        new DocumentMapper<SearchableBeanWithTwoIds>(SearchableBeanWithTwoIds.class);
    }

    @Test(expected=SearchConfigException.class)
    public void testDocumentMapperMissingSetter() {
        new DocumentMapper<SearchableBeanMissingSetter>(SearchableBeanMissingSetter.class);
    }

    @Test
    public void testDocumentMappeOk() {
        new DocumentMapper<SearchableBean>(SearchableBean.class);
    }

}
