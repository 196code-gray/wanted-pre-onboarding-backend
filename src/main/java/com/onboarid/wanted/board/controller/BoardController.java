package com.onboarid.wanted.board.controller;

import com.onboarid.wanted.board.dto.BoardDto;
import com.onboarid.wanted.board.entity.Board;
import com.onboarid.wanted.board.mapper.BoardMapper;
import com.onboarid.wanted.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {
    private final BoardMapper mapper;
    private final BoardService service;

    @PostMapping
    public ResponseEntity postBoard (@RequestBody @Valid BoardDto.Post post) {
        Board board = service.savedBoard(mapper.postDtoToEntity(post));

        return null;
    }

    @PatchMapping
    public ResponseEntity patchBoard (@RequestBody BoardDto.Patch patch) {
        return null;
    }

    @GetMapping("/{board-id}")
    public ResponseEntity getBoard (@PathVariable("board-id") @Positive long boardId) {
        return null;
    }

    @GetMapping
    public ResponseEntity getAllBoards (@RequestParam(defaultValue = "1") int page,
                                        @RequestParam(defaultValue = "10") int size) {
        return null;
    }

    @DeleteMapping("/{board-id}")
    public ResponseEntity deleteBoard (@PathVariable("board-id") @Positive long boardId) {
        return null;
    }
}
