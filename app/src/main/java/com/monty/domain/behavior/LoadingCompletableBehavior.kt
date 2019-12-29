package com.monty.domain.behavior

import com.sumera.koreactor.behaviour.CompletableWorker
import com.sumera.koreactor.behaviour.Messages
import com.sumera.koreactor.behaviour.MviBehaviour
import com.sumera.koreactor.behaviour.Triggers
import com.sumera.koreactor.reactor.data.MviReactorMessage
import com.sumera.koreactor.reactor.data.MviState
import io.reactivex.Observable

data class LoadingCompletableBehavior<INPUT_DATA, STATE : MviState>(
    private val triggers: Triggers<INPUT_DATA>,
    private val worker: CompletableWorker<INPUT_DATA>,
    private val cancelPrevious: Boolean,
    private val onStart: Messages<INPUT_DATA, STATE>,
    private val onError: Messages<Throwable, STATE>,
    private val onComplete: Messages<Unit, STATE>
) : MviBehaviour<STATE> {

    override fun createObservable(): Observable<out MviReactorMessage<STATE>> {
        return if (cancelPrevious) {
            triggers.merge().switchMap { createLoadingObservable(it) }
        } else {
            triggers.merge().flatMap { createLoadingObservable(it) }
        }
    }

    private fun createLoadingObservable(inputData: INPUT_DATA): Observable<MviReactorMessage<STATE>> {
        return worker.executeAsObservable(inputData)
            .map { onComplete.applyData(Unit) }
            .onErrorReturn { onError.applyData(it) }
            .startWith(onStart.applyData(inputData))
    }
}
