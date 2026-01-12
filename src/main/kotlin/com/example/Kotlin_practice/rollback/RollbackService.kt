package com.example.Kotlin_practice.rollback

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.IOException

@Service
class RollbackService(
    private val rollbackRepository: RollbackRepository
) {

    @Transactional
    fun throwRuntimeException() {
        rollbackRepository.save(Rollback(name = "runtime"))

        throw RuntimeException("runtime exception")
    }

    @Transactional
    fun throwCheckedException() {
        rollbackRepository.save(Rollback(name = "checked"))

        throw IOException("checked exception")
    }

    @Transactional(rollbackFor = [IOException::class])
    fun throwCheckedExceptionWithRollback() {
        rollbackRepository.save(Rollback(name = "checked-rollback"))

        throw IOException("checked exception")
    }
}
