package com.onboarid.wanted.board.service;

import com.onboarid.wanted.board.entity.Board;
import com.onboarid.wanted.board.repository.BoardRepository;
import com.onboarid.wanted.exception.BusinessException;
import com.onboarid.wanted.exception.ExceptionCode;
import com.onboarid.wanted.user.entity.User;
import com.onboarid.wanted.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service @Transactional
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository repository;
    private final UserService userService;

    public Board savedBoard (Board board) {
        long userId = userService.findSecurityContextHolderUserId();
        User user = userService.verifiedUser(userId);
        Board result = Board.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .user(user)
                .build();

        return repository.save(result);
    }

    public Board updateBoard (Board board) {
        User user = userService.verifiedSecurityContextHolderUserId();
        Board findBoard = verifiedUserBoard(user, board.getBoardId());

        Optional.ofNullable(board.getTitle()).ifPresent(title -> findBoard.updateTitle(title));
        Optional.ofNullable(board.getContent()).ifPresent(content -> findBoard.updateContent(content));

        return findBoard;
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
        User user = userService.verifiedSecurityContextHolderUserId();
        Board findBoard = verifiedUserBoard(user, boardId);

        repository.delete(findBoard);
    }

    public Board verifiBoard (long boardId) {
        Optional<Board> op = repository.findById(boardId);

        return op.orElseThrow(() -> new BusinessException(ExceptionCode.BOARD_NOT_FOUND));
    }
    public Board verifiedUserBoard (User user, long boardId) {
        Optional<Board> op = repository.findById(boardId);
        Board findBoard = op.orElseThrow(() -> new BusinessException(ExceptionCode.BOARD_NOT_FOUND));

        if (!Objects.equals(findBoard.getUser().getUserId(), user.getUserId())) {
            throw new BusinessException(ExceptionCode.UNAUTHORIZED);
        }
        else return findBoard;
    }
}
