package ru.inovus.gai.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageSourceAccessor accessor;

    @Override
    public String get(String code, Object... args) {
        return accessor.getMessage(code, args);
    }
}
