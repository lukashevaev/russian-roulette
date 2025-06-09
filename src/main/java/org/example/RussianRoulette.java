package org.example;

import java.util.Random;
/*
 * Играем в русскую рулетку с шестизарядным револьвером.
 * В револьвер заряжают не один, а два патрона.
 * Оба патрона располагают в соседние гнезда, друг за другом, остальные гнёзда пусты.
 * Барабан прокручивают.
 * После первого нажатия на спусковой крючок ничего не произошло, первый игрок жив.
 * Револьвер передаём второму игроку. Надо нажать на спусковой крючок второй раз.
 * Что лучше, нажать на спусковой крючок сразу или крутить барабан?
 *
 * Если рассмотреть ситуацию, когда 2 игрок не прокручивает барабан:
 * Всего существует 5 расстановок 2-х рядом стоящих патронов в 6-зарядном револьвере
 * Так как 1 игрок выжил, значит невозможна такая расстановка, когда патрон попадется уже в 1 ходе.
 * Следовательно остается 4 варианта расстановки 2-х рядом стоящих патронов
 * и всего 1 вариант, когда патрон попадется второму игроку сразу же при его 1-м ходе.
 * Следовательно, вероятность попадания 1/4 или 25%
 * Значит, удачный исход для 2-го игрока будет в 75%
 *
 * Если второй игрок прокрутит барабан перед выстрелом,
 * то патроны перераспределятся случайным образом по всему кругу,
 * предыдущая расстановка затрется и тогда вероятность попадания всегда будет одной и той же,
 * а именно 2/6 или 1/3  (при условии, что 1 игрок выжил).
 * Таким образом, вероятность 2-му игроку выжить при прокручивании барабана: 1 - 1/3 = 2/3 (66%).
 */
public class RussianRoulette {

    // Основной метод, эмулирующий шаги игроков в игре
    // Происходит подсчет количества проигрышей как у первого игрока, так и у второго
    public static void main(String[] args) {
        var attempts = 10000;
        var firstPlayerFailures = 0;
        var secondPlayerPickNextFailures = 0;
        var secondPlayerPickRandomFailures = 0;
        var bullets = bullets();

        for (int i = 0; i < attempts; i++) {
            var firstPlayerPick = findAnyEmpty(bullets);
            // Если ход "без пули" не с 1 попытки, увеличиваем счетчик проигрышей для 1 игрока
            if (firstPlayerPick[1] == 1) {
                firstPlayerFailures++;
            }
            // pickRandom - - позиция выстрела с прокручиванием барабана
            // pickNext - позиция выстрела без прокручивания барабана
            var pickNext = (firstPlayerPick[0] + 1) % 6;
            var pickRandom = new Random().nextInt(6);
            if (bullets[pickRandom]) {
                secondPlayerPickRandomFailures++;
            }

            if (bullets[pickNext]) {
                secondPlayerPickNextFailures++;
            }
        }

        System.out.println("Experiments count: " + attempts);
        System.out.println("First player successful attempts: " + (attempts - firstPlayerFailures) + ", chance to live: " + (attempts - firstPlayerFailures * 1.0) * 100 / attempts + " %");
        System.out.println("Second player 'pick random' successful attempts: " + (attempts - secondPlayerPickRandomFailures) + ", chance to live: " + (attempts - secondPlayerPickRandomFailures * 1.0) * 100 / attempts + " %");
        System.out.println("Second player 'pick next' successful attempts: " + (attempts - secondPlayerPickNextFailures) + ", chance to live: " + (attempts - secondPlayerPickNextFailures * 1.0) * 100 / attempts + " %");
    }
    // Метод для проверки повезло первому игроку или нет
    // Генерирует рандомную позицию пули в барабане, пока не получит шаг без пули
    // Возвращает позицию без пули и
    // информацию о том, с первой ли попытки получен успешный исход
    // (для дальнейшего подсчета кол-ва неуспешных исходов для первого игрока)
    public static int[] findAnyEmpty(boolean[] bullets) {
        var tryEmpty = new Random().nextInt(bullets.length);
        int wasFailure = bullets[tryEmpty] ? 1 : 0;
        while (bullets[tryEmpty]) {
            tryEmpty = new Random().nextInt(bullets.length);
        }

        return new int[] {tryEmpty, wasFailure};
    }
    // Метод для заполнения барабана двумя патронами подряд
    // в рандомном месте барабана
    public static boolean[] bullets() {
        boolean[] bullets = new boolean[6];
        var filledBullet = new Random().nextInt(6);
        bullets[filledBullet] = true;
        bullets[(filledBullet + 1) % 6] = true;

        return bullets;
    }

}
