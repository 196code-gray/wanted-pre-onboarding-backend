package com.onboarid.wanted.board.controller;

import com.google.gson.Gson;
import com.onboarid.wanted.board.dto.BoardDto;
import com.onboarid.wanted.board.entity.Board;
import com.onboarid.wanted.board.mapper.BoardMapper;
import com.onboarid.wanted.board.service.BoardService;
import com.onboarid.wanted.restdocs.ControllerTest;
import com.onboarid.wanted.restdocs.StubData;
import com.onboarid.wanted.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


import java.util.List;

import static com.onboarid.wanted.restdocs.ApiDocumentUtils.getRequestPreProcessor;
import static com.onboarid.wanted.restdocs.ApiDocumentUtils.getResponseProcessor;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(controllers = BoardController.class)
class BoardControllerTest extends ControllerTest{

    @Autowired
    private Gson gson;

    @MockBean
    private BoardService boardService;

    @MockBean
    private BoardMapper boardMapper;


    @Test
    @DisplayName("Test : Board - Post")
    @WithMockUser(username = "securityName", password = "securityPassword", roles = "USER")
    public void postBoardTest() throws Exception{
        // given
        BoardDto.Post post = StubData.MockBoard.post;
        Board board = StubData.MockBoard.getOneBoard();
        String content = gson.toJson(post);

        given(boardMapper.postDtoToEntity(Mockito.any(BoardDto.Post.class)))
                .willReturn(board);
        given(boardService.savedBoard(Mockito.any(Board.class)))
                .willReturn(board);

        // when
        ResultActions actions = mockMvc.perform(
                post("/boards")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization","{AccessToken}")
                        .content(content).with(csrf()));

        // then
        actions
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", is(startsWith("/boards"))))
                .andDo(document(
                        "post-board",
                        getRequestPreProcessor(),
                        getResponseProcessor(),
                        requestHeaders(
                                List.of(headerWithName("Authorization").description("Request User 의 access token")
                                        )
                        ),
                        requestFields(
                                List.of(
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("내용")
                                )
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("Location header. 등록된 리소스의 URI")
                        )
                )
                );
    }

