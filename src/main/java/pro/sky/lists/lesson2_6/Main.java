package pro.sky.lists.lesson2_6;

import java.util.*; //Классы List и ArrayList импортированы в настоящий класс
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        task1();
        task2();
        task3();
        task4();
        task5();
        task6();
        task7();
    }
    public static void task1() { //Из заданного списка печатаем только нечётные значения
        System.out.println("Задача 1: распечатка в листе только нечётных чисел");
//      List<Integer> nums = new ArrayList<>(List.of(1, 1, 2, 3, 4, 4, 5, 5, 6, 7, 10, 11, 15, 28, 101)); //Изменяемый список
        List<Integer> nums = List.of(1, 1, 2, 3, 4, 4, 5, 5, 6, 7, 10, 11, 15, 28, 101); //Неизменяемый список (в этой задаче без разницы, т.к. изменений не будет)
        System.out.println("Был список: " + nums);
        System.out.print("Распечатка только нечётных чисел списка : ");

        for (int i = 0; i < nums.size(); i++) {
            if (nums.get(i) % 2 != 0) {
                System.out.print(nums.get(i) + " ");
            }
        }
        System.out.println();
        System.out.println();
    }

    public static void task2() {
        System.out.println("Задача 2: сортировка листа, удаление дублей и исключение нечётных чисел в результате");
        List<Integer> nums = new ArrayList<>(List.of(18, 1, 2, 8, 4, 4, 5, 5, 5, 5, 6, 7, 3, 3, 3, 9, 9));
        System.out.println("Был список: " + nums);
        Collections.sort(nums);
        System.out.print("Распечатка отсортированного списка: " + nums);
        System.out.println();
        for (int i = 0; i < nums.size(); i++) {
            if (i > 0 && nums.get(i) == nums.get(i - 1)) {
                nums.remove(i - 1);
                i = i - 1;
            }
        }
        System.out.print("Распечатка списка, в котором удалены все имеющиеся в нём дубли: " + nums);
        System.out.println();
        System.out.print("Распечатка списка с опусканием нечётных чисел: ");
        for (int i = 0; i < nums.size(); i++) {
            if (nums.get(i) % 2 == 0) {
                System.out.print(nums.get(i) + " ");
            }
        }
        System.out.println();
        System.out.println();
    }
    public static void task3() {
        System.out.println("Задача 3: демонстрация сета");
        System.out.print("Был список: ");
        List<String> string1 = new ArrayList<>(List.of("Сергей", "Сергей", "Игорь", "Владимир", "Владимир"));
        System.out.println(string1);
        Set<String> string2 = new HashSet<>(List.of("Сергей", "Сергей", "Игорь", "Владимир", "Владимир"));
        System.out.print("Создали на его базе сет (множество) и распечатали: ");
        System.out.println(string2);
        System.out.println("Как видим, дубли игнорируются в сете.");
        System.out.println();
    }

    public static void task4() { //Задание 4. Содрал с гугла: одним махом подсчитываются количества дублей в листе
        //через мапу
        System.out.println("Задача 4: подсчёт в листе количества дублей слов");
        List<String> list1 = Arrays.asList(
                "один", "один", "один", "два", "три",
                "три", "три", "четыре", "один");

        Map<String, Long> frequency = //Вот та самая мапа, в которой ничего не понял
                list1.stream().collect(Collectors.groupingBy(
                        Function.identity(), Collectors.counting()));

        frequency.forEach((k, v) -> System.out.println(k + ": " + v));
        System.out.println();
    }

    public static void task5() { //Задание 4, выполненное сомостоятельно, но примитивно
        System.out.println("Задача 5: подсчёт в листе количества символов");
        List<String> list = Arrays.asList(
                "a", "c", "b", "a", "c",
                "b", "c", "b", "b");

        Collections.sort(list); //Можно было и не сортировать
        System.out.print("После сортировки листа: ");
        System.out.println(list);

        int count = 0;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals("a")) {
                count++;
            }
        }
        System.out.println("'a' повторяется = " + count + " раза");
        count = 0;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals("b")) {
                count++;
            }
        }
        System.out.println("'b' повторяется = " + count + " раза");
        count = 0;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals("c")) {
                count++;
            }
        }
        System.out.println("'c' повторяется = " + count + " раза");
        System.out.println();
    }

    public static void task6() { //То же задание 4 от Алексея Кизякова
        System.out.println("Задача 6 по подсчёту дублей слов в листе от Кизякова");
        List<String> strings = Arrays.asList(
                "один", "один", "один", "два", "три",
                "три", "три", "четыре", "один");
        //Напишите код, который выводит в консоль количество дублей для каждого уникального слова.
        Map<String, Integer> newStringsMap = new LinkedHashMap<>(); //Объявляем пустую пополняемую мапу
        int count = 0;
        for (String s : strings) //Проход по листу;
            // s при каждом новом проходе будет менять своё содержание на другое слово согласно списка
            if (!newStringsMap.containsKey(s)) { //Совпадает ли наше слово s в цикле с тем же словом s
                //С самого начала мап пустой, поэтому для первого слова ("один") будет 1 сразу, т.к. нет пока совпадений:
                newStringsMap.put(s, 1); //Формируем первый элемент мапа с ключом s и значением по переменной s
            } else { // Когда в цикле слово повторится (станет дублем, например, второй элемент листа - "один"), то уже этот подоператор работает и по этому же ключу
                //увеличивает Integer на единицу и т.д.
                count = newStringsMap.get(s); //Здесь с помощью утилитки по данному ключу вытаскиваем из мапа значение целого
                //элемента на текущий момент. Если зашли сюда первый раз, то значение элемента равно 1, а потом:
                count++; //Добавляем единицу к значению
                newStringsMap.put(s, count); //Прописываем новое значение элемента по этому же ключу
            }
        System.out.println(newStringsMap); //Вывел мапу для самопроверки
        List<Integer> result = new ArrayList<>(newStringsMap.values()); //Создаём новый лист и ложим в него весь мап через спец. утилитку values()
        System.out.println(result);
    }

    public static void task7() { //То же задание 4, только от Ильи-наставника
        System.out.println("Решение задачи 3 от Ильи - Подсчёт дублей в строке и выборка уникальных слов.");
        String text = "We don't need no education We don't need no thought control"; //Нашли такую строчку песни из Пинк-флойда
        System.out.println("Строка для условия: " + text); //Распечатываем строку
        System.out.println(geUnique(text)); //Вызвали метод подсчёта дублей
    }
    public static Set<String> geUnique(String input) { //Метод подсчёта дублей слов в предложении
        Map<String, Integer> counter = new HashMap<>(); //Объявляем коллекцию Мапу (пустую, пополняемую)

        for (String word : input.split(" ")) { //Выуживаем каждое слово в параметре 'строка input' (аргумент text) с помощью разделителя " " утилиты split()
            //и прокручиваем в цикле фор-ыч
            if (counter.containsKey(word)) { //Ищем в мапе по ключу переменной 'word': есть ли такой ключ в ней. Сначала в мапе, конечно, ничего нет,
                //поэтому проскакиваем на 'else' и в этом блоке назначается ЗНАЧЕНИЕ по этому ключу, равное '1' (см. ниже в 'else')
                int count = counter.get(word); //Придёт сюда, когда такое слово уже есть. Это значение после 'else' сначала равно 1,
                // а потом каждый раз инкрементируется через 'counter.put(word, ++count)'
                counter.put(word, ++count); //Произойдет затирание ЗНАЧЕНИЯ по этому КЛЮЧУ с увеличением на 1 ЗНАЧЕНИЯ элемента
            } else {
                counter.put(word, 1); //Положили в мапу первое ЗНАЧЕНИЕ '1' по ключу 'word'
            }
        }
        System.out.println(counter); //Вывод на печать мапы, где её элементами будут слова-ключи плюс количество слов в предложении (ЗНАЧЕНИЕ при ключе)
        System.out.println("Подзадача определения уникальных слов в предложении: ");
        Set<String> result = new HashSet<>(); //Объявление множества Сет (пустого, пополняемого)
        for (Map.Entry<String, Integer> mapEntry : counter.entrySet()) { //Прокрутка всей мапы через фор-ыч
            if (mapEntry.getValue() == 1) { //Если ЗНАЧЕНИЕ равно 1, то соответствующий значению ключ записывается в Сет с помощью утилиты getKey()
                result.add(mapEntry.getKey()); //Ключ (а это фактически-то строка) записывается в наш Сет. Повторы в сете бы игноририровались, а их и так не будет!
            }
        }
        return result; //Выводится вся коллекция Сет, состоящая из уникальных слов от первоначальной строки из песни ансамбля Пинк-флойд
    }
}
