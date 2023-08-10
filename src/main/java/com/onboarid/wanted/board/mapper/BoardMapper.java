package com.onboarid.wanted.board.mapper;

import com.onboarid.wanted.board.dto.BoardDto;
import com.onboarid.wanted.board.entity.Board;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BoardMapper {
    Board postDtoToEntity (BoardDto.Post post);
    Board patchDtoToEntity (BoardDto.Patch patch);
    BoardDto.Response entityToResponseDto(Board board);
    List<BoardDto.Response> pageToResponseDto (Page<Board> boardPage);
}
