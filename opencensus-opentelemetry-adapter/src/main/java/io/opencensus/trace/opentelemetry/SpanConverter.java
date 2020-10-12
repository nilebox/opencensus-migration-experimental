package io.opencensus.trace.opentelemetry;

import io.opencensus.implcore.trace.RecordEventsSpanImpl;
import io.opencensus.trace.Span;
import io.opencensus.trace.SpanContext;
import io.opencensus.trace.SpanId;
import io.opencensus.trace.TraceId;
import io.opencensus.trace.TraceOptions;
import io.opencensus.trace.export.SpanData;
import io.opentelemetry.OpenTelemetry;

public class SpanConverter {

  public static io.opentelemetry.trace.Span toOtelSpan(Span span) {
    RecordEventsSpanImpl spanImpl = (RecordEventsSpanImpl) span;
    SpanData spanData = spanImpl.toSpanData();
    io.opentelemetry.trace.Span.Builder builder = OpenTelemetry
        .getTracer("io.opencensus.trace.opentelemetry.OpenTelemetryContextManager")
        .spanBuilder(spanData.getName())
        .setStartTimestamp(spanData.getStartTimestamp().getNanos());

    // TODO: convert remaining metadata
    // if (attributes != null) {
    //   for (Entry<String, AttributeValue> attribute : attributes.entrySet()) {
    //     builder.setAttribute(attribute.getKey(),
    //         attribute.getValue().match(stringAttributeConverter, booleanAttributeConverter, longAttributeConverter,
    //             doubleAttributeConverter, defaultAttributeConverter));
    //   }
    // }
    return builder.startSpan();
  }

  public static Span fromOtelSpan(io.opentelemetry.trace.Span otelSpan) {
    if (otelSpan == null) {
      return null;
    }
    TraceId traceId = TraceId.fromBytes(otelSpan.getContext().getTraceIdBytes());
    SpanId spanId = SpanId.fromBytes(otelSpan.getContext().getSpanIdBytes());
    SpanContext spanContext = SpanContext.create(traceId, spanId, TraceOptions.DEFAULT);
    return new FakeSpan(spanContext);
  }

}
