package com.syllab.boutique;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.junit.jupiter.api.DisplayNameGenerator.Standard;

public class NommageRoyOsherove extends Standard {
  @Override
  public String generateDisplayNameForMethod(Class<?> testClass, Method testMethod) {
    var nomMethode = super.generateDisplayNameForMethod(testClass, testMethod)
        .replaceAll("\\([^\\)]*\\)", "");
    var parties = Arrays.stream(nomMethode.split("_"))
        .map(s -> String.join(" ", s.split("(?<!(^|[A-Z0-9]))(?=[A-Z0-9])|(?<!^)(?=[A-Z0-9][a-z])")).toLowerCase())
        .toArray();

    switch (parties.length) {
      case 1:
        return nomMethode;
      case 2:
        return String.format("(%s) %s", parties[0], parties[1]);
      default:
        return String.format("(%s) %s -> %s", parties[0], parties[1], parties[2]);
    }
  }
}