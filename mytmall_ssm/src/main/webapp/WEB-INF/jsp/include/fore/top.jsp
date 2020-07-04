<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>
<script>
    $(function () {
        $("#forelogout").click(function () {
            $.get("forelogout",function(result){
                if ("exit"===result){
                    location.reload();
                }
            });
            return false;
        });
    });
</script>
<nav class="top">
    <a href="${pageContext.request.contextPath}/fore">
        <span style="color:#C40000;margin:0" class=" glyphicon glyphicon-home redColor"></span>
        天猫首页
    </a>

    <span>喵，欢迎来天猫</span>

    <!--只有当登录后即Session中有user信息后才有退出按钮-->
    <c:if test="${!empty user}">
        <a href="loginPage">${user.username}</a>
        <a id="forelogout" href="#">退出</a>
    </c:if>

    <c:if test="${empty user}">
        <a href="loginPage">请登录</a>
        <a href="registerPage">免费注册</a>
    </c:if>

    <span class="pull-right">
            <a href="foreorder">我的订单</a>
            <a href="forecart">
            <span style="color:#C40000;margin:0px" class=" glyphicon glyphicon-shopping-cart redColor"></span>
            我的购物车<strong id="cartNum">${cartTotalItemNumber}</strong>件</a>
        </span>

</nav>
