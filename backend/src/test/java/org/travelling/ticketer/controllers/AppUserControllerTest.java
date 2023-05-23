package org.travelling.ticketer.controllers;

import com.google.gson.Gson;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.travelling.ticketer.constants.RoleType;
import org.travelling.ticketer.dao.AppUserDAO;
import org.travelling.ticketer.dto.AppUserDTO;
import org.travelling.ticketer.dto.UserCredentialsDTO;
import org.travelling.ticketer.dto.UserPersonalDTO;
import org.travelling.ticketer.entity.AppUser;

import java.util.Arrays;
import java.util.HashSet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource(
        locations = "classpath:integrationtest.properties")
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AppUserControllerTest  {
    public static final String CONTENT_TYPE = "application/json";
    @Autowired
    private MockMvc mvc;

    @Autowired
    private AppUserDAO appUserDAO;

    @Autowired
    private Gson gson;

    @Test
    public void shouldAddNewUser() throws Exception {

        AppUserDTO appUserDTO = new AppUserDTO();
        UserCredentialsDTO userCredentialsDTO = new UserCredentialsDTO();
        String username = "user";
        String password = "fb3ddeabce";
        String firstName = "mat";
        userCredentialsDTO.setUserName(username);
        userCredentialsDTO.setPassword(password);
        appUserDTO.setUserCredentials(userCredentialsDTO);

        appUserDTO.setRoles(new HashSet<>(Arrays.asList(RoleType.USER.name(), RoleType.ADMIN.name())));

        UserPersonalDTO userPersonalDTO = new UserPersonalDTO();
        userPersonalDTO.setEmail("mat@gmail.com");
        userPersonalDTO.setFirstName(firstName);
        userPersonalDTO.setLastName("pie");
        appUserDTO.setPersonalData(userPersonalDTO);


        mvc.perform(post("/addUser").contentType(CONTENT_TYPE).content(gson.toJson(appUserDTO))).andExpect(status().isOk());
        AppUser user = appUserDAO.findByUsername(username).orElseThrow(()->new IllegalArgumentException("User not created"));
        Assertions.assertThat(user.getUsername()).isEqualTo(username);
        Assertions.assertThat(user.getPassword()).isNotEqualTo(password);
        Assertions.assertThat(user.getFirstName()).isEqualTo(firstName);
    }
}