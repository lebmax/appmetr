/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.schernolyas.appmetertest;

import java.util.Date;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Sergey Chernolyas <sergey.chernolyas@gmail.com>
 */
public class WorkerTest {

    public WorkerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of main method, of class Worker.
     */
    @Test
    public void testExecuteTasks() {
        System.out.println("executeTasks");
        Worker worker = new Worker();
        final AtomicLong counter = new AtomicLong(0);
        final AtomicLong order = new AtomicLong(0);

        Queue<Task<?>> tasks = QueueHolder.getInstance().getTaskQueue();
        Queue<Task<?>> dlq = QueueHolder.getInstance().getDLQ();
        Task okT1 = new Task();
        okT1.setId(1);
        okT1.setExecuteFrom(new Date(System.currentTimeMillis() + 1000L));
        okT1.setCode(() -> {
            counter.incrementAndGet();
            order.set(1);
            return "";
        });

        tasks.add(okT1);
        Task eT1 = new Task();
        eT1.setId(2);
        eT1.setExecuteFrom(new Date(System.currentTimeMillis() + 1000L));
        eT1.setCode(() -> {
            throw new UnsupportedOperationException("Not implemented!");
        });
        tasks.add(eT1);
        
        Task okT2 = new Task();
        okT2.setId(3);
        okT2.setExecuteFrom(new Date(System.currentTimeMillis() + 100L));
        okT2.setCode(() -> {
            counter.incrementAndGet();
            order.set(3);
            return "";
        });

        tasks.add(okT2);
        
        
        worker.executeTasks();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
        }
        assertEquals(3l, order.get());
        assertEquals(2l, counter.get());
        assertEquals(1l, dlq.size());
        
    }

}
