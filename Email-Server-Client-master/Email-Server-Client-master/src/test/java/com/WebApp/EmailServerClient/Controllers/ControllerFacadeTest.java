package com.WebApp.EmailServerClient.Controllers;

import com.WebApp.EmailServerClient.Constants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ControllerFacadeTest {
    @Test
    void initDatabase() throws IOException {
        Assertions.assertEquals(ControllerFacade.InitDatabase(Constants.DATABASE_PATH + 6), true);
    }
}