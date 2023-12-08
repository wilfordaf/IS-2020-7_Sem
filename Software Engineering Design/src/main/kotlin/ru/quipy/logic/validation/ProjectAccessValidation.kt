package ru.quipy.logic.validation

import ru.quipy.logic.states.ProjectAggregateState
import java.util.*

class ProjectAccessValidation {
    companion object {
        fun validateUserAccess(project: ProjectAggregateState, currentUserId: UUID) {
            if (project.creatorId != currentUserId && !project.memberIds.contains(currentUserId)) {
                throw IllegalArgumentException("User $currentUserId does not have access to project ${project.getId()}")
            }
        }
    }
}