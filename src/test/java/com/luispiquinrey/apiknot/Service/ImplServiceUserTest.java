package com.luispiquinrey.apiknot.Service;

import com.luispiquinrey.apiknot.Repository.RepositoryUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ImplServiceUserTest {
    @Mock
    private RepositoryUser repositoryUser;

    @InjectMocks
    private ImplServiceUser serviceUser;

    @Test
    void findByEmail() {
    }
}
