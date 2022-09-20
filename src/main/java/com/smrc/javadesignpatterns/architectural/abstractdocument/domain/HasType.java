package com.smrc.javadesignpatterns.architectural.abstractdocument.domain;

import com.smrc.javadesignpatterns.architectural.abstractdocument.Document;
import com.smrc.javadesignpatterns.architectural.abstractdocument.Property;

import java.util.Optional;


public interface HasType extends Document {
    default Optional<String> getType() {
        return Optional.ofNullable((String) get(Property.TYPE.toString()));
    }
}
