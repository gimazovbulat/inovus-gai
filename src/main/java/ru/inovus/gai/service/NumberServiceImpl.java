package ru.inovus.gai.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.inovus.gai.exception.GaiRuntimeException;
import ru.inovus.gai.model.Number;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

@Getter
@Slf4j
@RequiredArgsConstructor
@Service
public class NumberServiceImpl implements NumberService {

    private final MessageService messageService;

    //номера, которые были использованы по последовательному алгоритму
    private final ConcurrentSkipListSet<Number> sequentialUsedNumbers = new ConcurrentSkipListSet<>();
    //номера, которые были использованы по рандомному алгоритму
    private final Set<Number> randomUsedNumbers = ConcurrentHashMap.newKeySet();

    private final List<Character> unsortedPossibleLetters = Arrays.asList('А', 'Е', 'Т', 'О', 'Р', 'Н', 'У', 'К', 'Х', 'С', 'В', 'М');
    private final List<Character> sortedPossibleLetters = unsortedPossibleLetters.stream().sorted().collect(Collectors.toList());

    @Override
    public Number getRandom() {
        Number randomNumber = generateRandomNumber();

        //если вдруг он уже был
        while (randomUsedNumbers.contains(randomNumber) || sequentialUsedNumbers.contains(randomNumber)) {
            //генерим следующий
            randomNumber = generateRandomNumber();
        }

        randomUsedNumbers.add(randomNumber);
        log.info("Generated new random number: {}", randomNumber);
        return randomNumber;
    }

    @Override
    public Number getNext() {
        Number newNumber;
        //если еще не выдавались номера, то вернем самый маленький
        if (sequentialUsedNumbers.isEmpty()) {
            String firstPossibleNumber = String.format(
                            Number.TEMPLATE,
                            sortedPossibleLetters.get(0),
                            "000",
                            sortedPossibleLetters.get(0),
                            sortedPossibleLetters.get(0)
                    )
                    .concat(StringUtils.SPACE)
                    .concat(Number.REGION);
            newNumber = new Number(firstPossibleNumber);
        } else {
            //иначе возьмем следующий по порядку номер
            newNumber = incrementNumber(sequentialUsedNumbers.last());
        }

        //если вдруг он уже выдавался рандомным алгоритмом
        while (randomUsedNumbers.contains(newNumber)) {
            //добавим его в список
            sequentialUsedNumbers.add(newNumber);
            //возьмем следующий
            newNumber = incrementNumber(newNumber);
        }

        sequentialUsedNumbers.add(newNumber);
        log.info("Generated new sequential number: {}", newNumber);
        return newNumber;
    }

    /**
     * Находит следующий по порядку номер
     *
     * @param oldNumber Предыдущий номер
     * @return новый номер
     */
    private Number incrementNumber(Number oldNumber) {
        Number newNumber = new Number(oldNumber.getVal());
        if (oldNumber.getDigits() < 999) {
            newNumber.setDigits(oldNumber.getDigits() + 1);
        } else {
            incrementLetters(newNumber);

            //если у нас после увеличения букв ничего не изменилось, значит это последний возможный номер
            if (newNumber.getVal().equals(oldNumber.getVal())){
                throw new GaiRuntimeException("numbers.ended");
            }

            newNumber.setDigits(0);
        }

        return newNumber;
    }

    /**
     * Генерирует рандомный номер
     *
     * @return случайный номер
     */
    private Number generateRandomNumber() {
        Random random = new Random();

        String randomNumberVal = String.format(
                Number.TEMPLATE,
                unsortedPossibleLetters.get(random.nextInt(unsortedPossibleLetters.size())),
                random.nextInt(1000),
                unsortedPossibleLetters.get(random.nextInt(unsortedPossibleLetters.size())),
                unsortedPossibleLetters.get(random.nextInt(unsortedPossibleLetters.size()))
        ).concat(Number.REGION);

        return new Number(randomNumberVal);
    }

    /**
     * Инкрементит номер по буквам, если это самый большой возможный номер по буквам - ничего не делает
     *
     * @param number номер, буквы которого нужно увеличить
     */
    private Number incrementLetters(Number number) {
        int indexOfFirstLetter = sortedPossibleLetters.indexOf(number.getFirstLetter());
        int indexOfSecondLetter = sortedPossibleLetters.indexOf(number.getSecondLetter());
        int indexOfThirdLetter = sortedPossibleLetters.indexOf(number.getThirdLetter());

        if (indexOfThirdLetter != sortedPossibleLetters.size() - 1) {
            char newThirdLetter = sortedPossibleLetters.get(indexOfThirdLetter + 1);
            number.setThirdLetter(newThirdLetter);
        } else if (indexOfSecondLetter != sortedPossibleLetters.size() - 1) {
            char newSecondLetter = sortedPossibleLetters.get(indexOfSecondLetter + 1);
            number.setSecondLetter(newSecondLetter);
            number.setThirdLetter(sortedPossibleLetters.get(0));
        } else if (indexOfFirstLetter != sortedPossibleLetters.size() - 1) {
            char newFirstLetter = sortedPossibleLetters.get(indexOfFirstLetter + 1);
            number.setFirstLetter(newFirstLetter);
            number.setThirdLetter(sortedPossibleLetters.get(0));
            number.setSecondLetter(sortedPossibleLetters.get(0));
        }
        return number;
    }
}
