package ru.quipy.logic.commands.project

import ru.quipy.api.aggregates.events.project.ProjectCreatedEvent
import ru.quipy.api.aggregates.events.project.ProjectTitleChangedEvent
import ru.quipy.api.aggregates.events.project.ProjectUserAddedEvent
import ru.quipy.api.aggregates.events.project.ProjectUserRemovedEvent
import ru.quipy.logic.commands.user.changeName
import ru.quipy.logic.states.ProjectAggregateState
import ru.quipy.logic.validation.ProjectAccessValidation.Companion.validateUserAccess
import java.util.*

fun ProjectAggregateState.create(currentUserId: UUID, title: String): ProjectCreatedEvent {
    return ProjectCreatedEvent(UUID.randomUUID(), title, currentUserId, UUID.randomUUID())
}

fun ProjectAggregateState.changeTitle(currentUserId: UUID, title: String): ProjectTitleChangedEvent {
    validateUserAccess(this, currentUserId)

    if (this.title == title) {
        throw java.lang.IllegalArgumentException("Project ${getId()} title can not be the same")
    }

    return ProjectTitleChangedEvent(getId(), title)
}

fun ProjectAggregateState.addUser(currentUserId: UUID, userId: UUID): ProjectUserAddedEvent {
    validateUserAccess(this, currentUserId)

    if (creatorId == userId || memberIds.contains(userId)) {
        throw IllegalArgumentException("User $userId is already in the project ${getId()}")
    }

    return ProjectUserAddedEvent(getId(), userId)
}

fun ProjectAggregateState.removeUser(currentUserId: UUID, userId: UUID): ProjectUserRemovedEvent {
    validateUserAccess(this, currentUserId)

    if (creatorId == userId) {
        throw IllegalArgumentException("Cannot remove user $userId because he is project ${getId()} creator")
    }

    if (!memberIds.contains(userId)) {
        throw IllegalArgumentException("User $userId is not in the project ${getId()}")
    }

    return ProjectUserRemovedEvent(getId(), userId)
}

