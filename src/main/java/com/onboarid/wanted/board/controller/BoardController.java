package com.onboarid.wanted.board.controller;

import com.onboarid.wanted.board.dto.BoardDto;
import com.onboarid.wanted.board.entity.Board;
import com.onboarid.wanted.board.mapper.BoardMapper;
import com.onboarid.wanted.board.service.BoardService;
import com.onboarid.wanted.util.MultiResponse;
import com.onboarid.wanted.util.URICreator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {
    private final BoardMapper mapper;
    private final BoardService service;

    @PostMapping
    public ResponseEntity postBoard (@RequestBody @Valid BoardDto.Post post) {
        Board board = service.savedBoard(mapper.postDtoToEntity(post));
        URI uri = URICreator.createUri("/boards", board.getBoardId());

        return ResponseEntity.created(uri).build();
    }

    @PatchMapping
    public ResponseEntity patchBoard (@RequestBody @Valid BoardDto.Patch patch) {

        return new ResponseEntity<>(mapper.entityToResponseDto(
                service.updateBoard(
                        mapper.patchDtoToEntity(patch)
                )),
                HttpStatus.OK);
    }

    @GetMapping("/{board-id}")
    public ResponseEntity getBoard (@PathVariable("board-id") @Positive long boardId) {

        return new ResponseEntity(mapper.entityToResponseDto(
                service.findBoard(boardId)),
                HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getAllBoards (@RequestParam(defaultValue = "1") int page,
                                        @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page -1, size);
        Page<Board> boardPage = service.findAllBoards(pageable);
        List<BoardDto.Response> result = mapper.pageToResponseDto(boardPage);

        return new ResponseEntity<>(new MultiResponse<>(result, boardPage), HttpStatus.OK);
    }

    @DeleteMapping("/{board-id}")
    public ResponseEntity deleteBoard (@PathVariable("board-id") @Positive long boardId) {
        service.deleteBoard(boardId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
