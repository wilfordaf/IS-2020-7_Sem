package ru.quipy.logic.commands.project

import ru.quipy.api.aggregates.events.project.TaskStatusCreatedEvent
import ru.quipy.api.aggregates.events.project.TaskStatusDeletedEvent
import ru.quipy.logic.states.ProjectAggregateState
import ru.quipy.logic.validation.ProjectAccessValidation.Companion.validateUserAccess
import java.util.*

fun ProjectAggregateState.createTaskStatus(currentUserId: UUID, title: String, colorRgb: Int): TaskStatusCreatedEvent {
    validateUserAccess(this, currentUserId)
    return TaskStatusCreatedEvent(UUID.randomUUID(), getId(), title, colorRgb)
}

fun ProjectAggregateState.deleteTaskStatus(currentUserId: UUID, taskStatusId: UUID): TaskStatusDeletedEvent {
    validateUserAccess(this, currentUserId)

    if (!taskStatuses.containsKey(taskStatusId)) {
        throw IllegalArgumentException("Project ${getId()} does not contain task status $taskStatusId")
    }

    if (tasks.any { t -> t.value.taskStatusId == taskStatusId }) {
        throw IllegalArgumentException("Cannot delete task status $taskStatusId because there are associated tasks")
    }

    return TaskStatusDeletedEvent(taskStatusId, getId())
}
