package pro.sky.lists.lesson2_6;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        task1();
        task2();
        task3();
        task4();
    }

    public static void task1() {
        List<Integer> nums = new ArrayList<>(List.of(1, 1, 2, 3, 4, 4, 5, 5, 6, 7, 10, 11, 15, 28, 101));
        for (int i = 0; i < nums.size(); i++) {
            if (nums.get(i) % 2 != 0) {
                System.out.print(nums.get(i) + " ");
            }
        }
        System.out.println();
    }

    public static void task2() {
        List<Integer> nums = new ArrayList<>(List.of(10, 1, 2, 8, 4, 4, 5, 5, 5, 5, 6, 7, 3, 3, 3, 9, 9));
        Collections.sort(nums);


        for (int i = 0; i < nums.size(); i++) {
            if (i > 0 && nums.get(i) == nums.get(i - 1)) {
                nums.remove(i - 1);
                i = i - 1;
            }
        }
        for (int i = 0; i < nums.size(); i++) {
            if (nums.get(i) % 2 == 0) {
                System.out.print(nums.get(i) + " ");
            }
        }
        System.out.println();
    }

    public static void task3() {
        Set<String> string = new HashSet<>(List.of("Сергей", "Сергей", "Игорь", "Владимир", "Владимир"));
        System.out.println(string);
    }

    public static void task4() {
        List<String> list = Arrays.asList(
                "один", "один", "один", "два", "три",
                "три", "три", "четыре", "один");

        Map<String, Long> frequency =
                list.stream().collect(Collectors.groupingBy(
                        Function.identity(), Collectors.counting()));

        frequency.forEach((k, v) -> System.out.println(k + ": " + v));
    }

}
