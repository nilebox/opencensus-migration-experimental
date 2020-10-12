package io.opencensus.trace;


// NOTE: this is copied for prototyping with unreleased library from
// https://github.com/nilebox/opencensus-java/blob/context-experimental/api/src/main/java/io/opencensus/trace/ContextManager.java
public interface ContextManager {
  Ctx currentContext();
  Ctx withValue(Ctx ctx, Span span);
  Span getValue(Ctx ctx);
}
