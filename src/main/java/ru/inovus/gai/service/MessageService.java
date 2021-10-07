package ru.inovus.gai.service;

public interface MessageService {

    /**
     * Возвращает сообщения из бандла (в messages.properties)
     *
     * @param code код сообщения
     * @param args дополнительные параметры
     * @return сообщение ошибки с доп параметрами
     */
    String get(String code, Object... args);
}
