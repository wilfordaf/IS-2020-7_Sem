package ru.quipy.logic.commands.user

import at.favre.lib.crypto.bcrypt.BCrypt
import ru.quipy.api.aggregates.events.user.UserCreatedEvent
import ru.quipy.api.aggregates.events.user.UserNameChangedEvent
import ru.quipy.logic.states.UserAggregateState
import java.lang.IllegalArgumentException
import java.util.*

fun UserAggregateState.create(nickname: String, userName: String?, password: String): UserCreatedEvent {
    val hashPassword = BCrypt.withDefaults().hashToString(10, password.toCharArray())
    return UserCreatedEvent(UUID.randomUUID(), nickname, userName, hashPassword)
}

fun UserAggregateState.changeName(currentUserId: UUID, userName: String?): UserNameChangedEvent {
    if (currentUserId != getId()) {
        throw IllegalArgumentException("User $currentUserId can not change name of ${getId()}")
    }

    if (this.userName == userName) {
        throw IllegalArgumentException("User $currentUserId name can not be the same")
    }

    return UserNameChangedEvent(currentUserId, userName)
}