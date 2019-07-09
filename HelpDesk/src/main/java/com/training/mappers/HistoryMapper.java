package com.training.mappers;

import com.training.dto.HistoryDto;
import com.training.model.History;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class HistoryMapper {

    ModelMapper modelMapper;

    public HistoryDto toDto(History history) {
        return modelMapper.map(history, HistoryDto.class);
    }
}
