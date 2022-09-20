package com.smrc.javadesignpatterns.architectural.abstractdocument.domain;

import com.smrc.javadesignpatterns.architectural.abstractdocument.AbstractDocument;
import lombok.ToString;

import java.util.Map;

@ToString
public class Part extends AbstractDocument implements HasType, HasModel, HasPrice {
    public Part(Map<String, Object> properties) {
        super(properties);
    }
}
