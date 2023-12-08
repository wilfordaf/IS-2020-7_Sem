package ru.quipy.api.aggregates.events.project

import ru.quipy.api.aggregates.ProjectAggregate
import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import java.util.*

const val TASK_CREATED_EVENT = "TASK_CREATED_EVENT"
const val TASK_ASSIGNED_EVENT = "TASK_ASSIGNED_EVENT"
const val TASK_UNASSIGNED_EVENT = "TASK_UNASSIGNED_EVENT"
const val TASK_STATUS_CHANGED_EVENT = "TASK_STATUS_CHANGED_EVENT"

@DomainEvent(TASK_CREATED_EVENT)
class TaskCreatedEvent(
    val taskId: UUID,
    val projectId: UUID,
    val title: String,
    val taskStatusId: UUID,
    createdAt: Long = System.currentTimeMillis()
) : Event<ProjectAggregate>(
    name = TASK_CREATED_EVENT,
    createdAt = createdAt
)

@DomainEvent(TASK_ASSIGNED_EVENT)
class TaskAssignedEvent(
    val taskId: UUID,
    val projectId: UUID,
    val userId: UUID,
    createdAt: Long = System.currentTimeMillis()
) : Event<ProjectAggregate>(
    name = TASK_ASSIGNED_EVENT,
    createdAt = createdAt
)

@DomainEvent(TASK_UNASSIGNED_EVENT)
class TaskUnassignedEvent(
    val taskId: UUID,
    val projectId: UUID,
    val userId: UUID,
    createdAt: Long = System.currentTimeMillis()
) : Event<ProjectAggregate>(
    name = TASK_UNASSIGNED_EVENT,
    createdAt = createdAt
)

@DomainEvent(TASK_STATUS_CHANGED_EVENT)
class TaskStatusChangedEvent(
    val taskId: UUID,
    val projectId: UUID,
    val taskStatusId: UUID,
    createdAt: Long = System.currentTimeMillis()
) : Event<ProjectAggregate>(
    name = TASK_STATUS_CHANGED_EVENT,
    createdAt = createdAt
)