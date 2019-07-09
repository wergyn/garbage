package com.training.repository;

import com.training.model.History;

import java.util.List;

public interface HistoryRepo extends GenericRepo<History, Long> {
    List<History> findHistoriesByTicketId(Long ticketId);
}
