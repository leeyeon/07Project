<%@ page contentType="text/html; charset=EUC-KR" %>
<%@ page pageEncoding="EUC-KR"%>


<!DOCTYPE html>

<html lang="ko">
	
<head>
	<meta charset="EUC-KR">
	
	<!-- ���� : http://getbootstrap.com/css/   ���� -->
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<meta name="google-signin-client_id" content="53828513767-euud6p6ljlmddag1odu5j48mufms1gcr.apps.googleusercontent.com">
	
	<!--  ///////////////////////// Bootstrap, jQuery CDN ////////////////////////// -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" >
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" >
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" ></script>
	<script src="https://apis.google.com/js/platform.js?onload=renderButton" async defer></script>
	
	<!--  ///////////////////////// CSS ////////////////////////// -->
	<style>
    	 body >  div.container{ 
        	border: 3px solid #D6CDB7;
            margin-top: 10px;
        }
    </style>
    
    <!--  ///////////////////////// JavaScript ////////////////////////// -->
	<script type="text/javascript">

		//============= "�α���"  Event ���� =============
		$( function() {
			
			$("#userId").focus();
			
			$("input").bind("keydown", function(e) {
				if(e.keyCode == 13) {
					login();
				}
			})
			
			//==> DOM Object GET 3���� ��� ==> 1. $(tagName) : 2.(#id) : 3.$(.className)
			$("button").on("click" , function() {
				var id=$("input:text").val();
				var pw=$("input:password").val();
				
				if(id == null || id.length <1) {
					alert('ID �� �Է����� �����̽��ϴ�.');
					$("#userId").focus();
					return;
				}
				
				if(pw == null || pw.length <1) {
					alert('�н����带 �Է����� �����̽��ϴ�.');
					$("#password").focus();
					return;
				}
				
				$("form").attr("method","POST").attr("action","/user/login").attr("target","_parent").submit();
			});
		});	
		
		
		//============= ȸ��������ȭ���̵� =============
		$( function() {
			//==> DOM Object GET 3���� ��� ==> 1. $(tagName) : 2.(#id) : 3.$(.className)
			$("a[href='#' ]").on("click" , function() {
				self.location = "/user/addUser"
			});
			
			/* īī�� �α��� ���� */
			
			$("#kakaoLogin").on({
				"click": function() {
					//self.location = "/kakaoLoginRequest";
					window.open('/kakaoLoginRequest', 'kakaoPopup', 'width=300, height=400, scrollbars= 0, toolbar=0, menubar=no');
				},
				"mouseover": function() {
					$(this).attr("src", "../images/api/kakao_account_login_btn_medium_narrow_ov.png");
					$(this).css("cursor", "pointer");
				},
				"mouseout": function() {
					$(this).attr("src", "../images/api/kakao_account_login_btn_medium_narrow.png");
				}
			});
			
			/* ���̹� �α��� ���� */
			
			$("#naverLogin").on({
				"click": function() {
					//self.location = "/naverLoginRequest";
					window.open('/naverLoginRequest', 'naverPopup', 'width=300, height=400, scrollbars= 0, toolbar=0, menubar=no');
				},
				"mouseover": function() {
					$(this).css("cursor", "pointer");
				}
			});
			
			/* ���� �α��� ���� */
			
			$("#googleLogin").on({
				"click": function() {
					self.location = "/googleLoginRequest";
					//window.open('/naverLoginRequest', 'naverPopup', 'width=300, height=400, scrollbars= 0, toolbar=0, menubar=no');
				},
				"mouseover": function() {
					$(this).attr("src", "../images/api/google_account_dark_pressed.png");
					$(this).css("cursor", "pointer");
				},
				"mouseout": function() {
					$(this).attr("src", "../images/api/google_account_darkl_web.png");
				}
			});
			
		});
		
		
		
	</script>		
	
</head>

<body>

	<!-- ToolBar Start /////////////////////////////////////-->
	<div class="navbar  navbar-default">
        <div class="container">
        	<a class="navbar-brand" href="/index.jsp">Yeonhee's Shop</a>
   		</div>
   	</div>
   	<!-- ToolBar End /////////////////////////////////////-->	
	
	<!--  ȭ�鱸�� div Start /////////////////////////////////////-->
	<div class="container">
		<!--  row Start /////////////////////////////////////-->
		<div class="row">
		
			<div class="col-md-6">
					<img src="/images/logo-spring.png" class="img-rounded" width="100%" />
			</div>
	   	 	
	 	 	<div class="col-md-6">
	 	 	
		 	 	<br/><br/>
				
				<div class="jumbotron">	 	 	
		 	 		<h1 class="text-center">�� &nbsp;&nbsp;�� &nbsp;&nbsp;��</h1>

			        <form class="form-horizontal" style="padding-bottom: 150px">
		  
					  <div class="form-group">
					    <label for="userId" class="col-sm-3 control-label">���̵�</label>
					    <div class="col-sm-7">
					      <input type="text" class="form-control" name="userId" id="userId"  placeholder="���̵�" >
					    </div>
					  </div>
					  
					  <div class="form-group">
					    <label for="password" class="col-sm-3 control-label">��й�ȣ</label>
					    <div class="col-sm-7">
					      <input type="password" class="form-control" name="password" id="password" placeholder="�н�����" >
					    </div>
					  </div>
					  
					  <div class="form-group">
					    <div class="col-sm-offset-3 col-sm-7 text-center">
					      <button type="button" class="btn btn-primary"  >�� &nbsp;�� &nbsp;��</button>
					      <a class="btn btn-primary btn" href="#" role="button">ȸ &nbsp;�� &nbsp;�� &nbsp;��</a>
					    </div>
					  </div>
					  
					  <div class="col-sm-offset-3 col-sm-7 text-center" style="padding-top: 5px" >
							<img id="kakaoLogin"
								src="../images/api/kakao_account_login_btn_medium_narrow.png" width="222px" height="49px">
					  </div>
					  
					  <div class="col-sm-offset-3 col-sm-7 text-center" style="padding-top: 5px" >
							<img id="naverLogin" 
							src="../images/api/naver_account_login.png" width="222px" >
					  </div>
					  
					  <div class="col-sm-offset-3 col-sm-7 text-center" style="padding-top: 2px; padding-left: 11px" >
							<img id="googleLogin" 
							src="../images/api/google_account_darkl_web.png" width="230px" >
					  </div>
					  
					</form>		  
			
				</div>
			
			</div>
			
  	 	</div>
  	 	<!--  row Start /////////////////////////////////////-->
  	 	
 	</div>
 	<!--  ȭ�鱸�� div end /////////////////////////////////////-->

</body>

</html>