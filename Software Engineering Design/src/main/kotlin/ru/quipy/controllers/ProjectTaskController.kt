package ru.quipy.controllers

import org.springframework.web.bind.annotation.*
import ru.quipy.api.aggregates.ProjectAggregate
import ru.quipy.api.aggregates.events.project.TaskAssignedEvent
import ru.quipy.api.aggregates.events.project.TaskCreatedEvent
import ru.quipy.api.aggregates.events.project.TaskStatusChangedEvent
import ru.quipy.api.aggregates.events.project.TaskUnassignedEvent
import ru.quipy.controllers.dto.TaskCreateDto
import ru.quipy.core.EventSourcingService
import ru.quipy.logic.commands.project.assignTask
import ru.quipy.logic.commands.project.changeTaskStatus
import ru.quipy.logic.commands.project.createTask
import ru.quipy.logic.commands.project.unAssignTask
import ru.quipy.logic.states.ProjectAggregateState
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/projects/{projectId}/tasks")
class TaskController(
    val projectEsService: EventSourcingService<UUID, ProjectAggregate, ProjectAggregateState>
) {
    @PostMapping
    fun createTask(
        @PathVariable projectId: UUID,
        @RequestParam currentUserId: UUID,
        @RequestBody @Valid dto: TaskCreateDto
    ): TaskCreatedEvent {
        return projectEsService.update(projectId) { it.createTask(currentUserId, dto.title, dto.taskStatusId) }
    }

    @PutMapping("/{taskId}/assign")
    fun assignTask(
        @PathVariable projectId: UUID,
        @PathVariable taskId: UUID,
        @RequestParam currentUserId: UUID,
        @RequestParam userId: UUID
    ): TaskAssignedEvent {
        return projectEsService.update(projectId) { it.assignTask(currentUserId, taskId, userId) }
    }

    @PutMapping("/{taskId}/remove")
    fun unAssignTask(
        @PathVariable projectId: UUID,
        @PathVariable taskId: UUID,
        @RequestParam currentUserId: UUID,
        @RequestParam userId: UUID
    ): TaskUnassignedEvent {
        return projectEsService.update(projectId) { it.unAssignTask(currentUserId, taskId, userId) }
    }

    @PutMapping("/{taskId}/status")
    fun changeTaskStatus(
        @PathVariable projectId: UUID,
        @PathVariable taskId: UUID,
        @RequestParam currentUserId: UUID,
        @RequestParam taskStatusId: UUID
    ): TaskStatusChangedEvent {
        return projectEsService.update(projectId) { it.changeTaskStatus(currentUserId, taskId, taskStatusId) }
    }
}