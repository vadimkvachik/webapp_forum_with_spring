<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="lang_bundles.texts"/>
<!DOCTYPE html>
<html lang="${sessionScope.lang}">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="resources/css/main.css"/>
    <link rel="stylesheet" href="resources/css/tables.css"/>
    <link rel="stylesheet" href="resources/css/buttons_and_forms.css"/>
    <link rel="stylesheet" href="resources/css/popup_window.css"/>
    <link rel="icon" href="resources/images/icons/main_icon.ico">
    <title><fmt:message key="error"/> - <fmt:message key="head"/></title>
</head>

<body>
    <jsp:include page="inner_pages/header.jsp"/>

    <div id="forumBody">
        <table id="information">
            <tr>
                <td class="tableTitle">
                </td>
            </tr>
            <tr>
                <td class="information">
                    <fmt:message key="error_page_message"/>
                    <br/>${errorInfo}
                </td>
            </tr>
        </table>
    </div>
</body>
</html>