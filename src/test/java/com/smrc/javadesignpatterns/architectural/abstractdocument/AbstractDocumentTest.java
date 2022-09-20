package com.smrc.javadesignpatterns.architectural.abstractdocument;

import lombok.ToString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AbstractDocumentTest {

    private static final String KEY = "key";
    public static final String VALUE = "value";

    private static class DocumentImplementation extends AbstractDocument {

        protected DocumentImplementation(Map<String, Object> properties) {
            super(properties);
        }
    }

    private final DocumentImplementation document = new DocumentImplementation(new HashMap<>());

    @Test
    void shouldPutAndGetValue() {
        document.put(KEY, VALUE);
        Assertions.assertEquals(VALUE, document.get(KEY));
    }

    @Test
    void shouldRetrieveChildren() {
        List<Map<String, Object>> maps = List.of(Map.of(), Map.of());

        document.put(KEY, maps);

        Stream<DocumentImplementation> children = document.children(KEY, DocumentImplementation::new);
        List<DocumentImplementation> collect = document.children(KEY, DocumentImplementation::new).collect(Collectors.toList());
        System.out.println("collect = " + collect);
        Assertions.assertNotNull(children);
        Assertions.assertEquals(2, children.count());
    }

    @Test
    void shouldRetrieveEmptyStreamForNonExistingChildren() {
        var children = document.children(KEY, DocumentImplementation::new);
        Assertions.assertNotNull(children);
        Assertions.assertEquals(0, children.count());
    }

    @Test
    void shouldIncludePropsInToString() {
        var props = Map.of(KEY, (Object) VALUE);
        var document = new DocumentImplementation(props);
        System.out.println("document = " + document);
        Assertions.assertTrue(document.toString().contains(KEY));
        Assertions.assertTrue(document.toString().contains(VALUE));
    }
}
