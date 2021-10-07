package ru.inovus.gai.service;

import ru.inovus.gai.model.Number;

/**
 * Сервис, по работе с номерами
 * */
public interface NumberService {

    /**
     * Возвращает рандомный номер, притом не совпадающий с выданными ранее
     @return рандомный номер
     * */
    Number getRandom();

    /**
     * Вовзращает следующий по порядку номер,  притом не совпадающий с выданными ранее
     * @return следующий по порядку номер
     * */
    Number getNext();
}
