<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix='fmt' %> 

<html>

<head>
	<!--相对于当前地址栏请求路径的地址-->
	<!--因为从静态的jsp路径来看它和js的路径相对关系是../js/，但是往往很多时候我们不是直接访问jsp页面的，
	是通过其他的jsp页面或者servlet，或者struts的action通过forward的方式转发过来访问的，这时候请求的
	当前路径就不是该jsp的路径，而是转发过来之前那个jsp，servlet或action的路径，所以和js的相对路径关系
	就可能不再是../js/了，而在实际使用中，访问同一个jsp可能由很多不同的来源，那么它的相对路径关系可能随时
	都可能改变，这时候jsp页面里写死的相对路径就无法访问到对应的资源了。所以要使用绝对路径访问。
	-->
	<!--
	而真正的相对于web工程的绝对路径写法是：/ 代表url根路径，例如http://localhost:8080/web/js/jquery.js里的http://localhost:8080/，而./代表web工程根路径http://localhost:8080/web/
	所以你还可以这么写：
	1. /web/js/jquery.js
	2. ./js/jquery.js(第二种写法)
	-->
	<script src="js/jquery/jquery-1.11.0.min.js"></script>
	<link href="css/bootstrap/3.3.6/bootstrap.min.css" rel="stylesheet">
	<script src="js/bootstrap/3.3.6/bootstrap.min.js"></script>
	<link href="css/back/style.css" rel="stylesheet">
	
<script>
function checkEmpty(id, name){
	var value = $("#"+id).val();
	if(value.length==0){
		alert(name+ "不能为空");
		$("#"+id).focus();
		return false;
	}
	return true;
}
function checkNumber(id, name){
	var value = $("#"+id).val();
	if(value.length==0){
		alert(name+ "不能为空");
		$("#"+id).focus();
		return false;
	}
	if(isNaN(value)){
		alert(name+ "必须是数字");
		$("#"+id).focus();
		return false;
	}
	
	return true;
}
function checkInt(id, name){
	var value = $("#"+id).val();
	if(value.length==0){
		alert(name+ "不能为空");
		$("#"+id)[0].focus();
		return false;
	}
	if(parseInt(value)!=value){
		alert(name+ "必须是整数");
		$("#"+id).focus();
		return false;
	}
	
	return true;
}

$(function(){
    //监听所有删除的a标签,进行删除确认
	$("a").click(function(){
		var deleteLink = $(this).attr("deleteLink");
		console.log(deleteLink);
		if("true"==deleteLink){
			var confirmDelete = confirm("确认要删除");
			return confirmDelete;
		}
	});
})
</script>	
</head>
<body>

