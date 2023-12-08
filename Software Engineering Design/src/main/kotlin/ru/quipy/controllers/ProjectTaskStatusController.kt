package ru.quipy.controllers

import org.springframework.web.bind.annotation.*
import ru.quipy.api.aggregates.ProjectAggregate
import ru.quipy.api.aggregates.events.project.TaskStatusCreatedEvent
import ru.quipy.api.aggregates.events.project.TaskStatusDeletedEvent
import ru.quipy.controllers.dto.TaskStatusCreateDto
import ru.quipy.core.EventSourcingService
import ru.quipy.logic.commands.project.createTaskStatus
import ru.quipy.logic.commands.project.deleteTaskStatus
import ru.quipy.logic.states.ProjectAggregateState
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/projects/{projectId}/tasks/statuses")
class ProjectTaskStatusController(
    val projectEsService: EventSourcingService<UUID, ProjectAggregate, ProjectAggregateState>
) {
    @PostMapping
    fun createTaskStatus(
        @PathVariable projectId: UUID,
        @RequestParam currentUserId: UUID,
        @RequestBody @Valid dto: TaskStatusCreateDto
    ): TaskStatusCreatedEvent {
        return projectEsService.update(projectId) { it.createTaskStatus(currentUserId, dto.title, dto.colorRgb) }
    }

    @DeleteMapping("/{taskStatusId}")
    fun deleteTaskStatus(
        @PathVariable projectId: UUID,
        @PathVariable taskStatusId: UUID,
        @RequestParam currentUserId: UUID
    ): TaskStatusDeletedEvent {
        return projectEsService.update(projectId) { it.deleteTaskStatus(currentUserId, taskStatusId) }
    }
}