package com.iobuilders.bank.transaction.adapter.`in`.rest

import com.iobuilders.bank.api.TransactionsApi
import com.iobuilders.bank.model.TransactionRequest
import com.iobuilders.bank.model.TransactionResponse
import com.iobuilders.bank.model.TransactionsResponse
import com.iobuilders.bank.transaction.TransactionService
import org.iban4j.Iban
import org.slf4j.LoggerFactory.getLogger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI
import java.util.*

@RestController
class TransactionController(
    private val transactionService: TransactionService,
    private val transactionResponseConverter: TransactionResponseConverter,
    private val transactionsResponseConverter: TransactionsResponseConverter
) : TransactionsApi {

    companion object {
        private val log = getLogger(TransactionController::class.java)
    }

    override fun createTransaction(transactionRequest: TransactionRequest): ResponseEntity<TransactionResponse> {
        log.info("createTransaction transactionRequest:$transactionRequest")
        val transaction = transactionService.createTransaction(
            amount = transactionRequest.amount,
            destinationAccountIban = transactionRequest.destinationAccountIban,
            originAccountIban = transactionRequest.originAccountIban
        )

        val uri: URI = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(transaction.id)
            .toUri()

        return ResponseEntity.created(uri).body(transactionResponseConverter.convert(transaction))
    }

    override fun getTransactionById(transactionId: UUID): ResponseEntity<TransactionResponse> {
        log.info("getTransactionById transactionId:$transactionId")
        return ResponseEntity.ok(
            transactionResponseConverter.convert(
                transactionService.getTransactionById(transactionId)
            )
        )
    }
    override fun getTransactions(originAccountIban: String?, destinationAccountIban: String?): ResponseEntity<TransactionsResponse> {
        log.info("getTransactions")
        return ResponseEntity.ok(
            transactionsResponseConverter.convert(
                transactionService.getTransactions(originAccountIban, destinationAccountIban)
            )
        )
    }

}