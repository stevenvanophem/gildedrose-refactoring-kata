package com.gildedrose;

import java.util.Objects;

public record Item(Name name, SellDate sellDate, Quality quality) {

    public Item {
        Objects.requireNonNull(name, "item name is missing");
        Objects.requireNonNull(sellDate, "item sellIn is missing");
        Objects.requireNonNull(quality, "item quality is missing");
    }

    @Override
    public String toString() {
        return this.name.value() + ", " + this.sellDate.value() + ", " + this.quality.value();
    }

    public Item degrade() {
        if (name.isLegendary())
            return this;

        final SellDate degradedSellDate = sellDate.degrade();

        if (name.isAgedBrie()) {
            final Quality increasedQuality = quality.increase();
            return new Item(name, degradedSellDate, increasedQuality);
        }

        if (name.isBackstagePasses()) {
            if (degradedSellDate.fiveDaysOrLess()) {
                final Quality trippledQuality = quality.increaseThreeTimesAsFast();
                return new Item(name, degradedSellDate, trippledQuality);
            }
            if (degradedSellDate.tenDaysOrLess()) {
                final Quality doubleIncreasedQuality = quality.increaseTwiceAsFast();
                return new Item(name, degradedSellDate, doubleIncreasedQuality);
            }
            if (degradedSellDate.isExpired()) {
                final Quality worthlessQuality = Quality.worthless();
                return new Item(name, degradedSellDate, worthlessQuality);
            }
            final Quality increasedQuality = quality.increase();
            return new Item(name, degradedSellDate, increasedQuality);
        }

        if (degradedSellDate.isExpired()) {
            final Quality doubleDegradedQuality = quality.degradeTwiceAsFast();
            return new Item(name, degradedSellDate, doubleDegradedQuality);
        }

        final Quality degradedQuality = quality.degrade();
        return new Item(name, degradedSellDate, degradedQuality);
    }

    public record Name(String value) {

        public Name {
            Objects.requireNonNull(value, "item name is missing");
            if (value.isBlank())
                throw new IllegalArgumentException("item name cannot be blank");
        }

        public static Name fromString(String value) {
            return new Name(value);
        }

        public boolean isLegendary() {
            return value.equals("Sulfuras, Hand of Ragnaros");
        }

        public boolean isAgedBrie() {
            return value.equals("Aged Brie");
        }

        public boolean isBackstagePasses() {
            return value.equals("Backstage passes to a TAFKAL80ETC concert");
        }

    }

    public record SellDate(int value) {

        public SellDate degrade() {
            return new SellDate(value - 1);
        }

        public boolean isExpired() {
            return value < 0;
        }

        public boolean tenDaysOrLess() {
            return value <= 10;
        }

        public boolean fiveDaysOrLess() {
            return value <= 5;
        }

    }

    public record Quality(int value) {

        public Quality {
            if (value < 0)
                throw new IllegalArgumentException("Quality cannot be negative");
            if (value > 50 && value != 80)
                throw new IllegalArgumentException("Quality cannot be greater than 50");
        }

        public static Quality of(int value) {
            return new Quality(value);
        }

        public static Quality mostValuable() {
            return new Quality(50);
        }

        public static Quality worthless() {
            return new Quality(0);
        }

        public boolean isWorthless() {
            return value == 0;
        }

        public boolean isLegendary() {
            return value == 80;
        }

        public Quality degrade() {
            int nextValue = value - 1;
            if (nextValue >= 0) {
                return new Quality(nextValue);
            }
            return Quality.worthless();
        }

        public Quality degradeTwiceAsFast() {
            int nextValue = value - 2;
            if (nextValue >= 0) {
                return new Quality(nextValue);
            }
            return Quality.worthless();
        }

        public Quality increase() {
            int nextValue = value + 1;
            if (nextValue <= 50) {
                return new Quality(nextValue);
            }
            return Quality.mostValuable();
        }

        public Quality increaseTwiceAsFast() {
            int nextValue = value + 2;
            if (nextValue <= 50) {
                return new Quality(nextValue);
            }
            return Quality.mostValuable();
        }

        public Quality increaseThreeTimesAsFast() {
            int nextValue = value + 3;
            if (nextValue <= 50) {
                return new Quality(nextValue);
            }
            return Quality.mostValuable();
        }

    }

}
