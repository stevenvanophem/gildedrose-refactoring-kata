package com.gildedrose;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class GildedRoseTest {

    private final GildedRose app = new GildedRose();

    @AfterEach
    void tearDown() {
        app.clearItems();
    }

    @Test
    @DisplayName("I can add items to the GildedRose")
    void testAddItems() {
        provideItems().forEach(app::addItem);
    }

    @ParameterizedTest(name = "Day {1} expected sellIn: {2} and quality: {3}")
    @MethodSource
    @DisplayName("Once the sell by date has passed, Quality degrades twice as fast")
    void testRule1(TestItem testItem, int days, int sellIn, int quality) {
        final Item item = testItem.toItem();

        app.addItem(item);
        IntStream.range(0, days).forEach(day -> app.updateQuality());

        assertThat(item.toString()).isEqualTo(item.name + ", " + sellIn + ", " + quality);
    }

    private static Stream<Arguments> testRule1() {
        final TestItem cookies = new TestItem("Cookies", 5, 10);
        return Stream.of(
            Arguments.of(cookies, 0, 5, 10),
            Arguments.of(cookies, 1, 4, 9),
            Arguments.of(cookies, 2, 3, 8),
            Arguments.of(cookies, 3, 2, 7),
            Arguments.of(cookies, 4, 1, 6),
            Arguments.of(cookies, 5, 0, 5),
            Arguments.of(cookies, 6, -1, 3),
            Arguments.of(cookies, 7, -2, 1),
            Arguments.of(cookies, 8, -3, 0),
            Arguments.of(cookies, 9, -4, 0),
            Arguments.of(cookies, 10, -5, 0),
            Arguments.of(cookies, 11, -6, 0)
        );
    }

    @Test
    @DisplayName("The items degrade correctly after one day")
    void testDegrade() {
        List<Item> items = provideItems();
        items.forEach(app::addItem);

        app.updateQuality();

        final String formattedItems = format(items);
        final String expectedResult = """
            +5 Dexterity Vest, 9, 19
            Aged Brie, 1, 1
            Elixir of the Mongoose, 4, 6
            Sulfuras, Hand of Ragnaros, 0, 80
            Sulfuras, Hand of Ragnaros, -1, 80
            Backstage passes to a TAFKAL80ETC concert, 14, 21
            Backstage passes to a TAFKAL80ETC concert, 9, 50
            Backstage passes to a TAFKAL80ETC concert, 4, 50
            Conjured Mana Cake, 2, 5
            """;

        assertThat(formattedItems).isEqualTo(expectedResult);
    }

    @Test
    @DisplayName("The items degrade correctly after two days")
    void testDegradeDay2() {
        List<Item> items = provideItems();
        items.forEach(app::addItem);

        app.updateQuality();
        app.updateQuality();

        final String formattedItems = format(items);
        final String expectedResult = """
            +5 Dexterity Vest, 8, 18
            Aged Brie, 0, 2
            Elixir of the Mongoose, 3, 5
            Sulfuras, Hand of Ragnaros, 0, 80
            Sulfuras, Hand of Ragnaros, -1, 80
            Backstage passes to a TAFKAL80ETC concert, 13, 22
            Backstage passes to a TAFKAL80ETC concert, 8, 50
            Backstage passes to a TAFKAL80ETC concert, 3, 50
            Conjured Mana Cake, 1, 4
            """;

        assertThat(formattedItems).isEqualTo(expectedResult);
    }

    private static String format(List<Item> items) {
        String result = items.stream()
            .map(Item::toString)
            .collect(Collectors.joining("\n"));
        return result + "\n";
    }

    private static List<Item> provideItems() {
        return List.of(
            new Item("+5 Dexterity Vest", 10, 20),
            new Item("Aged Brie", 2, 0),
            new Item("Elixir of the Mongoose", 5, 7),
            new Item("Sulfuras, Hand of Ragnaros", 0, 80),
            new Item("Sulfuras, Hand of Ragnaros", -1, 80),
            new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20),
            new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49),
            new Item("Backstage passes to a TAFKAL80ETC concert", 5, 49),
            // this conjured item does not work properly yet
            new Item("Conjured Mana Cake", 3, 6)
        );
    }

    record TestItem(String name, int sellIn, int quality) {

        public Item toItem() {
            return new Item(name, sellIn, quality);
        }

    }

}
