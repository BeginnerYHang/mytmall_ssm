<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>

<c:forEach items="${ois}" var="oi" varStatus="status">
    <div class="reviewDiv">
        <div class="reviewProductInfoDiv">
            <div class="reviewProductInfoImg"><img width="400px" height="400px" src="img/productSingle/${oi.product.firstProductImage.id}.jpg"></div>
            <div class="reviewProductInfoRightDiv">
                <div class="reviewProductInfoRightText">
                        ${oi.product.name}
                </div>
                <table class="reviewProductInfoTable">
                    <tr>
                        <td width="75px">价格:</td>
                        <td><span class="reviewProductInfoTablePrice">￥<fmt:formatNumber type="number" value="${oi.product.originalPrice}" minFractionDigits="2"/></span> 元 </td>
                    </tr>
                    <tr>
                        <td>配送</td>
                        <td>快递:  0.00</td>
                    </tr>
                    <tr>
                        <td>月销量:</td>
                        <td><span class="reviewProductInfoTableSellNumber">${oi.product.saleCount}</span> 件</td>
                    </tr>
                </table>

                <div class="reviewProductInfoRightBelowDiv">
                    <span class="reviewProductInfoRightBelowImg"><img1 src="img/site/reviewLight.png"></span>
                    <span class="reviewProductInfoRightBelowText" >现在查看的是 您
于<fmt:formatDate value="${o.createDate}" pattern="yyyy年MM月dd"/>下单购买的商品 </span>
                </div>
            </div>
            <div style="clear:both"></div>
        </div>
        <div class="reviewStasticsDiv">
            <div class="reviewStasticsLeft">
                <div class="reviewStasticsLeftTop"></div>
                <div class="reviewStasticsLeftContent">累计评价 <span class="reviewStasticsNumber"> ${oi.product.reviewCount}</span></div>
                <div class="reviewStasticsLeftFoot"></div>
            </div>
            <div class="reviewStasticsRight">
                <div class="reviewStasticsRightEmpty"></div>
                <div class="reviewStasticsFoot"></div>
            </div>
        </div>

        <c:if test="${irs[status.index]}">
            <div class="makeReviewText">您已评价过该商品,无需评价了哦(实际上是不想做追加评价功能了)</div>
            <div class="reviewDivlistReviews">
                <c:forEach items="${oi.product.reviews}" var="r">
                    <div class="reviewDivlistReviewsEach">
                        <div class="reviewDate"><fmt:formatDate value="${r.createDate}" pattern="yyyy-MM-dd"/></div>
                        <div class="reviewContent">${r.content}</div>
                        <div class="reviewUserInfo pull-right">${r.user.anonymousName}<span class="reviewUserInfoAnonymous">(匿名)</span></div>
                    </div>
                </c:forEach>
            </div>
        </c:if>

        <c:if test="${!irs[status.index]}">
            <div class="makeReviewDiv">
                <form method="post" action="foredoreview">
                    <div class="makeReviewText">其他买家，需要你的建议哦！</div>
                    <table class="makeReviewTable">
                        <tr>
                            <td class="makeReviewTableFirstTD">评价商品</td>
                            <td><textarea name="content"></textarea></td>
                        </tr>
                    </table>
                    <div class="makeReviewButtonDiv">
                        <input type="hidden" name="oid" value="${o.id}">
                        <input type="hidden" name="pid" value="${oi.product.id}">
                        <button type="submit">提交评价</button>
                    </div>
                </form>
            </div>
        </c:if>
    </div>
</c:forEach>
