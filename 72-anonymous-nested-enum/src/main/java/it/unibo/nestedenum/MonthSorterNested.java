package it.unibo.nestedenum;

import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;

/**
 * Implementation of {@link MonthSorter}.
 */
public final class MonthSorterNested implements MonthSorter {

    @Override
    public Comparator<String> sortByDays() {
        return new SortByDays();
    }

    @Override
    public Comparator<String> sortByOrder() {
        return new SortByMonthOrder();
    }

    private enum Month {
        JANUARY(31),
        FEBRUARY(28),
        MARCH(31),
        APRIL(30),
        MAY(31),
        JUNE(30),
        JULY(31),
        AUGUST(31),
        SEPTEMBER(30),
        OCTOBER(31),
        NOVEMBER(30),
        DECEMBER(31);

        private final int days;

        private Month(final int days){
            this.days = days;
        }

        public static Month fromString(String name){
            Month match = null;
            try {
                return Month.valueOf(name);
            } catch (IllegalArgumentException e) {
                for(var month: Month.values()){
                    if(month.toString().toUpperCase(Locale.ROOT).startsWith(name.toUpperCase(Locale.ROOT))){
                        if (match != null) {
                            throw new IllegalArgumentException(
                                "Inserted name can be interpreted by multiple months", e
                            );
                        }
                        match = month;
                    }
                }

                if (match != null) {
                    return match;
                }
                throw new IllegalArgumentException("No matching months found", e);
            }
        }
    }

    private static final class SortByMonthOrder implements Comparator<String>{

        @Override
        public int compare(String arg0, String arg1) {
            var month1 = Month.fromString(arg0);
            var month2 = Month.fromString(arg1);
            return month2.ordinal() - month1.ordinal();

            /*soluzione prof
             * return Month.fromString(arg0).compareTo(Month.fromString(arg1));
             */
            
        }
    }

    private static final class SortByDays implements Comparator<String>{

        @Override
        public int compare(String arg0, String arg1) {
            var month1 = Month.fromString(arg0);
            var month2 = Month.fromString(arg1);
            return Integer.compare(month1.days, month2.days);
        }

    }
}

