package com.reviso

class Task {
    String taskId
    Type type
    Priority priority
    String description
    State state
    Date dateCreated
    Date lastUpdated
    static belongsTo = [ projectId : Project ]
    static constraints = {
    }
}
