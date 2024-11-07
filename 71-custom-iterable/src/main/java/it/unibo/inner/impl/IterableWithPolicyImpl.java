package it.unibo.inner.impl;

import it.unibo.inner.api.IterableWithPolicy;
import it.unibo.inner.api.Predicate;

import java.util.Arrays;
import java.util.Iterator;

public class IterableWithPolicyImpl<T> implements IterableWithPolicy<T> {
    
    private final T[] array;
    private Predicate<T> filter;

    public IterableWithPolicyImpl(final T[] array){
        this(array, new Predicate<T>() {
           @Override
           public boolean test(T elem) {
               return true;
           } 
        });
    }
    public IterableWithPolicyImpl(final T[] array, final Predicate<T> filter){
        this.array = Arrays.copyOf(array, array.length);
        this.filter = filter;
    }

    @Override
    public void setIterationPolicy(Predicate<T> filter) {
        this.filter = filter;   
    }

    @Override
    public Iterator<T> iterator() {
        return new InnerIterator();
    }

    public class InnerIterator implements Iterator<T>{

        private int counter;

        @Override
        public boolean hasNext() {
            while(counter < array.length){
                if (filter.test(array[counter])) {
                    return true;
                }
                next(); 
            }
            return false;
        }

        @Override
        public T next() {
            int currentElement = counter;
            counter = counter + 1;
            return array[currentElement];
        }

    }

    
}
