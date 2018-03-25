package com.reviso

import groovy.time.TimeCategory
import groovy.time.TimeDuration

import java.time.Duration

class TimeSpent {


    Double timeSpent
    Date dateCreated
    static belongsTo = [ projectId : Project ]

    static constraints = {
    }
}
