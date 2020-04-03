package com.example.sydemo.model;

import androidx.annotation.NonNull;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class basemodel2<Item> implements Iterable {
    public class Node{
        Item item;
        Node next;
    }
    private Node first;
    @NonNull
    @Override
    public Iterator iterator() {
        return null;
    }

    @Override
    public void forEach(@NonNull Consumer action) {

    }

    @NonNull
    @Override
    public Spliterator spliterator() {
        return null;
    }
    public class ListIteratorr implements Iterator<Item>{
        private Node current=first;
        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public Item next() {
            if (current!=null) {
                Node node = current;
                current = current.next;
                return node.item;
            }
            return null;
        }
    }
}
