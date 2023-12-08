package ru.quipy.api.aggregates.events.project

import ru.quipy.api.aggregates.ProjectAggregate
import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import java.util.*

const val PROJECT_CREATED_EVENT = "PROJECT_CREATED_EVENT"
const val PROJECT_TITLE_CHANGED_EVENT = "PROJECT_TITLE_CHANGED_EVENT"
const val PROJECT_USER_ADDED_EVENT = "PROJECT_USER_ADDED_EVENT"
const val PROJECT_USER_REMOVED_EVENT = "PROJECT_USER_REMOVED_EVENT"

@DomainEvent(PROJECT_CREATED_EVENT)
class ProjectCreatedEvent(
    val projectId: UUID,
    val title: String,
    val creatorId: UUID,
    val defaultTaskStatusId: UUID,
    createdAt: Long = System.currentTimeMillis()
) : Event<ProjectAggregate>(
    name = PROJECT_CREATED_EVENT,
    createdAt = createdAt
)

@DomainEvent(PROJECT_TITLE_CHANGED_EVENT)
class ProjectTitleChangedEvent(
    val projectId: UUID,
    val title: String,
    createdAt: Long = System.currentTimeMillis()
) : Event<ProjectAggregate>(
    name = PROJECT_TITLE_CHANGED_EVENT,
    createdAt = createdAt
)

@DomainEvent(PROJECT_USER_ADDED_EVENT)
class ProjectUserAddedEvent(
    val projectId: UUID,
    val userId: UUID,
    createdAt: Long = System.currentTimeMillis()
) : Event<ProjectAggregate>(
    name = PROJECT_USER_ADDED_EVENT,
    createdAt = createdAt
)

@DomainEvent(PROJECT_USER_REMOVED_EVENT)
class ProjectUserRemovedEvent(
    val projectId: UUID,
    val userId: UUID,
    createdAt: Long = System.currentTimeMillis()
) : Event<ProjectAggregate>(
    name = PROJECT_USER_REMOVED_EVENT,
    createdAt = createdAt
)