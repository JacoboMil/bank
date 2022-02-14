package com.iobuilders.bank.transaction.adapter.`in`.rest

import com.iobuilders.bank.account.adapter.`in`.rest.TransactionResponseConverter
import com.iobuilders.bank.api.TransactionsApi
import com.iobuilders.bank.model.TransactionRequest
import com.iobuilders.bank.model.TransactionResponse
import com.iobuilders.bank.transaction.domain.usecase.MoneyTransferUseCase
import org.slf4j.LoggerFactory.getLogger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RestController
class TransactionController(
    private val moneyTransferService: MoneyTransferUseCase,
    private val transactionResponseConverter: TransactionResponseConverter
) : TransactionsApi {

    companion object {
        private val log = getLogger(TransactionController::class.java)
    }

    override fun createTransaction(transactionRequest: TransactionRequest): ResponseEntity<TransactionResponse> {
        log.info("createTransaction transactionRequest:$transactionRequest")
        val transaction = moneyTransferService.moneyTransfer(
            amount = transactionRequest.amount,
            destinationAccountId = transactionRequest.destinationAccountId,
            originAccountId = transactionRequest.originAccountId
        )

        val uri: URI = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(transaction.id)
            .toUri()

        return ResponseEntity.created(uri).body(transactionResponseConverter.convert(transaction))
    }

}