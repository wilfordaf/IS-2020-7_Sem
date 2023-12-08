package ru.quipy.logic.states

import ru.quipy.api.aggregates.ProjectAggregate
import ru.quipy.api.aggregates.events.project.*
import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import java.util.*

const val DEFAULT_TASK_STATUS_TITLE = "Created"
const val DEFAULT_TASK_STATUS_COLOR_RGB = 3329330

class ProjectAggregateState : AggregateState<UUID, ProjectAggregate> {
    private lateinit var projectId: UUID
    var createdAt: Long = System.currentTimeMillis()
    var updatedAt: Long = System.currentTimeMillis()

    lateinit var title: String
    lateinit var creatorId: UUID

    var memberIds = mutableSetOf<UUID>()
    var tasks = mutableMapOf<UUID, TaskEntity>()
    var taskStatuses = mutableMapOf<UUID, TaskStatusEntity>()

    override fun getId() = projectId

    @StateTransitionFunc
    fun apply(event: ProjectCreatedEvent) {
        projectId = event.projectId
        title = event.title
        creatorId = event.creatorId

        taskStatuses[event.defaultTaskStatusId] = createDefaultTaskStatus(event.defaultTaskStatusId)

        createdAt = event.createdAt
        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun apply(event: ProjectTitleChangedEvent) {
        title = event.title
        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun apply(event: ProjectUserAddedEvent) {
        memberIds.add(event.userId)
        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun apply(event: ProjectUserRemovedEvent) {
        memberIds.remove(event.userId)
        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun apply(event: TaskStatusCreatedEvent) {
        taskStatuses[event.taskStatusId] = TaskStatusEntity(event.taskStatusId, event.title, event.colorRgb)
        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun apply(event: TaskStatusDeletedEvent) {
        taskStatuses.remove(event.taskStatusId)
        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun apply(event: TaskCreatedEvent) {
        tasks[event.taskId] = TaskEntity(event.taskId, event.title, event.taskStatusId)
        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun apply(event: TaskAssignedEvent) {
        tasks.getValue(event.taskId).assigneeIds.add(event.userId)
        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun apply(event: TaskUnassignedEvent) {
        tasks.getValue(event.taskId).assigneeIds.remove(event.userId)
        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun apply(event: TaskStatusChangedEvent) {
        tasks.getValue(event.taskId).taskStatusId = event.taskStatusId
        updatedAt = event.createdAt
    }
}

data class TaskEntity(
    val id: UUID = UUID.randomUUID(),
    val title: String,
    var taskStatusId: UUID,
    val assigneeIds: MutableSet<UUID> = mutableSetOf()
)

data class TaskStatusEntity(
    val id: UUID = UUID.randomUUID(),
    val title: String,
    val colorRgb: Int
)

private fun createDefaultTaskStatus(id: UUID) : TaskStatusEntity {
    return TaskStatusEntity(
        id,
        DEFAULT_TASK_STATUS_TITLE,
        DEFAULT_TASK_STATUS_COLOR_RGB
    )
}
