package com.onboarid.wanted.board.service;

import com.onboarid.wanted.board.entity.Board;
import com.onboarid.wanted.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @Transactional
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository repository;

    public Board savedBoard (Board board) {
        return repository.save(board);
    }
}
