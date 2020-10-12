package io.opencensus.trace.opentelemetry;

import io.opencensus.trace.ContextManager;

public class OpenTelemetryAdapter {
  private OpenTelemetryAdapter() {
  }

  public static void register() {
    ContextManager cm = new OpenTelemetryContextManager();
    // After new OpenCensus release, we will be available to register a custom context manager here:
    // CtxUtils.setContextManager(cm);
    // See https://github.com/nilebox/opencensus-java/blob/context-experimental/api/src/main/java/io/opencensus/trace/unsafe/CtxUtils.java
  }
}
