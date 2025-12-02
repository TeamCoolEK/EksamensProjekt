package org.example.coolplanner.service;

import org.example.coolplanner.repository.CoolPlannerWriteRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CoolPlannerWriteServiceTest {
    @Mock
    CoolPlannerWriteRepository coolPlannerWriteRepository;

    @InjectMocks
    CoolPlannerWriteService coolPlannerWriteService;

    //@Test
}
