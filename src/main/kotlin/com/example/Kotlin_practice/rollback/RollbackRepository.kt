package com.example.Kotlin_practice.rollback

import org.springframework.data.jpa.repository.JpaRepository

interface RollbackRepository : JpaRepository<Rollback, Long>