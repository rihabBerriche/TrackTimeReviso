package com.reviso

/**
 * Created by rbe on 24/03/2018.
 */
class Result {


    public static final int OK_STATUS = 200
    public static final int BAD_REQUEST = 300
    public static final int NOT_FOUND = 404

    boolean success
    int status
    def data = [:]
    def error

}
