package ru.inovus.gai;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.inovus.gai.exception.GaiRuntimeException;
import ru.inovus.gai.model.Number;
import ru.inovus.gai.service.MessageService;
import ru.inovus.gai.service.NumberServiceImpl;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

@ExtendWith(MockitoExtension.class)
class NumberServiceTest {

    @Mock
    MessageService messageService;

    @InjectMocks
    NumberServiceImpl numberService;

    @Test
    void shouldReturnFirstValidSequentialNumber() {
        Number actualNumber = numberService.getNext();
        String expected = "А000АА 116 RUS";

        Assertions.assertEquals(expected, actualNumber.getVal());
    }

    @Test
    void shouldThrowExceptionBecauseSequentialNumbersEnded() {
        ConcurrentSkipListSet<Number> sequentialUsedNumbers = numberService.getSequentialUsedNumbers();
        sequentialUsedNumbers.add(new Number("Х999ХХ 116 RUS"));

        Assertions.assertThrows(GaiRuntimeException.class, () -> numberService.getNext());
    }

    @Test
    void shouldReturnValidNextSequentialNumberIfSequentialNumbersNotEmpty() {
        //given
        ConcurrentSkipListSet<Number> sequentialUsedNumbers = numberService.getSequentialUsedNumbers();
        sequentialUsedNumbers.add(new Number("А123АА 116 RUS"));

        //when
        Number actualNumber = numberService.getNext();

        //then
        String expected = "А124АА 116 RUS";
        Assertions.assertEquals(expected, actualNumber.getVal());
    }

    @Test
    void shouldReturnValidNextSequentialNumberIfRandomNumbersNotEmpty() {
        //given
        Set<Number> randomNumbers = numberService.getRandomUsedNumbers();
        randomNumbers.add(new Number("А000АА 116 RUS"));

        //when
        Number actualNumber = numberService.getNext();

        //then
        String expected = "А001АА 116 RUS";
        Assertions.assertEquals(expected, actualNumber.getVal());
    }

    @Test
    void shouldReturnValidNumberWhenChangingFirstLetter() {
        //given
        ConcurrentSkipListSet<Number> sequentialUsedNumbers = numberService.getSequentialUsedNumbers();
        sequentialUsedNumbers.add(new Number("А999ХХ 116 RUS"));

        //when
        Number actualNumber = numberService.getNext();

        //then
        String expected = "В000АА 116 RUS";
        Assertions.assertEquals(expected, actualNumber.getVal());
    }

    @Test
    void shouldReturnValidNumberWhenChangingSecondLetter() {
        //given
        ConcurrentSkipListSet<Number> sequentialUsedNumbers = numberService.getSequentialUsedNumbers();
        sequentialUsedNumbers.add(new Number("А999АХ 116 RUS"));

        //when
        Number actualNumber = numberService.getNext();

        //then
        String expected = "А000ВА 116 RUS";
        Assertions.assertEquals(expected, actualNumber.getVal());
    }

    @Test
    void shouldReturnValidNumberWhenChangingThirdLetter() {
        //given
        ConcurrentSkipListSet<Number> sequentialUsedNumbers = numberService.getSequentialUsedNumbers();
        sequentialUsedNumbers.add(new Number("А999АА 116 RUS"));

        //when
        Number actualNumber = numberService.getNext();

        //then
        String expected = "А000АВ 116 RUS";
        Assertions.assertEquals(expected, actualNumber.getVal());
    }
}
