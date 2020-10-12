package io.opencensus.trace.opentelemetry;

import io.grpc.Context;
import io.opencensus.implcore.trace.RecordEventsSpanImpl;
import io.opencensus.trace.ContextManager;
import io.opencensus.trace.Ctx;
import io.opencensus.trace.Span;
import io.opencensus.trace.SpanContext;
import io.opencensus.trace.SpanId;
import io.opencensus.trace.TraceId;
import io.opencensus.trace.TraceOptions;
import io.opencensus.trace.export.SpanData;
import io.opentelemetry.OpenTelemetry;
import io.opentelemetry.trace.TracingContextUtils;

public class OpenTelemetryContextManager implements ContextManager {

  @Override
  public Ctx currentContext() {
    return wrapContext(Context.current());
  }

  @Override
  public Ctx withValue(Ctx ctx, Span span) {
    OpenTelemetryCtx openTelemetryCtx = (OpenTelemetryCtx) ctx;
    // TODO: One problem with this approach is that we will never invoke "end" on the OpenTelemetry span,
    // so it will never be exported via the OpenTelemetry pipeline.
    // This might be okay if the OpenCensus pipeline will export the original (non-fake) OpenCensus span though.
    return wrapContext(TracingContextUtils.withSpan(toOtelSpan(span), unwrapContext(openTelemetryCtx)));
  }

  @Override
  public Span getValue(Ctx ctx) {
    return fromOtelSpan(TracingContextUtils.getSpan(unwrapContext(ctx)));
  }

  private static Ctx wrapContext(Context context) {
    return new OpenTelemetryCtx(context);
  }

  private static Context unwrapContext(Ctx ctx) {
    return ((OpenTelemetryCtx)ctx).getContext();
  }

  private static io.opentelemetry.trace.Span toOtelSpan(Span span) {
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

  private static Span fromOtelSpan(io.opentelemetry.trace.Span otelSpan) {
    if (otelSpan == null) {
      return null;
    }
    TraceId traceId = TraceId.fromBytes(otelSpan.getContext().getTraceIdBytes());
    SpanId spanId = SpanId.fromBytes(otelSpan.getContext().getSpanIdBytes());
    SpanContext spanContext = SpanContext.create(traceId, spanId, TraceOptions.DEFAULT);
    return new FakeSpan(spanContext);
  }
}