    @Test
    @DisplayName("Tset : Board - Patch")
    @WithMockUser(username = "securityName", password = "securityPassword", roles = "USER")
    public void patchBoardTest() throws Exception {
        // given
        BoardDto.Patch patch = StubData.MockBoard.patch;
        Board board = StubData.MockBoard.getOneBoard();
        BoardDto.Response response = StubData.MockBoard.getOneBoardResponse();
        String content = gson.toJson(patch);

        given(boardMapper.patchDtoToEntity(Mockito.any(BoardDto.Patch.class)))
                .willReturn(board);
        given(boardService.updateBoard(Mockito.any(Board.class)))
                .willReturn(board);
        given(boardMapper.entityToResponseDto(Mockito.any(Board.class)))
                .willReturn(response);

        // when
        ResultActions actions = mockMvc.perform(
                patch("/boards")
                        .accept(MediaType.APPLICATION_JSON) // response (요청) 으로 오는 타입
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content).with(csrf())
                        .header("Authorization", "{accessToken}")
        );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("title").value(patch.getTitle()))
                .andExpect(jsonPath("content").value(patch.getContent()))
                .andDo(document(
                        "patch-board",
                        getRequestPreProcessor(),
                        getResponseProcessor(),
                        requestHeaders(
                                headerWithName("Authorization").description("Request user의 accessToken")
                        ),
                        requestFields(
                                List.of(
                                        fieldWithPath("boardId").type(JsonFieldType.NUMBER).description("board Id").optional(),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("게시글의 제목").optional(),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("게시글의 내용").optional()
                                        )
                                ),
                        responseFields(
                                List.of(
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("게시글의 제목"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("게시글의 내용")
                                )
                        )
                        )
                );
    }
    @Test
    @DisplayName("Test : Board - get")
    @WithMockUser(username = "securityName", password = "securityPassword", roles = "USER")
    public void getBoardTest() throws Exception {
        // given
        long boardId = 1L;
        Board board = StubData.MockBoard.getOneBoardId(boardId);
        BoardDto.Response response = StubData.MockBoard.getOneBoardResponse();

        given(boardService.findBoard(Mockito.anyLong()))
                .willReturn(board);
        given(boardMapper.entityToResponseDto(Mockito.any(Board.class)))
                .willReturn(response);

        // when
        ResultActions actions = mockMvc.perform(
                get("/boards/{board-id}", boardId)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("title").value(board.getTitle()))
                .andExpect(jsonPath("content").value(board.getContent()))
                .andDo(document(
                        "get-board",
                        getRequestPreProcessor(),
                        getResponseProcessor(),
                        pathParameters(
                                parameterWithName("board-id").description("게시글 아이디")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("게시글의 제목"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("게시글의 내용")
                                )
                        )
                ));
    }
    @Test
    @DisplayName("Test : Board - getAll")
    @WithMockUser(username = "securityName", password = "securityPassword", roles = "USER")
    public void getAllBoardsTest() throws Exception {
        // given
        Page<Board> page = StubData.MockBoard.pageBoard();
        List<BoardDto.Response> responses = StubData.MockBoard.getAllBoard();

        given(boardService.findAllBoards(Mockito.any()))
                .willReturn(page);
        given(boardMapper.pageToResponseDto(Mockito.any()))
                .willReturn(responses);

        // when
        ResultActions actions = mockMvc.perform(
                get("/boards")
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.[0].title").value(responses.get(0).getTitle()))
                .andExpect(jsonPath("$.data.[1].title").value(responses.get(1).getTitle()))
                .andExpect(jsonPath("$.data.[2].title").value(responses.get(2).getTitle()))
                .andExpect(jsonPath("$.data.[0].content").value(responses.get(0).getContent()))
                .andExpect(jsonPath("$.data.[1].content").value(responses.get(1).getContent()))
                .andExpect(jsonPath("$.data.[2].content").value(responses.get(2).getContent()))
                .andDo(document(
                        "getAll-board",
                        getRequestPreProcessor(),
                        getResponseProcessor(),
                        requestParameters(
                                List.of(
                                parameterWithName("page").description("페이지 위치").optional(),
                                parameterWithName("size").description("한 페이지에 보고 싶은 게시글 수").optional()
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.ARRAY).description("결과 데이터"),
                                        fieldWithPath("data[].title").type(JsonFieldType.STRING).description("게시글의 제목"),
                                        fieldWithPath("data[].content").type(JsonFieldType.STRING).description("게시글의 내용"),
                                        fieldWithPath("page").type(JsonFieldType.OBJECT).description("페이지 정보"),
                                        fieldWithPath("page.page").type(JsonFieldType.NUMBER).description("현재 페이지"),
                                        fieldWithPath("page.size").type(JsonFieldType.NUMBER).description("한 페이지의 게시물 개수"),
                                        fieldWithPath("page.totalElement").type(JsonFieldType.NUMBER).description("총 게시물 개수"),
                                        fieldWithPath("page.totalPage").type(JsonFieldType.NUMBER).description("총 페이지 개수")
                                )
                        )
                ));

    }

    @Test
    @DisplayName("Test : Board - delete")
    @WithMockUser(username = "securityName", password = "securityPassword", roles = "USER")
    public void deleteBoardTest() throws Exception {
        // given
        long boardId = 1L;

        doNothing().when(boardService).deleteBoard(Mockito.anyLong());

        // when
        ResultActions actions = mockMvc.perform(
                delete("/boards/{board-id}", boardId)
                        .header("Authorization", "{accessToken}").with(csrf())
        );

        // then
        actions
                .andExpect(status().isNoContent())
                .andDo(document(
                        "delete-board",
                        getRequestPreProcessor(),
                        getResponseProcessor(),
                        pathParameters(
                                parameterWithName("board-id").description("게시글 아이디")
                        ),
                        requestHeaders(
                                headerWithName("Authorization").description("user의 access Token")
                        )
                ));
    }

}