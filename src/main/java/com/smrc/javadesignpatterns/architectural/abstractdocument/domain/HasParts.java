package com.smrc.javadesignpatterns.architectural.abstractdocument.domain;

import com.smrc.javadesignpatterns.architectural.abstractdocument.Document;
import com.smrc.javadesignpatterns.architectural.abstractdocument.Property;

import java.util.stream.Stream;

public interface HasParts extends Document {

  default Stream<Part> getParts() {
    return children(Property.PARTS.toString(), Part::new);
  }
}