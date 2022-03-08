package com.example.threaddemo

import java.util.concurrent.locks.ReentrantLock

/**
 * ... 实现了一个简单的线程池
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/3/7
 */
class SimpleThreadPool(private val poolSize: Int) {
    //线程状态
    @Volatile
    var runState = RUNNING

    companion object {
        const val RUNNING = 0
        const val SHUTDOWN = 1
        const val STOP = 2
        const val TERMINATED = 3
    }

    private val queue = LinkedBlockingQueue2222<Runnable>()
    private val lock = ReentrantLock()
    private val work = HashSet<WorkThread>()

    init {
        init()
    }

    /**
     * 提交任务
     */
    fun execute(task: Runnable?): Boolean {
        if (task == null) {
            throw KotlinNullPointerException()
        }
        if (runState != RUNNING) {
            return false
        }
        return queue.put(task)
    }

    /**
     * 初始化
     */
    private fun init() {
        for (i in 0 until poolSize) {
            val w = WorkThread()
            work.add(w)
            w.start()
        }
    }

    //此时不能再添加新任务，但已添加的任务会执行完
    fun shutdown() {
        runState = SHUTDOWN
    }

    //此时不能再添加新任务，并尝试停止运行任务，队列中的任务可能不会全部运行完。
    fun shutdownNow() {
        runState = STOP
    }

    //工作线程
    inner class WorkThread() : Thread() {
        private var running = true
        override fun run() {
            while (running) {
                val task: Runnable? = getTask()
                if (task != null) {
                    task.run()
                } else {
                    running = false
                    lock.lock()
                    work.remove(this)
                    if (work.size == 0) {
                        runState = TERMINATED

                    }
                    lock.unlock()
                }
            }
        }
    }

    //从任务队列获取任务
    private fun getTask(): Runnable? {
        if (runState > SHUTDOWN) {
            return null
        } else {
            try {
                return queue.take()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
        return null
    }

}