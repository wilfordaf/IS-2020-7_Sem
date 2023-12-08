package ru.quipy.controllers

import org.springframework.web.bind.annotation.*
import ru.quipy.api.aggregates.ProjectAggregate
import ru.quipy.api.aggregates.UserAggregate
import ru.quipy.api.aggregates.events.project.ProjectCreatedEvent
import ru.quipy.api.aggregates.events.project.ProjectTitleChangedEvent
import ru.quipy.api.aggregates.events.project.ProjectUserAddedEvent
import ru.quipy.api.aggregates.events.project.ProjectUserRemovedEvent
import ru.quipy.core.EventSourcingService
import ru.quipy.logic.commands.project.addUser
import ru.quipy.logic.commands.project.changeTitle
import ru.quipy.logic.commands.project.create
import ru.quipy.logic.commands.project.removeUser
import ru.quipy.logic.states.ProjectAggregateState
import ru.quipy.logic.states.UserAggregateState
import java.lang.IllegalStateException
import java.util.*
import javax.validation.constraints.Size

@RestController
@RequestMapping("/projects")
class ProjectController(
    val userEsService: EventSourcingService<UUID, UserAggregate, UserAggregateState>,
    val projectEsService: EventSourcingService<UUID, ProjectAggregate, ProjectAggregateState>
) {
    @PostMapping
    fun createProject(
        @RequestParam
        @Size(min = 2, max = 50, message = "Title must be from 2 to 50 characters long")
        title: String,
        @RequestParam currentUserId: UUID) : ProjectCreatedEvent {

        if (userEsService.getState(currentUserId) == null) {
            throw IllegalStateException("User with id $currentUserId was not registered")
        }

        return projectEsService.create { it.create(currentUserId, title) }
    }

    @PutMapping("/{projectId}/title")
    fun changeTitle(
        @PathVariable
        projectId: UUID,
        @RequestParam
        @Size(min = 2, max = 50, message = "Title must be from 2 to 50 characters long")
        title: String,
        @RequestParam currentUserId: UUID) : ProjectTitleChangedEvent {
        return projectEsService.update(projectId) { it.changeTitle(currentUserId, title) }
    }

    @PostMapping("/{projectId}/user/add")
    fun addProjectUser(
        @PathVariable projectId: UUID,
        @RequestParam currentUserId: UUID,
        @RequestParam userId: UUID
    ): ProjectUserAddedEvent {
        return projectEsService.update(projectId) { it.addUser(currentUserId, userId) }
    }

    @PostMapping("/{projectId}/user/remove")
    fun removeProjectUser(
        @PathVariable projectId: UUID,
        @RequestParam currentUserId: UUID,
        @RequestParam userId: UUID
    ): ProjectUserRemovedEvent {
        return projectEsService.update(projectId) { it.removeUser(currentUserId, userId) }
    }

    @GetMapping("/{projectId}")
    fun getProject(@PathVariable projectId: UUID) : ProjectAggregateState? {
        return projectEsService.getState(projectId)
    }
}