package com.myRetail.config

import com.myRetail.exception.ItemNotFoundException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.dao.DataAccessException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.http.ResponseEntity
import org.springframework.web.server.ServerWebInputException

@ControllerAdvice
class GlobalExceptionHandler {
    private val LOGGER: Logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(ServerWebInputException::class)
    fun handleInvalidRequest(e: ServerWebInputException): ResponseEntity<Any> {
        LOGGER.info("Type mismatch for ${e.methodParameter?.containingClass?.name}.${e.methodParameter?.member?.name}")
        return ResponseEntity.badRequest().body("Invalid type for request")
    }

    @ExceptionHandler(ItemNotFoundException::class)
    fun handleItemNotFoundException(e: ItemNotFoundException): ResponseEntity<String> {
        LOGGER.info("Item not found for id ${e.itemId}")
        return ResponseEntity.notFound().build<String>()
    }

    @ExceptionHandler(DataAccessException::class)
    fun handleCassandraFailure(e: DataAccessException): ResponseEntity<String> {
        LOGGER.error("Call to cassandra failed", e)
        return ResponseEntity.status(503).build()
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericExceptions(e: Exception): ResponseEntity<String> {
        LOGGER.error("Unexpected error occurred", e)
        return ResponseEntity.status(500).build()
    }
}