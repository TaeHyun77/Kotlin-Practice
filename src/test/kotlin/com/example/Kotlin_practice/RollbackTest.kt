package com.example.Kotlin_practice

import com.example.Kotlin_practice.rollback.RollbackRepository
import com.example.Kotlin_practice.rollback.RollbackService
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.io.IOException
import kotlin.test.Test
import kotlin.test.assertEquals

@SpringBootTest
class RollbackTest {

    @Autowired
    lateinit var rollbackService: RollbackService

    @Autowired
    lateinit var rollbackRepository: RollbackRepository

    @AfterEach
    fun clear() {
        rollbackRepository.deleteAll()
    }

    /*
    * 코틀린에서도 checked exception은 기본적으로 롤백이 되지 않으며, 롤백을 시키려면 rollbackFor을 지정해야 함
    *
    * */

    @Test
    fun `RuntimeException 발생 시 트랜잭션은 롤백된다`() {
        assertThrows<RuntimeException> {
            rollbackService.throwRuntimeException()
        }

        // 롤백되었으므로 데이터 없음
        assertEquals(0, rollbackRepository.count())
    }

    @Test
    fun `Checked Exception 발생 시 기본적으로 롤백되지 않는다`() {
        assertThrows<IOException> {
            rollbackService.throwCheckedException()
        }

        // 커밋됨
        assertEquals(1, rollbackRepository.count())
    }

    @Test
    fun `Checked Exception이지만 rollbackFor 지정 시 롤백된다`() {
        assertThrows<IOException> {
            rollbackService.throwCheckedExceptionWithRollback()
        }

        // 롤백됨
        assertEquals(0, rollbackRepository.count())
    }
}
