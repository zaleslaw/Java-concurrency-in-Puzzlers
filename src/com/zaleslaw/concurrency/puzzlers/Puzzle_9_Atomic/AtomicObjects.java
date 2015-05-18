package com.zaleslaw.concurrency.puzzlers.Puzzle_9_Atomic;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicReference;

public class AtomicObjects {

    private static Atom a;
    private static NotAtom na;
    private static AtomicInteger id;
    private static AtomicReference<String> name;
    private static AtomicIntegerArray kwarks;
    private static String naname;
    private static Integer[] nakwarks;
    private static Integer naid;

    private static class Atom {

        AtomicReference<String> name;
        AtomicIntegerArray kwarks;
        AtomicInteger id;

        public AtomicReference<String> getName() {
            return name;
        }

        public AtomicIntegerArray getKwarks() {
            return kwarks;
        }

        public AtomicInteger getId() {
            return id;
        }

        public Atom(AtomicReference<String> name, AtomicIntegerArray kwarks, AtomicInteger id) {
            this.name = name;
            this.kwarks = kwarks;
            this.id = id;
        }
    }

    private static class NotAtom {

        String name;
        Integer[] kwarks;
        Integer id;

        public NotAtom(String name, Integer[] kwarks, Integer id) {
            this.name = name;
            this.kwarks = kwarks;
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public Integer[] getKwarks() {
            return kwarks;
        }

        public Integer getId() {
            return id;
        }
    }

    public static void main(String[] args) throws InterruptedException {


        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                id = new AtomicInteger(1);

                name = new AtomicReference<>("AtomName1");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                kwarks = new AtomicIntegerArray(10);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < 10; i++) {
                    kwarks.getAndSet(i, i);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                a = new Atom(name, kwarks, id);

                naname = "NotAtomName1";
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                nakwarks = new Integer[10];
                for (int i = 0; i < 10; i++) {
                    nakwarks[i] = i;
                    Thread.yield();
                }
                naid = new Integer(1);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                na = new NotAtom(naname, nakwarks, naid);
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                id = new AtomicInteger(2);
                try {
                    Thread.sleep(12);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                name = new AtomicReference<>("AtomName2");
                kwarks = new AtomicIntegerArray(10);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < 10; i++) {
                    kwarks.getAndSet(i, -i);
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                a = new Atom(name, kwarks, id);

                naname = "NotAtomName2";
                nakwarks = new Integer[10];
                for (int i = 0; i < 10; i++) {
                    nakwarks[i] = -i;
                    try {
                        Thread.sleep(40);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                naid = new Integer(2);
                try {
                    Thread.sleep(8);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                na = new NotAtom(naname, nakwarks, naid);
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("a " + a.getId() + " " + a.getName() + " " + a.getKwarks().length());
        for (int i = 0; i < a.getKwarks().length(); i++) {
            System.out.println(a.getKwarks().get(i));
        }

        System.out.println("na " + na.getId() + " " + na.getName() + " " + na.getKwarks().length);
        for (int i = 0; i < na.getKwarks().length; i++) {
            System.out.println(na.getKwarks()[i]);
        }


    }
}
