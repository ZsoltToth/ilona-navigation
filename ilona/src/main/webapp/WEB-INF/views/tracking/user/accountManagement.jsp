<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@page session="true"%>
<%@ page isELIgnored="false"%>

<!-- CSRF Protection token -->
<meta name="_csrf" content="${_csrf.token}" />
<!-- default header name is X-CSRF-TOKEN -->
<meta name="_csrf_header" content="${_csrf.headerName}" />

<script type="text/javascript">

	$('[data-toggle="tooltip"]').tooltip();
	$('[data-toggle="popover"]').popover();

	$("#userAccManUpdateDetailsBTN").click(function(event){
		event.preventDefault();
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		
		$("#userAccManUpdateDetailsErrorDIV").html("");
		var hadError = 0;
		var errorText = "";
						
		var username = document.getElementById("userAccManUsernameTXT");	
		if (username.checkValidity() == false) {
			errorText += "<p class='text-danger bg-primary'>Invalid username!</p>";
			hadError = 1;
		}
				
		var email = document.getElementById("userAccManEmailTXT");
		if(email.checkValidity()  == false) {
			errorText += "<p class='text-danger bg-primary'>Email address is invalid!</p>";
			hadError = 1;
		}

		if (Boolean(hadError) == true) {
			$("#userAccManUpdateDetailsErrorDIV").html(errorText);
		} else {
			$.ajax({
				type : "POST",
				async : true,
				url : "<c:url value='/tracking/user/accmanchangeuserdetails'></c:url>",
				beforeSend : function(xhr) {
					xhr.setRequestHeader(header, token);
				},
				data : {
					userid : $("#userAccManUseridTXT").val(),
					username : $("#userAccManUsernameTXT").val(),
					email : $("#userAccManEmailTXT").val()
				},
				success : function(result, status, xhr) {
					$("#page-wrapper").html(result);
				},
				error : function(xhr, status, error) {
					$("#userAccManUpdateDetailsErrorDIV").html("<p class='text-danger bg-primary'>An error occured!</p>"+error);	
				}
			});
		}
		
	});
	
	$("#userAccManChangePasswordBTN").click(function(event){
		event.preventDefault();
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		
		$("#userAccManChangePasswordErrorsDIV").html("");
		var hadError = 0;
		var errorText = "";
		var password1 = document.getElementById("userAccManPassword1TXT");
		var password2 = document.getElementById("userAccManPassword2TXT");
		
		if (password1.checkValidity() == false) {
			errorText += "<p class='text-danger bg-primary'>Invalid password!</p>";
			hadError = 1;
		}
		
		if ( password1.value != password2.value) {
			errorText += "<p class='text-danger bg-primary'>Passwords don't match!</p>";
			hadError = 1;
		}
		
		if(Boolean(hadError) == true) {
			$("#userAccManChangePasswordErrorsDIV").html(errorText);
		} else {
			$.ajax({
				type : "POST",
				async : true,
				url : "<c:url value='/tracking/user/accmanchangepassword'></c:url>",
				beforeSend : function(xhr) {
					xhr.setRequestHeader(header, token);
				},
				data : {
					userid : $("#userAccManUseridTXT").val(),
					password : $("#userAccManPassword1TXT").val()
				},
				success : function(result, status, xhr) {
					$("#page-wrapper").html(result);
				},
				error : function(xhr, status, error) {
					$("#userAccManChangePasswordErrorsDIV").html("<p class='text-danger bg-primary'>An error occured!</p>"+error);
				}
			});
		}
		
	});
</script>

<jsp:directive.include file="userNavbar.jsp" />


<div class="row">
<div class="col-lg-12">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h4><b>Account details change: ${successfulDetailsModification}${userDetailsChangeError}</b></h4>
		</div>
		<div class="panel-body">
			<label for="userAccManUseridTXT" id="userAccManUseridTXTLabel">Userid: READONLY<span class="glyphicon glyphicon-exclamation-sign">
			</span></label>
			<input id="userAccManUseridTXT"
				type="text" 
				readonly="readonly" 
				required="required"
				class="form-control"
				value="${useridValue}"><br/>
			
			<label for="userAccManUsernameTXT" id="userAccManUsernameTXTLabel">Username:
				<span data-toggle="popover"
					data-html="true"
					data-trigger="hover"
					data-content="${usernameRestriction}"
					title="The username can contain the following elements:"
					class="fa  fa-info-circle">
				</span>
			</label>
			<input type="text" 
				id="userAccManUsernameTXT" 
				class="form-control"
				required="required"
				pattern="${usernamePattern}"
				value="${usernameValue}"><br />

			<label for="userAccManEmailTXT" id="userAccManEmailTXTLabel">Email address:
				<span data-toggle="popover"
					data-html="true"
					data-trigger="hover"
					data-content="${emailRestriction}"
					title="The email address can contain the following elements:"
					class="fa  fa-info-circle">
				</span>
			</label>
			<input type="text"
				id="userAccManEmailTXT" 
				class="form-control" 
				required="required"
				value="${emailValue}"><br />
		
			<input type="button" id="userAccManUpdateDetailsBTN" class="btn btn-primary" value="Update account!"><br/>
					
		</div>
		
		<div class="panel-body" id="userAccManUpdateDetailsErrorDIV">
			<c:forEach var="error" items="${changeDetailsErrors}">
				<p class='text-danger bg-primary'>${error}</p>
			</c:forEach>
		</div>
	</div>
		
	<div class="panel panel-danger">
		<div class="panel-heading">
			<h4><b>Account password change: ${successfulPasswordModification}</b></h4>
			
		</div>
		
		<div class="panel-body">
			<label for="userAccManPassword1TXT" id="userAccManPassword1TXTLabel">Password:
				<span data-toggle="popover"
					data-html="true"
					data-trigger="hover"
					data-content="${passwordRestriction}"
					title="The email address can contain the following elements:"
					class="fa  fa-info-circle">
				</span>
			</label>
			<input type="password"
				id="userAccManPassword1TXT"
				class="form-control"
				required="required"
				pattern="${passwordPattern}"
				name="userAccManPassword1TXTName"
				value=""><br />
			
			<input type="password"
				id="userAccManPassword2TXT"
				required="required"
				class="form-control"
				name="userAccManPassword2TXTName"
				value=""><br />
			
			<input type="button" id="userAccManChangePasswordBTN" class="btn btn-danger" value="Change password!">
		</div>
		
		<div class="panel-body" id="userAccManChangePasswordErrorsDIV">
			<c:forEach var="error" items="${passwordErrors}">
				<p class='text-danger bg-primary'>${error}</p>
			</c:forEach>
		</div>
	</div>
</div>
</div>