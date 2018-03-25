package com.reviso

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class ProjectController {

    ProjectService projectService
    ProjectImplService projectImplService
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]


    def index() {

        respond projectService.list(params), model: [projectCount: projectService.count(), projects: projectService.list(params), totalTimeSpent: flash.totalTime]
    }

    def show(Long id) {
        respond projectService.get(id)
    }

    def showReport(Long id) {

        def totalTimeSpent = projectImplService.displayTotalTimeSpent(id)
        Result response = projectImplService.showTimeSpent(id)
        render(view: "showReport", model: [list: response.data, totalTimeSpent: totalTimeSpent])
    }


    def updateState(Long id) {

        def result = projectImplService.updateState(id)
        flash.totalTime = projectImplService.displayTotalTimeSpent(id)

        if (result.success) {
            redirect(action: "index")
        } else {
            render(view: 'index', model: [errorMessage: result.data])
        }
    }


    def create() {
        respond new Project(params)
    }

    def save(Project project) {
        if (project == null) {
            notFound()
            return
        }

        try {
            projectService.save(project)
        } catch (ValidationException e) {
            respond project.errors, view: 'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'project.label', default: 'Project'), project.id])
                redirect project
            }
            '*' { respond project, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond projectService.get(id)
    }

    def update(Project project) {
        if (project == null) {
            notFound()
            return
        }

        try {
            projectService.save(project)
        } catch (ValidationException e) {
            respond project.errors, view: 'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'project.label', default: 'Project'), project.id])
                redirect project
            }
            '*' { respond project, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        projectService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'project.label', default: 'Project'), id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
