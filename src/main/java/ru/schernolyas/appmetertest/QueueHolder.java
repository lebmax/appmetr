/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.schernolyas.appmetertest;

import java.util.Comparator;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 *
 * @author Sergey Chernolyas <sergey.chernolyas@gmail.com>
 */
public class QueueHolder {

    private PriorityBlockingQueue<Task<?>> taskQueue = new PriorityBlockingQueue(10, new TaskComparator());
    private LinkedBlockingQueue<Task<?>> dlq = new LinkedBlockingQueue<>();

    private QueueHolder() {
    }

    public static QueueHolder getInstance() {
        return QueueHolderHolder.INSTANCE;
    }

    private static class QueueHolderHolder {

        private static final QueueHolder INSTANCE = new QueueHolder();
    }

    public Queue<Task<?>> getTaskQueue() {
        return taskQueue;
    }

    public Queue<Task<?>> getDLQ() {
        return dlq;
    }

    private static class TaskComparator implements Comparator<Task<?>> {

        @Override
        public int compare(Task<?> o1, Task<?> o2) {
            return o1.getExecuteFrom().compareTo(o2.getExecuteFrom());
        }
    }

}
