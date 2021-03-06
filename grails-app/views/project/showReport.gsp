<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'project.label', default: 'project')}" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
<a href="#show-project" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
    </ul>
</div>
<div id="show-project" class="content scaffold-show" role="main">
    <h1><g:message code="default.show.label" args="[entityName]" /></h1>

    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>

    <f:display bean="project" />

    <h1>Total Time Spent : ${totalTimeSpent} </h1>
    <table>
        <thead>
        <tr>
            <th>Date</th>
            <th>timeSpent</th>
        </tr>
        </thead>
        <tbody>
        <g:each in="${list}" var="record" status="i">
            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                <td>
                    <% def val1 = new java.text.SimpleDateFormat('yyyyy-mm-dd hh:mm').format(record.dateCreated) %>
                     ${val1}

                </td>
                <td>
                    ${record.timeSpent}
                </td>

            </tr>
        </g:each>
        </tbody>
    </table>

</div>
</body>
</html>
