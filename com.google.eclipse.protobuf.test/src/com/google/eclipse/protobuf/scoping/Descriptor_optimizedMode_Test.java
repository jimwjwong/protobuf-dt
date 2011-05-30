/*
 * Copyright (c) 2011 Google Inc.
 *
 * All rights reserved. This program and the accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 *
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.google.eclipse.protobuf.scoping;

import static com.google.eclipse.protobuf.junit.matchers.EnumHasLiterals.hasLiterals;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.*;

import com.google.eclipse.protobuf.junit.core.XtextRule;
import com.google.eclipse.protobuf.protobuf.Enum;

/**
 * Tests for <code>{@link Descriptor#optimizedMode()}</code>.
 *
 * @author alruiz@google.com (Alex Ruiz)
 */
public class Descriptor_optimizedMode_Test {

  @Rule public XtextRule xtext = new XtextRule();
  
  private Descriptor descriptor;
  
  @Before public void setUp() {
    descriptor = xtext.getInstanceOf(Descriptor.class);
  }
  
  @Test public void should_return_enum_OptimizeMode() {
    Enum optimizedMode = descriptor.optimizedMode();
    assertThat(optimizedMode.getName(), equalTo("OptimizeMode"));
    assertThat(optimizedMode, hasLiterals("SPEED", "CODE_SIZE", "LITE_RUNTIME"));
  }
}