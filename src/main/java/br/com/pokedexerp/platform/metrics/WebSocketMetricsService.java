package br.com.pokedexerp.platform.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class WebSocketMetricsService {

    private final MeterRegistry registry;

    private final AtomicInteger activeConnections = new AtomicInteger(0);

    private final ConcurrentMap<String, Counter> actionCounters = new ConcurrentHashMap<>();

    private final ConcurrentMap<String, Timer> actionTimers = new ConcurrentHashMap<>();

    public WebSocketMetricsService(MeterRegistry registry) {
        this.registry = registry;
    }

    @PostConstruct
    public void initMetrics() {
        Gauge.builder("websocket_active_sessions", activeConnections, AtomicInteger::get)
                .description("Number of websocket connections active")
                .register(registry);
    }


    public void incrementConnections() {
        activeConnections.incrementAndGet();
    }

    public void decrementConnections() {
        activeConnections.decrementAndGet();
    }

    public void trackMessage(String event) {
        actionCounters.computeIfAbsent(event, a ->
                Counter.builder("websocket_messages_total")
                        .tag("event", a)
                        .description("Messages received grouped by action")
                        .register(registry)
        ).increment();
    }

    public void trackLatency(String event, Runnable runnable) {
        var timer = actionTimers.computeIfAbsent(event, a ->
                Timer.builder("websocket_message_latency")
                        .tag("event", a)
                        .description("Processing latency grouped by action")
                        .register(registry)
        );

        timer.record(runnable);
    }

}
