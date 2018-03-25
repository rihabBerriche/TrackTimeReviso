package com.reviso

import grails.gorm.transactions.Transactional
import groovy.time.TimeCategory
import groovy.time.TimeDuration
import java.util.concurrent.TimeUnit

import java.sql.Date
import java.sql.Timestamp

@Transactional
class ProjectImplService {


    def updateState(Long id) {

        def project = Project.get(id)
        log.info("updating project ": "${project.name}")

        try {
            if (project.state) {
                saveTimeSpent(project, id)
                project.state = false
                project.save()
            } else {
                project.state = true
                project.save()
            }
        } catch (Exception e) {
            return new Result([success: false, data: "Exception found  :  $e "])
        }
        return new Result([success: true])
    }

    def saveTimeSpent(Project p, long id) {
        use(groovy.time.TimeCategory) {
            def duration = (new java.util.Date() - p.lastUpdated).toMilliseconds()
            log.info("Duration Spent on the project  ": "${duration}")
            TimeSpent timeSpent = new TimeSpent(projectId: Project.get(id), timeSpent: duration).save(failOnError: true)
            log.info("Duration Spent on the project  ": "${timeSpent}")

        }
    }

    def showTimeSpent(long id) {

        def list = TimeSpent.executeQuery("select timeSpent,dateCreated  from TimeSpent p " +
                "where p.projectId = ?", [Project.get(id)])
        List timeSpent = []
        list.each { row ->
            int total = (Integer) row[0]
            timeSpent << new searchResult([timeSpent: convert(total), dateCreated: row[1]])
        }

        return new Result([success: true, data: timeSpent])

    }

    def displayTotalTimeSpent(long id) {

        def totalSeconds = TimeSpent.executeQuery("select sum(time.timeSpent) FROM TimeSpent time Where time.projectId = ? ",[Project.get(id)])
        def total = (Integer) totalSeconds[0]
        def totalTimeSpent = convert(total)
        return totalTimeSpent
    }


    def convert(int millis) {
        long hours = TimeUnit.MILLISECONDS.toHours(millis)
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1)
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1)

        String format = String.format("%02d:%02d:%02d", Math.abs(hours), Math.abs(minutes), Math.abs(seconds))

        log.info("$millis milliSeconds ToConvert  = " + format)
        return format
    }

}
