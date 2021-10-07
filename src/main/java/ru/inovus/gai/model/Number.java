package ru.inovus.gai.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * Представление номера
 * */
@Data
@AllArgsConstructor
public class Number implements Comparable<Number> {

    public static final String TEMPLATE = "%c%s%c%c";

    //пусть хардкод
    public static final String REGION = "116 RUS";

    //сам номер
    private String val;

    /**
     * Возвращает цифры в номере
     *
     * @return Цифры в номере
     */
    public int getDigits() {
        return Integer.parseInt(val.substring(1, 4));
    }

    /**
     * Заменяет цифры в номере
     *
     * @param digits новые цифры
     */
    public void setDigits(int digits) {
        setVal(
                getFirstLetter() +
                        String.format("%0" + 3 + "d", digits) +
                        getSecondLetter() +
                        getThirdLetter() +
                        StringUtils.SPACE +
                        REGION
        );
    }

    /**
     * Возвращает буквы в номере
     *
     * @return Буквы в номере
     */
    public String getLetters() {
        return String.valueOf(val.charAt(0)).concat(val.substring(4, 7));
    }

    /**
     * Возвращает 1 букву в номере слева направо
     *
     * @return 1 буква в номере
     */
    public char getFirstLetter() {
        return getLetters().charAt(0);
    }

    /**
     * Возвращает 2 букву в номере слева направо
     *
     * @return 2 буква в номере
     */
    public char getSecondLetter() {
        return getLetters().charAt(1);
    }

    /**
     * Возвращает 3 букву в номере слева направо
     *
     * @return 3 буква в номере
     */
    public char getThirdLetter() {
        return getLetters().charAt(2);
    }

    /**
     * Заменяет первую букву в номере
     *
     * @param letter новая первая буква
     */
    public void setFirstLetter(char letter) {
        setVal(String.valueOf(letter) + this.getDigits() + getSecondLetter() + getThirdLetter() + StringUtils.SPACE + REGION);
    }

    /**
     * Заменяет вторую букву в номере
     *
     * @param letter вторая первая буква
     */
    public void setSecondLetter(char letter) {
        setVal(String.valueOf(getFirstLetter()) + this.getDigits() + letter + getThirdLetter() + StringUtils.SPACE + REGION);
    }

    /**
     * Заменяет третью букву в номере
     *
     * @param letter третья первая буква
     */
    public void setThirdLetter(char letter) {
        setVal(String.valueOf(getFirstLetter()) + this.getDigits() + getSecondLetter() + letter + StringUtils.SPACE + REGION);
    }

    @Override
    public int compareTo(Number o) {
        //если буквы равны, значит надо сравнить цифры, если нет - сравниваем только буквы
        return this.getLetters().equals(o.getLetters()) ? this.getDigits() - o.getDigits() :
                this.getLetters().compareTo(o.getLetters());
    }
}
