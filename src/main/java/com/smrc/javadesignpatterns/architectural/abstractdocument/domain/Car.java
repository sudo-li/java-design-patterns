package com.smrc.javadesignpatterns.architectural.abstractdocument.domain;

import com.smrc.javadesignpatterns.architectural.abstractdocument.AbstractDocument;

import java.util.Map;

public class Car extends AbstractDocument implements HasModel, HasPrice, HasParts {

    public Car(Map<String, Object> properties) {
        super(properties);
    }
}
