package com.bordify.shared.infrastucture.controlles;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestMethodOrder;

@Tag("integration")
@TestMethodOrder(MethodOrderer.Random.class)
public interface IntegrationTestBaseClass {
}
