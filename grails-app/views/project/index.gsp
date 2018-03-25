<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'project.label', default: 'Project')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#list-project" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="list-project" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <table>
                <thead>
                <tr>
                    <th>ProjectName</th>
                    <th>Status</th>
                    <th>Tasks</th>
                    <th>lastUpdated</th>
                    <th>Report</th>

                </tr>
                </thead>
                <tbody>
                <g:each in="${projects}" var="record" status="i">
                    <tr class="${(i % 2) == 0 ? 'even' : 'odd'}" id="${record.id}">
                        <td>
                            <g:link action="show" params="[id: record.id]">${record.name}</g:link>
                        </td>
                        <td>
                            <g:if test="${record.state == true }">
                                <g:link action="updateState" style="color: #cc3333" params="[id: record.id]">Pause</g:link>
                            </g:if>
                            <g:else>
                                <g:link action="updateState" params="[id: record.id]">Back to Work</g:link>
                            </g:else>
                        </td>
                        <td>
                            <g:link action="index" controller = "task" params="[id: record.id]">Tasks</g:link>
                        </td>
                        <td>${record.lastUpdated}</td>
                        <td>
                            <g:link action="showReport"  params="[id: record.id]">Overview</g:link>
                        </td>

                    </tr>
                </g:each>
                </tbody>
            </table>

            %{--<f:table collection="${projectList}" />--}%

            %{--<div class="pagination">--}%
                %{--<g:paginate total="${projectCount ?: 0}" />--}%
            %{--</div>--}%
        </div>
    </body>
</html>