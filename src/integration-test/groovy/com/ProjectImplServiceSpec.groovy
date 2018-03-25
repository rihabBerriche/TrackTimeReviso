package com

import com.reviso.Project
import com.reviso.ProjectImplService
import com.reviso.ProjectService
import com.reviso.TimeSpent
import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration
import org.hibernate.SessionFactory
import spock.lang.Specification

@Integration
@Rollback
class ProjectImplServiceSpec extends Specification {
    ProjectImplService projectImplService
    ProjectService projectService
    SessionFactory sessionFactory



    def setup() {
        Project p1 = new Project(name: "trackTimer", description: " firstProject", state: false).save(flush: true, failOnError: true)
        Project p2 = new Project(name: "trackTimer2", description: " SecondProject", state: true).save(flush: true, failOnError: true)
        Project p3 = new Project(name: "trackTimer3", description: " thirdProject", state: true).save(flush: true, failOnError: true)
        TimeSpent t1 = new TimeSpent(projectId: 1 , timeSpent: 45 ).save(flush: true,failOnError: true)
        TimeSpent t2 = new TimeSpent(projectId: 1 , timeSpent: 900 ).save(flush: true,failOnError: true)
        TimeSpent t3= new TimeSpent(projectId: 1 , timeSpent: 788 ).save(flush: true,failOnError: true)

        TimeSpent t4 = new TimeSpent(projectId: 2 , timeSpent: 504 ).save(flush: true,failOnError: true)
        TimeSpent t5 = new TimeSpent(projectId: 3 , timeSpent: 565 ).save(flush: true,failOnError: true)

    }

    void "test update state of Project 1 to true "() {
        when : projectImplService.updateState(1)

        then:
        def response =Project.findById(1)
        with(response){
            state
            response.id == 1
        }

    }


    void "test saveTimeSpent"() {
        given:
        Project p1 = new Project(name: "trackTimer", description: " firstProject", state: false).save()

        when:
        projectImplService.saveTimeSpent( p1, 1 )
        then:
        def response =TimeSpent.findById(1)
        with(response){
            response.timeSpent !=0
        }
    }

    void "test showTimeSpent"() {
        given :
        Date date
        projectImplService.metaClass.convert = {int  o -> '00:00:89' }

        when :
       def response =  projectImplService.showTimeSpent( 1 )

        expect:

        response.success
        response.data == [ '00:00:89', date]



    }

}
