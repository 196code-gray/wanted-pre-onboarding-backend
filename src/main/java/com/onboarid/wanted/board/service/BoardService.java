package com.onboarid.wanted.board.service;

import com.onboarid.wanted.board.entity.Board;
import com.onboarid.wanted.board.repository.BoardRepository;
import com.onboarid.wanted.exception.BusinessException;
import com.onboarid.wanted.exception.ExceptionCode;
import com.onboarid.wanted.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service @Transactional
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository repository;
    private final UserService userService;

    public Board savedBoard (Board board) {
        return repository.save(board);
    }

    public Board updateBoard (Board board) {
//        Long userId =
        return null;
    }

    @Transactional(readOnly = true)
    public Board findBoard (long boardId) {
        return verifiBoard(boardId);
    }

    @Transactional(readOnly = true)
    public Page<Board> findAllBoards (Pageable pageable) {
        Page<Board> result = repository.findAll(pageable);

        return result;
    }

    public void deleteBoard (long boardId) {

//        repository.delete();
    }

    public Board verifiBoard (long boardId) {
        Optional<Board> op = repository.findById(boardId);

        return op.orElseThrow(() -> new BusinessException(ExceptionCode.BOARD_NOT_FOUND));
    }
}
