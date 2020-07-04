<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>

<!--此时a标签的href属性为空,在加入拦截器后-->
<a href="${contextPath}">
    <img id="logo" src="img/site/logo.gif" class="logo">
</a>

<form action="foresearch" method="post" >
    <div class="searchDiv">
        <!--解决param.keyword中文乱码问题:配置tomcat的server.xml中的URIEncoding与页面的pageEncoding一致-->
        <input name="keyword" value="${param.keyword}" type="text" placeholder="时尚男鞋  太阳镜 ">
        <button  type="submit" class="searchButton">搜索</button>
        <div class="searchBelow">
            <c:forEach items="${cs}" var="c" varStatus="st">
                <c:if test="${st.count>=5 and st.count<=8}">
                        <span>
                            <a href="forecategory?cid=${c.id}">
                                    ${c.name}
                            </a>
                            <c:if test="${st.count!=8}">
                                <span>|</span>
                            </c:if>
                        </span>
                </c:if>
            </c:forEach>
        </div>
    </div>
</form>
