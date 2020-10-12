package io.opencensus.trace.opentelemetry;

import io.grpc.Context;
import io.opencensus.trace.ContextManager;
import io.opencensus.trace.Ctx;
import io.opencensus.trace.Span;
import io.opentelemetry.trace.TracingContextUtils;

public class OpenTelemetryContextManager implements ContextManager {
  private final SpanCache spanCache;

  public OpenTelemetryContextManager(SpanCache spanCache) {
    this.spanCache = spanCache;
  }

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
    return wrapContext(TracingContextUtils.withSpan(spanCache.toOtelSpan(span), unwrapContext(openTelemetryCtx)));
  }

  @Override
  public Span getValue(Ctx ctx) {
    return spanCache.fromOtelSpan(TracingContextUtils.getSpan(unwrapContext(ctx)));
  }

  private static Ctx wrapContext(Context context) {
    return new OpenTelemetryCtx(context);
  }

  private static Context unwrapContext(Ctx ctx) {
    return ((OpenTelemetryCtx)ctx).getContext();
  }

}
