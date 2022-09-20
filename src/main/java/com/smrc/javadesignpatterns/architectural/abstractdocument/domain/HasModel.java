package com.smrc.javadesignpatterns.architectural.abstractdocument.domain;

import com.smrc.javadesignpatterns.architectural.abstractdocument.Document;
import com.smrc.javadesignpatterns.architectural.abstractdocument.Property;

import java.util.Optional;

public interface HasModel extends Document {
  default Optional<String> getModel() {
    return Optional.ofNullable((String) get(Property.MODEL.toString()));
  }
}