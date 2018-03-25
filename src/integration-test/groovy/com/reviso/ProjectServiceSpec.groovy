package com.reviso

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class ProjectServiceSpec extends Specification {

    ProjectService projectService
    SessionFactory sessionFactory

    private Long setupData() {

         Project p1= new Project(name: "trackTimer", description: " firstProject", state: false).save(flush: true, failOnError: true)
         Project p2= new Project(name: "trackTimer2", description: " SecondProject", state: true).save(flush: true, failOnError: true)
         Project p3= new Project(name: "trackTimer3", description: " thirdProject",  state: true).save(flush: true, failOnError: true)

    }

    void "test update"() {
        setupData()

        expect:
        projectService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Project> projectList = projectService.list(max: 2, offset: 2)

        then:
        projectList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        projectService.count() == 5
    }

    void "test delete"() {
        Long projectId = setupData()

        expect:
        projectService.count() == 5

        when:
        projectService.delete(projectId)
        sessionFactory.currentSession.flush()

        then:
        projectService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Project project = new Project()
        projectService.save(project)

        then:
        project.id != null
    }
}
