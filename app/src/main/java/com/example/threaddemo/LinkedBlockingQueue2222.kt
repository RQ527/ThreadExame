package com.example.threaddemo

import java.util.*
import java.util.concurrent.locks.ReentrantLock

/**
 * ... 简单阻塞队列的实现（参考了网上一些资料）
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/3/5
 */
class LinkedBlockingQueue2222<E>() {

    private val lock = ReentrantLock()
    val notFull = lock.newCondition()
    val notEmpty = lock.newCondition()

    private val max = 10
    private var num = 0

    private val list = LinkedList<E>()

    //放
    fun put (e:E):Boolean {
        lock.lock()
        try {
            while (num == max) {
               try {
                   notFull.await()
               }catch (e:InterruptedException){
                   e.printStackTrace()
               }
            }
            list.addLast(e)
            num++
            notEmpty.signal()
            return true
        } finally {
            lock.unlock()
        }
    }
    //拿
    fun take():E {
        lock.lock()
        try {
            while (num == 0) {
                try {
                    notEmpty.await()
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
            val e = list.removeFirst()
            num--
            notFull.signal()
            return e
        }finally {
            lock.unlock()
        }
    }
}