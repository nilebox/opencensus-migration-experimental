package io.opencensus.trace.opentelemetry;

import io.grpc.Context;
import io.opencensus.trace.Ctx;

public class OpenTelemetryCtx implements Ctx {

  // TODO migrate to new context following this PR: https://github.com/open-telemetry/opentelemetry-java/pull/1751
  private final io.grpc.Context context;

  public OpenTelemetryCtx(Context context) {
    this.context = context;
  }

  Context getContext() {
    return context;
  }

  @Override
  public Ctx attach() {
    return new OpenTelemetryCtx(context.attach());
  }

  @Override
  public void detach(Ctx ctx) {
    OpenTelemetryCtx impl = (OpenTelemetryCtx) ctx;
    context.detach(impl.context);
  }
}
