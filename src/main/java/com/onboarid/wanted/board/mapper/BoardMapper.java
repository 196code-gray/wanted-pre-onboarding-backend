package com.onboarid.wanted.board.mapper;

import com.onboarid.wanted.board.dto.BoardDto;
import com.onboarid.wanted.board.entity.Board;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BoardMapper {
    Board postDtoToEntity (BoardDto.Post post);
}
