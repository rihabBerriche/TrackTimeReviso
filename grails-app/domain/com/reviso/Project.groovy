package com.reviso

import grails.rest.Resource

class Project {
    String name
    //State state = State.PAUSED
    String description
    boolean state
    Date lastUpdated
    Date dateCreated

    static constraints = {
    }
}
