package io.opencensus.trace;


// NOTE: this is copied for prototyping with unreleased library from
// https://github.com/nilebox/opencensus-java/blob/context-experimental/api/src/main/java/io/opencensus/trace/Ctx.java
public interface Ctx {
  Ctx attach();
  void detach(Ctx ctx);
}
