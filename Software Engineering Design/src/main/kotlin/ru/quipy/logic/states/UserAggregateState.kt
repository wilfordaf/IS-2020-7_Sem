package ru.quipy.logic.states

import ru.quipy.api.aggregates.UserAggregate
import ru.quipy.api.aggregates.events.user.UserCreatedEvent
import ru.quipy.api.aggregates.events.user.UserNameChangedEvent
import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import java.util.*

class UserAggregateState : AggregateState<UUID, UserAggregate> {
    private lateinit var userId: UUID
    var createdAt: Long = System.currentTimeMillis()
    var updatedAt: Long = System.currentTimeMillis()

    lateinit var nickname: String
    lateinit var hashPassword: String

    var userName: String? = null

    override fun getId() = userId

    @StateTransitionFunc
    fun apply(event: UserCreatedEvent) {
        userId = event.userId
        nickname = event.nickname
        userName = event.userName
        hashPassword = event.hashPassword
        createdAt = event.createdAt
        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun apply(event: UserNameChangedEvent) {
        userName = event.userName
        updatedAt = event.createdAt
    }
}