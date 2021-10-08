package ru.inovus.gai.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * Представление номера
 */
@Data
@NoArgsConstructor
public class Number implements Comparable<Number> {

    /**
     * Шаблон, по которому и строится номер
     */
    private static final String TEMPLATE = "%c%s%c%c";

    //пусть хардкод
    private static final String REGION = "116 RUS";

    /**
     * Сам номер
     */
    private String val;

    /**
     * Цифры в номере по шаблону, то есть с нулями в начале, если они нужны
     */
    private int digits;

    /**
     * Первая буква слева направо
     */
    private char firstLetter;

    /**
     * Вторая буква слева направо
     */
    private char secondLetter;

    /**
     * Третья буква слева направо
     */
    private char thirdLetter;

    @Builder
    public Number(int digits, char firstLetter, char secondLetter, char thirdLetter) {
        this.digits = digits;
        this.firstLetter = firstLetter;
        this.secondLetter = secondLetter;
        this.thirdLetter = thirdLetter;
    }

    public Number(String val) {
        this.val = val;

        String letters = String.valueOf(val.charAt(0)).concat(val.substring(4, 7));
        setFirstLetter(letters.charAt(0));
        setSecondLetter(letters.charAt(1));
        setThirdLetter(letters.charAt(2));
        setDigits(Integer.parseInt(val.substring(1, 4)));
    }

    /**
     * Возвращает цифры в номере по шаблону, то есть с нулями в начале, если они нужны
     *
     * @return Цифры в номере
     */
    public String getDigitsAsString() {
        return String.format("%0" + 3 + "d", getDigits());
    }

    /**
     * Возвращает буквы в номере
     *
     * @return Буквы в номере
     */
    public String getLetters() {
        return String.valueOf(getFirstLetter()) + getSecondLetter() + getThirdLetter();
    }

    /**
     * Возвращает номер
     */
    public String getVal() {
        return String.format(TEMPLATE,
                        getFirstLetter(),
                        getDigitsAsString(),
                        getSecondLetter(),
                        getThirdLetter()
                )
                .concat(StringUtils.SPACE)
                .concat(REGION);
    }

    @Override
    public int compareTo(Number o) {
        //если буквы равны, значит надо сравнить цифры, если нет - сравниваем только буквы
        return this.getLetters().equals(o.getLetters()) ? this.getDigits() - o.getDigits() :
                this.getLetters().compareTo(o.getLetters());
    }
}
