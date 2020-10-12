package io.opencensus.trace.opentelemetry;

import io.opencensus.implcore.trace.RecordEventsSpanImpl.StartEndHandler;
import io.opencensus.trace.ContextManager;
import io.opencensus.trace.TraceComponent;

public class OpenTelemetryAdapter {
  private OpenTelemetryAdapter() {
  }

  public static void register() {
    SpanCache spanCache = new SpanCache();
    StartEndHandler startEndHandler = new OpenTelemetryStartEndHandler(spanCache);
    TraceComponent traceComponent = new OpenTelemetryTraceComponent();
    ContextManager cm = new OpenTelemetryContextManager(spanCache);
    // After new OpenCensus release, we will be available to register a custom context manager here:
    // CtxUtils.setContextManager(cm);
    // See https://github.com/nilebox/opencensus-java/blob/context-experimental/api/src/main/java/io/opencensus/trace/unsafe/CtxUtils.java
  }
}
