package com.smrc.javadesignpatterns.architectural.abstractdocument.domain;

import com.smrc.javadesignpatterns.architectural.abstractdocument.Document;
import com.smrc.javadesignpatterns.architectural.abstractdocument.Property;

import java.util.Optional;

public interface HasPrice extends Document {
  default Optional<Number> getPrice() {
    return Optional.ofNullable((Number) get(Property.PRICE.toString()));
  }
}