<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set scope="request" var="reloj" value="${requestScope['reloj']}" />
<c:set scope="request" var="storage" value="${requestScope['storage']}" />
<c:set scope="request" var="db" value="${requestScope['db']}" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="<%= request.getContextPath() %>/style.css" />
    </head>
    <body>
        <p>Son las ${reloj.time}</p>
        
        <hr/>
        
        <h1>Lista de personas</h1>
        
        <p>Hay ${db.count} registros</p>
        
        <c:if test="${db.count > 0}">
            <table border="1">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Apellido</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${db.people}" var="person">
                        <tr>
                            <td>${person.id()}</td>
                            <td>${person.name()}</td>
                            <td>${person.surname()}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
        
        <form method="POST">
            <p>
            <label for="nombre">ID</label>
            <input type="text" id="id" name="id" placeholder="Tu ID"/>
            </p>
            <p>
            <label for="nombre">Nombre</label>
            <input type="text" id="nombre" name="nombre" placeholder="Tu nombre"/>
            </p>
            <p>
            <label for="nombre">Apellido</label>
            <input type="text" id="apellido" name="apellido" placeholder="Tu apellido"/>
            </p>
            <p>
            <button type="submit">Vale</button>
            </p>
        </form>
    </body>
</html>
