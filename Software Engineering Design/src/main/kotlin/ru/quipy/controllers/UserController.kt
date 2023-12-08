package ru.quipy.controllers

import org.springframework.web.bind.annotation.*
import ru.quipy.api.aggregates.UserAggregate
import ru.quipy.api.aggregates.events.user.UserCreatedEvent
import ru.quipy.api.aggregates.events.user.UserNameChangedEvent
import ru.quipy.controllers.dto.UserCreateDto
import ru.quipy.core.EventSourcingService
import ru.quipy.logic.commands.user.changeName
import ru.quipy.logic.commands.user.create
import ru.quipy.logic.states.UserAggregateState
import java.util.*
import javax.validation.constraints.Size

@RestController
@RequestMapping("/users")
class UserController(
    val userEsService: EventSourcingService<UUID, UserAggregate, UserAggregateState>
) {
    @PostMapping
    fun createUser(@RequestBody dto: UserCreateDto): UserCreatedEvent {
        return userEsService.create { it.create(dto.nickname, dto.userName, dto.password) }
    }

    @PutMapping
    fun changeName(@RequestParam
                   @Size(min = 2, max = 50, message = "User name must be from 2 to 50 symbols long")
                   userName: String?,
                   @RequestParam currentUserId: UUID): UserNameChangedEvent {
        return userEsService.update(currentUserId) { it.changeName(currentUserId, userName) }
    }

    @GetMapping("/{userId}")
    fun getUser(@PathVariable userId: UUID): UserAggregateState? {
        return userEsService.getState(userId)
    }
}