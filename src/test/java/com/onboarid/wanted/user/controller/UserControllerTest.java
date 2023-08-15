package com.onboarid.wanted.user.controller;

import com.google.gson.Gson;
import com.onboarid.wanted.user.dto.UserPostDto;
import com.onboarid.wanted.user.entity.User;
import com.onboarid.wanted.user.mapper.UserMapper;
import com.onboarid.wanted.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import java.util.List;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static com.onboarid.wanted.restdocs.ApiDocumentUtils.getRequestPreProcessor;
import static com.onboarid.wanted.restdocs.ApiDocumentUtils.getResponseProcessor;
@WebMvcTest(controllers = UserController.class)
@AutoConfigureRestDocs
@MockBean(JpaMetamodelMappingContext.class)
class UserControllerTest {

    @Autowired
    private Gson gson;

    @MockBean
    private UserService service;

    @MockBean
    private UserMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("User - Post")
    @WithMockUser(username = "TestUser", roles = "admin")
    public void postUserTest() throws Exception {
        // given
        UserPostDto post = UserPostDto.builder()
                .email("abc@gmail.com")
                .password("12345678")
                .build();
        User user = User.builder()
                .userId(1L)
                .email(post.getEmail())
                .password(post.getPassword())
                .build();
        String content = gson.toJson(post);

        given(mapper.PostDtoToEntity(Mockito.any(UserPostDto.class)))
                .willReturn(user);
        given(service.savedUser(Mockito.any(User.class)))
                .willReturn(user);

        // when
        ResultActions actions = mockMvc.perform(
                post("/users/sign-up")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content).with(csrf())

        );

        // then
        actions
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", is(startsWith("/users/sign-up"))))
                .andDo(document("post-user",
                        getRequestPreProcessor(),
                        getResponseProcessor(),
                        requestFields(
                                List.of(
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("email"),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("password")
                                )
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("Location header. 등록된 리소스의 URI")
                        )));
    }

}