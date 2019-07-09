package com.training.service;

import com.training.enums.Action;

public interface ActionService {
    void processAction(Action action, Long ticketId);
}
