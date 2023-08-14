package com.onboarid.wanted.restdocs;

import com.onboarid.wanted.board.dto.BoardDto;
import com.onboarid.wanted.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public class StubData {
    public static class MockUser {

    }
    public static class MockBoard {
        public static BoardDto.Post post = BoardDto.Post
                    .builder()
                    .title("제목입니다.")
                    .content("내용입니다.")
                    .build();

            public static BoardDto.Patch patch = BoardDto.Patch
                    .builder()
                    .boardId(1L)
                    .title("제목이 수정되었습니다")
                    .content("내용이 수정되었습니다.")
                    .build();

        public static BoardDto.Response getOneBoardResponse() {
            return BoardDto.Response.builder()
                    .title("제목입니다.")
                    .content("내용입니다.")
                    .build();
        }
        public static List<BoardDto.Response> getAllBoard() {
            BoardDto.Response one = BoardDto.Response.builder()
                    .title("제목입니다.")
                    .content("내용입니다.")
                    .build();
            BoardDto.Response two = BoardDto.Response.builder()
                    .title("타이틀.")
                    .content("컨텐츠.")
                    .build();
            BoardDto.Response three = BoardDto.Response.builder()
                    .title("타이틀1.")
                    .content("컨텐츠2.")
                    .build();

            return List.of(one, two, three);
        }
        public static Page<Board> pageBoard () {
            Board one = Board.builder()
                    .title("제목입니다.")
                    .content("내용입니다.")
                    .build();
            Board two = Board.builder()
                    .title("타이틀.")
                    .content("컨텐츠.")
                    .build();
            Board three = Board.builder()
                    .title("타이틀1.")
                    .content("컨텐츠2.")
                    .build();

            return new PageImpl<>(List.of(one, two, three),
                    PageRequest.of(0, 10), 3);
        }
        public static Board getOneBoardId (long boardId) {
            return Board.builder()
                    .boardId(boardId)
                    .title("제목입니다.")
                    .content("내용입니다.")
                    .build();
        }
        public static Board getOneBoard() {
            return Board.builder()
                    .boardId(1L)
                    .title("제목입니다.")
                    .content("내용입니다.")
                    .build();
        }


    }
}
