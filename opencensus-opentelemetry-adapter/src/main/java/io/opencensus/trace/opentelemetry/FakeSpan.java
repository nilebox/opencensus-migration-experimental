package io.opencensus.trace.opentelemetry;

import io.opencensus.trace.Annotation;
import io.opencensus.trace.AttributeValue;
import io.opencensus.trace.EndSpanOptions;
import io.opencensus.trace.Link;
import io.opencensus.trace.Span;
import io.opencensus.trace.SpanContext;
import java.util.EnumSet;
import java.util.Map;

public class FakeSpan extends Span {

  // Same as in RecordEventsSpanImpl
  private static final EnumSet<Span.Options> RECORD_EVENTS_SPAN_OPTIONS =
      EnumSet.of(Span.Options.RECORD_EVENTS);

  protected FakeSpan(SpanContext context) {
    super(context, RECORD_EVENTS_SPAN_OPTIONS);
  }

  @Override
  public void addAnnotation(String description, Map<String, AttributeValue> attributes) {
    
  }

  @Override
  public void addAnnotation(Annotation annotation) {

  }

  @Override
  public void addLink(Link link) {

  }

  @Override
  public void end(EndSpanOptions options) {

  }
}
