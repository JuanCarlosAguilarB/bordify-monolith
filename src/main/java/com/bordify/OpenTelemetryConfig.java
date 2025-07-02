package com.bordify;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.sdk.metrics.SdkMeterProvider;
import io.opentelemetry.sdk.resources.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.opentelemetry.sdk.resources.ResourceBuilder;

//package com.bordify;
//
@Configuration

public class OpenTelemetryConfig {

    @Bean
    public SdkMeterProvider sdkMeterProvider() {
        // Define los recursos de la aplicación (ejemplo: nombre, versión, etc.)
        Resource resource = ResourceBuilder.createDefault()
                .put("service.name", "spring-boot-app")
                .put("service.version", "1.0.0")
                .build();

        // Inicializa el SdkMeterProvider
        SdkMeterProvider sdkMeterProvider = SdkMeterProvider.builder()
                .setResource(resource)
                .build();

        // Registra el exportador Prometheus
        PrometheusCollector.builder()
                .setMetricProducer(sdkMeterProvider)
                .buildAndRegister();

        return sdkMeterProvider;
    }

}