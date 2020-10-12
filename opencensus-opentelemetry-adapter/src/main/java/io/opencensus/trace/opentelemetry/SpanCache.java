package io.opencensus.trace.opentelemetry;

import io.opencensus.trace.Span;
import java.util.HashMap;
import java.util.Map;

public class SpanCache {

  Map<Span, io.opentelemetry.trace.Span> ocToOt = new HashMap<>();
  // This might be useful in 2 cases:
  // - extracting existing "current" OC span from the OT context
  // - creating new "OC" span for the original OT span
  Map<io.opentelemetry.trace.Span, Span> otToOc = new HashMap<>();

  public io.opentelemetry.trace.Span toOtelSpan(Span span) {
    io.opentelemetry.trace.Span otSpan = ocToOt.get(span);
    if (otSpan == null) {
      otSpan = SpanConverter.toOtelSpan(span);
      otToOc.put(otSpan, span);
      ocToOt.put(span, otSpan);
    }
    return otSpan;
  }

  public Span fromOtelSpan(io.opentelemetry.trace.Span otSpan) {
    Span span = otToOc.get(otSpan);
    if (span == null) {
      span = SpanConverter.fromOtelSpan(otSpan);
      ocToOt.put(span, otSpan);
      otToOc.put(otSpan, span);
    }
    return span;
  }

  // TODO: implement deletion of spans from the cache
}
