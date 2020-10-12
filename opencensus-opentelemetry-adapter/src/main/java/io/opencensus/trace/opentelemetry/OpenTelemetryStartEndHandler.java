package io.opencensus.trace.opentelemetry;

import io.opencensus.implcore.trace.RecordEventsSpanImpl;
import io.opencensus.implcore.trace.RecordEventsSpanImpl.StartEndHandler;

public class OpenTelemetryStartEndHandler implements StartEndHandler {
  private final SpanCache spanCache;

  public OpenTelemetryStartEndHandler(SpanCache spanCache) {
    this.spanCache = spanCache;
  }

  @Override
  public void onStart(RecordEventsSpanImpl span) {
    io.opentelemetry.trace.Span otSpan = spanCache.toOtelSpan(span);
    // TODO: Do we need to do anything else here?
  }

  @Override
  public void onEnd(RecordEventsSpanImpl span) {
    io.opentelemetry.trace.Span otSpan = spanCache.toOtelSpan(span);
    otSpan.end();
  }
}
