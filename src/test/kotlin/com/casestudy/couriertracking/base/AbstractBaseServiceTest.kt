package com.casestudy.couriertracking.base

import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.junit.jupiter.MockitoSettings
import org.mockito.quality.Strictness

/**
 * Base class for service layer tests that use Mockito for mocking dependencies.
 *
 * This abstract class sets up Mockito for unit tests, allowing for flexible and
 * lenient configurations. Test classes extending this should use Mockito to mock
 * dependencies and verify interactions.
 */
@ExtendWith(MockitoExtension::class)
@MockitoSettings(strictness = Strictness.LENIENT)
abstract class AbstractBaseServiceTest