// Copyright 2000-2020 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package org.jetbrains.plugins.groovy.lang.resolve


import groovy.transform.CompileStatic
import org.jetbrains.plugins.groovy.util.GroovyLatestTest
import org.jetbrains.plugins.groovy.util.HighlightingTest
import org.junit.Test

@CompileStatic
class TupleConstructorTest extends GroovyLatestTest implements HighlightingTest {

  @Test
  void 'many constructors'() {
    highlightingTest """
@groovy.transform.TupleConstructor
class Rr {
    String actionType = ""
    long referrerCode;
    String referrerUrl;
}

@groovy.transform.CompileStatic
static void main(String[] args) {
    new Rr("")
    new Rr("", 1)
    new Rr("", 1, "groovy")
}
"""
  }

  @Test
  void 'many constructors with excludes'() {
    highlightingTest """
@groovy.transform.TupleConstructor(excludes = ['actionType'])
class Rr {
    String actionType = ""
    long referrerCode;
    String referrerUrl;
}

@groovy.transform.CompileStatic
static void main(String[] args) {
    new Rr()
    new Rr(1)
    new Rr(1, "groovy")
}
"""
  }

  @Test
  void 'many constructors with includes'() {
    highlightingTest """
@groovy.transform.TupleConstructor(includes = ['actionType', 'referrerUrl'])
class Rr {
    String actionType = ""
    long referrerCode;
    String referrerUrl;
}

@groovy.transform.CompileStatic
static void main(String[] args) {
    new Rr()
    new Rr("")
    new Rr("a", "groovy")
}
"""
  }


  @Test
  void 'many constructors with raw includes'() {
    highlightingTest """
@groovy.transform.TupleConstructor(includes = 'actionType,  referrerUrl ')
class Rr {
    String actionType = ""
    long referrerCode;
    String referrerUrl;
}

@groovy.transform.CompileStatic
static void main(String[] args) {
    new Rr()
    new Rr("")
    new Rr("a", "groovy")
}
"""
  }
}
