/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.schernolyas.appmetertest;

import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sergey Chernolyas <sergey.chernolyas@gmail.com>
 */
public class Worker {

    private static final Logger LOG = Logger.getLogger(Worker.class.getName());
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Worker worker = new Worker();
        worker.executeTasks();
    }

    void executeTasks() {
        Task<?> task = null;
        while ((task = QueueHolder.getInstance().getTaskQueue().poll()) != null) {
            long now = System.currentTimeMillis();
            // We have task for executiong
            //is time for executing?
            while (task.getExecuteFrom().getTime() < now) {
                try {
                    Thread.sleep(now - task.getExecuteFrom().getTime());
                } catch (InterruptedException e) {
                }
            }
            ForkJoinPool.commonPool().submit(new TaskExecuter(task));
        }

    }

    private class TaskExecuter implements Callable<Object> {

        private Task<?> task;

        public TaskExecuter(Task<?> task) {
            this.task = task;
        }

        @Override
        public Object call() throws Exception {
            try {
                LOG.log(Level.INFO, String.format("Execute task %d ",task.getId()));
                task.getCode().call();
            } catch (Exception e) {
                LOG.log(Level.SEVERE, String.format("Get error and add task %d to DLQ",task.getId()));
                task.setThrowable(e);
                QueueHolder.getInstance().getDLQ().add(task);
            }
            return null;
        }
    }

}
