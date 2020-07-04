<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix='fmt' %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>

<head>
    <script src="js/jquery/2.0.0/jquery.min.js"></script>
    <link href="css/bootstrap/3.3.6/bootstrap.min.css" rel="stylesheet">
    <script src="js/bootstrap/3.3.6/bootstrap.min.js"></script>
    <link href="css/fore/style.css" rel="stylesheet">
    <script src="js/data/city.js"></script>
    <script>
        function formatMoney(num){
            num = num.toString().replace(/\$|\,/g,'');
            //isNaN() 函数用于检查其参数是否是非数字值。
            //如果参数值为 NaN 或字符串、对象、undefined等非数字值则返回 true, 否则返回 false。
            if(isNaN(num))
                num = "0";
            sign = (num == (num = Math.abs(num)));
            num = Math.floor(num*100+0.50000000001);
            cents = num%100;
            num = Math.floor(num/100).toString();
            if(cents<10)
                cents = "0" + cents;
            for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
                num = num.substring(0,num.length-(4*i+3))+','+
                    num.substring(num.length-(4*i+3));
            return (((sign)?'':'-') + num + '.' + cents);
        }

        function changeCheckCode() {
            $("#checkCode").attr("src","checkCode?time="+new Date().getTime());
        }

        $(function(){

            $("a.productDetailTopReviewLink").click(function(){
                $("div.productReviewDiv").show();
                $("div.productDetailDiv").hide();
            });
            $("a.productReviewTopPartSelectedLink").click(function(){
                $("div.productReviewDiv").hide();
                $("div.productDetailDiv").show();
            });

            $("span.leaveMessageTextareaSpan").hide();
            $("img.leaveMessageImg").click(function(){

                $(this).hide();
                $("span.leaveMessageTextareaSpan").show();
                $("div.orderItemSumDiv").css("height","100px");
            });

            $("div#footer a[href$=#nowhere]").click(function(){
                alert("模仿天猫的连接，并没有跳转到实际的页面");
            });

            $("a.wangwanglink").click(function(){
                alert("模仿旺旺的图标，并不会打开旺旺");
            });

            $("a.notImplementLink").click(function(){
                alert("该功能尚在开发中");
            });

            //显示province数据(js的for in循环当in为数组时var为索引,in为对象时var为属性)
            for (var province in arrCity){
                var option = "<option>" + arrCity[province]["name"] + "</option>";
                $("select:eq(0)").append(option);
            }
            var pcs;
            //显示city数据
            $("select:eq(0)").change(function(){
                $("select:eq(1)").html("");
                $("select:eq(2)").html("");
                var provinceName;
                $("select:eq(0)>option").each(function () {
                    if (this.selected){
                        provinceName = $(this).text();
                    }
                });
                for (var province in arrCity){
                    if (arrCity[province]["name"]===provinceName){
                        pcs = arrCity[province]["sub"];
                        for (var city in arrCity[province]["sub"]){
                            var option = "<option>" + arrCity[province]["sub"][city]["name"] + "</option>";
                            $("select:eq(1)").append(option);
                        }
                        break;
                    }
                }
            });

            //显示District数据
            $("select:eq(1)").change(function(){
                $("select:eq(2)").html("");
                var districtName;
                $("select:eq(1)>option").each(function () {
                    if (this.selected){
                        districtName = $(this).text();
                    }
                });

                for (var city in pcs){
                    if (pcs[city]["name"]===districtName){
                        for (var district in pcs[city]["sub"]){
                            var option = "<option>" + pcs[city]["sub"][district]["name"] + "</option>";
                            $("select:eq(2)").append(option);
                        }
                        break;
                    }
                }
            });
        });

    </script>
</head>

<body>
