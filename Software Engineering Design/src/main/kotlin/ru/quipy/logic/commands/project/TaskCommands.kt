package ru.quipy.logic.commands.project

import ru.quipy.api.aggregates.events.project.*
import ru.quipy.logic.states.ProjectAggregateState
import ru.quipy.logic.states.TaskEntity
import ru.quipy.logic.validation.ProjectAccessValidation.Companion.validateUserAccess
import java.util.*

fun ProjectAggregateState.createTask(currentUserId: UUID, title: String, taskStatusId: UUID): TaskCreatedEvent {
    validateUserAccess(this, currentUserId)
    return TaskCreatedEvent(UUID.randomUUID(), getId(), title, taskStatusId)
}

fun ProjectAggregateState.assignTask(currentUserId: UUID, taskId: UUID, userId: UUID): TaskAssignedEvent {
    validateUserAccess(this, currentUserId)
    validateUserAccess(this, userId)

    if (getTask(taskId).assigneeIds.contains(userId)) {
        throw IllegalArgumentException("Task $taskId in project ${getId()} already contains assignee $userId")
    }

    return TaskAssignedEvent(taskId, getId(), userId)
}

fun ProjectAggregateState.unAssignTask(currentUserId: UUID, taskId: UUID, userId: UUID): TaskUnassignedEvent {
    validateUserAccess(this, currentUserId)

    if (!getTask(taskId).assigneeIds.contains(userId)) {
        throw IllegalArgumentException("Task $taskId in project ${getId()} does not contain assignee $userId")
    }

    return TaskUnassignedEvent(taskId, getId(), userId)
}

fun ProjectAggregateState.changeTaskStatus(currentUserId: UUID, taskId: UUID, taskStatusId: UUID): TaskStatusChangedEvent {
    validateUserAccess(this, currentUserId)

    if (!taskStatuses.containsKey(taskStatusId)) {
        throw IllegalArgumentException("Task status with id $taskStatusId does not exist in project ${getId()}")
    }

    if (getTask(taskId).taskStatusId == taskStatusId) {
        throw IllegalArgumentException("Task $taskId status is already $taskStatusId")
    }

    return TaskStatusChangedEvent(taskId, getId(), taskStatusId)
}

private fun ProjectAggregateState.getTask(taskId: UUID): TaskEntity {
    return tasks[taskId] ?: throw IllegalStateException("Task with id $taskId does not exist in project ${getId()}")
}