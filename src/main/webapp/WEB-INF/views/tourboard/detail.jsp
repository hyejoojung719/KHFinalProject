<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>title here</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
	crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.15.4/css/all.css"
	integrity="sha384-DyZ88mC6Up2uqS4h/KRgHuoeGwBcD4Ng9SiP4dIRy0EXTlnuz47vAwmeGwVChigm"
	crossorigin="anonymous">
<!-- include libraries(jQuery, bootstrap) -->
<link
	href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css"
	rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<!-- include summernote css/js -->
<link
	href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>
	<jsp:include page="../base/header.jsp"></jsp:include>
<style>

* {
	box-sizing: border-box;
}

body {
	margin: 0; /* 임시로 body margin 0px */
}

/* div{border:1px solid black} */
.banner {
	background-color: rgb(56, 181, 174);
	height: 200px;
}

.banner_title {
	width: 100%;
	height: 70%;
	color: white;
	font-size: 35px;
	font-weight: 600;
	padding: 60px 0px 10px 100px;
}

.banner_content {
	width: 100%;
	height: 30%;
	color: white;
	font-size: 15px;
	font-weight: 500;
	padding-left: 100px;
}

.container {
	border: 1px solid red;
}

.container>.root {
	padding-left: 80px;
	overflow: auto;
}

.root>div {
	border: 1px solid red;
	float: left;
	margin: 40px 0px 40px 0px;
	padding-right: 10px;
}

.writeForm {
	border: 1px solid red;
}

.catetitle {
	overflow: auto;
	padding: 0px 80px 0px 80px;
}

.category {
	width: 10%;
	float: left;
}

.title {
	border: 1px solid red;
	width: 90%;
	float: left;
}

.title>input {
	width: 100%;
}

.writeForm>div {
	border: 1px solid red;
}

.contents {
	padding-right: 80px;
}

.ft_btn {
	border: 1px solid red;
	width: 100%;
	text-align: right;
	padding: 0px 80px 0px 80px;
}

.icons {
	border: 1px solid red;
	padding: 0px 80px 0px 80px;
	overflow: auto;
}

.icons>div {
	float: left;
	border: 1px solid red;
	padding-right: 20px;
}

.reply {
	border: 1px solid red;
	padding: 0px 80px 0px 80px;
}

.reply>div {
	border: 1px solid red;
}

.reply_list {
	border: 1px solid red;
	padding: 0px 80px 0px 80px;
}

.reply_title {
	border: 1px solid red;
	overflow: auto;
}

.reply_title>div {
	float: left;
	border: 1px solid red;
}

.reply_title>.rp_id {
	border: 1px solid red;
	width: 85%;
	padding-left: 10px;
}

.reply_title>.rp_time {
	width: 15%;
	text-align: center;
}

.reply_contents {
	overflow: auto;
	border: 1px solid red;
	width: 100%;
}

.reply_contents>div {
	float: left;
	border: 1px solid red;
}

.reply_contents>.rp_content {
	border: 1px solid red;
	width: 85%;
}

.rp_content>input {
	width: 100%;
}

.rp_btns {
	width: 15%;
	text-align: center;
}

.rp_contents:hover {
	cursor:pointer ;
}

.re_reply{
    border: 1px solid red;
    width: 100%;
    float: left;
    padding: 0px 80px 0px 80px;
}

.re_reply>.re_rp_title{
    overflow: auto;
    border: 1px solid red;
}

.re_rp_title>div{
    float: left;
    border: 1px solid red;
}

.re_rp_title>.re_rp_id{
    width: 85%;
}

.re_rp_title>.re_rp_time{
    width: 15%;
}

.re_reply>.re_rp_contents{
    overflow: auto;
}

.re_rp_contents>div{
    float: left;
    border: 1px solid red;
}

.re_rp_contents>.re_rp_content{
    width: 85%;
}

.re_rp_contents>.re_rp_btns{
    width: 15%;
}

/* 링크 속성 지우기 */
a {
	text-decoration: none
}

a:hover {
	text-decoration: none;
	color: black;
}

a:link {
	text-decoration: none;
	color: black;
}

a:visited {
	text-decoration: none;
	color: black;
}

a:active {
	text-decoration: none;
	color: black;
}

.fa-home {
	color: rgb(56, 181, 174);
}

.fa-thumbs-up, .fa-comment-dots {
	color: rgb(151, 151, 151);
}

.icon_recommand :hover {
	cursor: pointer;
}
</style>
</head>

<body>

	<!-- .banner에 이미지 추가해야한다.-->
	<div class="banner">
		<div class="banner_title" href="">여행지 게시판</div>
		<div class="banner_content">각 지역의 여행 후기를 남겨보세요</div>
	</div>
	<div class="container">
		<div class="root">
			<div class="home">
				<a href="/"><i class="fas fa-home"></i></a>
			</div>
			<div>></div>
			<div class="community" href="">커뮤니티</div>
			<div>></div>
			<div class="tourboard">
				<a href="/tourboard/list?cpage=1">여행지 게시판</a>
			</div>
		</div>
		<form action="/tourboard/modify" method="post" id="frmDetail"
			enctype="multipart/form-data">
			<div class="writeForm">
				<div class="catetitle">
					<div class="category">
						<input type=text value="[${dto.category }]" id="discategory"
							style="border: 0px; text-align: center; width: 100%;" readonly>
						<select style="display: none" ; id="modcategory" name="category">
							<option value="">말머리</option>
							<option value="명소">명소</option>
							<option value="문화">문화</option>
							<option value="생태">생태</option>
							<option value="체험">체험</option>
						</select>
					</div>
					<div class="title">
						<input type=hidden value="${dto.seq}" name=seq> <input
							type=text placeholder="제목을 입력하세요" id="title" name="title"
							value="${dto.title }" readonly>
					</div>
				</div>
				<br>
				<div class="contents" style="margin-left: 80px;">
					<textarea class="summernote" id="summernote" rows="5" name="explanation" style="height: 300px;">${dto.contents }</textarea>
				</div>
			</div>
			<div class="ft_btn">
				<a href="/tourboard/list?cpage=1"><button type=button>목록으로</button></a>
				<button type=button id=mod>수정하기</button>
				<button type=button id=del>삭제하기</button>
				<button type=button id=modOk style="display: none;">수정완료</button>
				<button type=button id=modCancel style="display: none;">취소</button>
			</div>
		</form>
		<br>
		<div class="icons">
			<div class="icon_recommand">
				<i class="fas fa-thumbs-up fa-lg"> 3</i>
			</div>
			<div class="icon_reply">
				<i class="far fa-comment-dots fa-lg"> ${dto.rep_count }</i>
			</div>
		</div>
		<br>
		<form action="/tourreply/reply" method="post" id="frmReply"	enctype="multipart/form-data">
			<div class="reply">
				<input type=hidden value="${dto.seq}" name=rseq>
				<div class="rp_input">
					<input type=text placeholder="댓글을 입력하세요" style="width: 100%; height: 30px;" name="reply">
				</div>
				<div class="rp_write" style="text-align: right;">
					<button type=submit id="write_btn">작성하기</button>
				</div>
			</div>
		</form>

		<div class="reply_list">
            <hr>
			<c:forEach var="rp" items="${rp_list }">
				<form method="post" id="frmRpMod" enctype="multipart/form-data">
					<div class="reply_title">
						<input type=hidden value="${rp.seq}" name=seq> <input type=hidden value="${rp.par_seq}" name=par_seq>
						<div class="rp_id">${rp.mem_seq }</div>
						<div class="rp_time" name="writen_date">${rp.writen_time }</div>
					</div>
					<br>
					<div class="reply_contents " style="text-align: right;">
						<div class="rp_content">
							<input type=text value="${rp.contents }" class="rp_contents" id="rp_contents${rp.seq }" name="contents" readonly>
						</div>
						<div class="rp_btns">
							<button type=button class="rp_reply_btn" id="rp_reply_btn${rp.seq }">rep</button>
							<button type=button class="rp_mod_btn" id="rp_mod_btn${rp.seq }">mod</button>
							<button type=button class="rp_del_btn" id="rp_del_btn${rp.seq }" style="color: red;"><b>del</b></button>
							<button type=submit class="rp_modOk_btn" id="rp_modOk_btn${rp.seq }" style="display: none;" formaction="/tourreply/modify">ok</button>
							<button type=button class="rp_cancle_btn" id="rp_cancle_btn${rp.seq }" style="color: red; display: none;"><b>can</b></button>
						</div>
						
						<c:forEach var="re" items="${re_list }">
						<c:choose>
							<c:when test="${re.par_seq == rp.seq}">
                       			<div class="re_reply" id="re_reply${rp.seq }">
                            		<div class="re_rp_title">
                                		<div class="re_rp_id" style="text-align:left;"> ${re.mem_seq }</div>
                                		<div class="re_rp_time" style="text-align:center;">${re.writen_time }</div>
                            		</div>
                            		<br>
                            		<div class="re_rp_contents">
		                                <div class="re_rp_content">
		                                	<input type=text style="width:4%; border:0px;" value="@${rp.mem_seq }" readonly>
                                			<input type=text style="width:95%;" id="recontent${re.seq }" name="recontent" value="${re.contents}" readonly>
                                		</div>
                                		<div class="re_rp_btns" style="text-align:center">
                                			<button type=button class="re_mod_btn" id="re_mod_btn${re.seq }" rpseq=${rp.seq }>mod</button>
                                			<button type=button class="re_del_btn" id="re_del_btn${re.seq }">del</button>
                                			<button type=button class="re_modOk_btn" id="re_modOk_btn${re.seq }" rpseq=${rp.seq } style="display: none;">ok</button>
                                			<button type=button class="re_cancle_btn" id="re_cancle_btn${re.seq }" style="display: none;">can</button>
                                		</div>
                            		</div>
                        		</div>
                        	</c:when>
                        </c:choose>
                        </c:forEach>
                        
                        <div class="re_reply_input" id="re_reply_input${rp.seq }" style="display: none;">
                            <div class="re_reply_content">
                            	<input type=hidden value="${dto.seq}" name=writeseq>
                            	<input type=hidden value="${rp.seq}" name=rpseq>
                            	<input type=text placeholder="댓글을 입력하세요" name=recontents>
                            </div>
                            <div class="re_reply_input_btn">
                                <button type=submit formaction="/tourreply/rereply">ok</button>
                                <button type=button class="rp_reply_cancle_btn" id="rp_reply_cancle_btn${rp.seq }">can</button>
                            </div>
                        </div>
					</div>
					<br>
				</form>
			</c:forEach>			
		</div>        

	</div>
	
	<script>
		$(".re_del_btn").on("click", function(){
			
			let id = this.id.substr(10);
			location.href = "/tourreply/redelete?idseq="+id+"&writeseq=${dto.seq}";
		})
	</script>
	
	<script>
		$(".re_modOk_btn").on("click", function(){
// 			re_modOk_btn${re.seq}${rp.seq }
			let id = this.id.substr(12);
			let rp = $(this).attr(rpseq);
			console.log("id : rp = " + id + " : " + rp);
			let recontent = $("#recontent${re.seq }"+id).val();
			
			location.href = "/tourreply/remodify?writeseq=${dto.seq}&idseq="+id+"&recontent="+recontent;
		})
	</script>
	
	<script>
		$(".re_cancle_btn").on("click", function(){
					
			location.reload();
		})
	</script>
	
	<script>
		$(".re_mod_btn").on("click", function(){
			let id = this.id.substr(10);
			let mod_id = $(this).attr("rpseq");
			
			console.log("mod_btn 눌렀을 때 id : mod_id = " + id + " : " + mod_id);
			$("#re_mod_btn"+id+mod_id).css("display","none");
			$("#re_del_btn"+id).css("display","none");
			$("#re_modOk_btn"+id+mod_id).css("display","inline");
			$("#re_cancle_btn"+id).css("display","inline");	
			$("#recontent"+id).removeAttr("readonly");
		})
		
	</script>
	
	<script>
		$(".rp_reply_cancle_btn").on("click", function(){
			let id = this.id.substr(19)
			$("#re_reply_input"+id).css("display", "none");
		})
	</script>
	<script>
		$(".rp_reply_btn").on("click", function(){
			let id = this.id.substr(12);
			$("#re_reply_input"+id).css("display", "inline");
		})
	</script>
	
	<script>
		$(".rp_del_btn").on("click", function() {
			if (confirm("댓글을 삭제하시겠습니까?")) {
				let id = this.id.substr(10);
				location.href = "/tourreply/delete?rpseq=" + id	+ "&bseq=${dto.seq}";
			}
		})
	</script>

	<script>
		$(".rp_mod_btn").on("click", function() {
			//${rp.seq }
			let id = this.id.substr(10);

			rpContents = $("#contents" + id).val();
			$("#rp_mod_btn" + id).css("display", "none");
			$("#rp_del_btn" + id).css("display", "none");
			$("#rp_cancle_btn" + id).css("display", "inline");
			$("#rp_modOk_btn" + id).css("display", "inline");
			$("#rp_reply_btn" + id).css("display", "none");
			$("#rp_contents" + id).removeAttr("readonly");
		})
	</script>

	<script>
		$(".rp_cancle_btn").on("click", function() {

			location.reload();
		})
	</script>

	<script>
		$("#write_btn").on("click", function() {
			if (confirm("이대로 작성하시겠습니까?")) {
				$("#frmReply").submit();
			}
		});
	</script>

	<script>
		$(".icon_recommand").on("click", function() {
			location.href = "/tourboard/like";
		})
	</script>

	<script>
		$("#del").on("click", function() {
			if (confirm("정말 삭제하시겠습니까?")) {

				location.href = "/tourboard/delete?seq=${dto.seq}";
			}
		});
	</script>

	<script>
		$("#list_btn").on("click", function() {
			history.back();
		})
	</script>

	<script>
		let bkTitle = "";
		let bkContents = "";

		$("#mod").on("click", function() {

			bkTitle = $("#title").val();
			bkContents = $("#summernote").val();

			console.log("bkTitle : " + bkTitle + "bkContents : " + bkContents);

			$("#title").removeAttr("readonly");
			$("#contents").removeAttr("readonly");
			$("#mod").css("display", "none");
			$("#del").css("display", "none");
			$("#modOk").css("display", "inline");
			$("#modCancel").css("display", "inline");
			$("#discategory").css("display", "none");
			$("#modcategory").css("display", "inline");
			
			$('.summernote').summernote({
				airMode : false
			});
			
			// 서머노트 쓰기 활성화
			$('#summernote').summernote('enable');
		});
	</script>	

	<script>
		$("#modCancel").on("click", function() {
			if (confirm("정말 취소하시겠습니까?")) {

				location.reload();
			}
		})
	</script>

	<script>
		$("#modOk").on("click", function() {
			if ($("#modcategory").val() == "") {

				alert("말머리를 선택해주세요");
				return false;
			}

			if ($("#title").val() == "") {

				alert("제목을 입력해주세요");
				return false;
			}

			if ($("#summernote").val() == "") {

				alert("내용을 입력해주세요");
				return false;
			}

			if (confirm("이대로 작성하시겠습니까?")) {
				$("#frmDetail").submit();
			}
		});
	</script>

	<script>
		$(document).ready(function() {
			//여기 아래 부분
			$('#summernote').summernote({
				height : 300, // 에디터 높이
				minHeight : 300, // 최소 높이
				maxHeight : null, // 최대 높이
				focus : true, // 에디터 로딩후 포커스를 맞출지 여부
				lang : "ko-KR", // 한글 설정
				airMode: false,
				
				toolbar: [
				    // [groupName, [list of button]]
				    ['fontname', ['fontname']],
				    ['fontsize', ['fontsize']],
				    ['style', ['bold', 'italic', 'underline','strikethrough', 'clear']],
				    ['color', ['forecolor','color']],
				    ['table', ['table']],
				    ['para', ['ul', 'ol', 'paragraph']],
				    ['height', ['height']],
				    ['insert',['picture','link','video']],
				    ['view', ['fullscreen', 'help']]
				  ],
				fontNames: ['Arial', 'Arial Black', 'Comic Sans MS', 'Courier New','맑은 고딕','궁서','굴림체','굴림','돋움체','바탕체'],
				fontSizes: ['8','9','10','11','12','14','16','18','20','22','24','28','30','36','50','72'],
				placeholder : '최대 2048자까지 쓸 수 있습니다' //placeholder 설정
			});
			$('#summernote').summernote('disable');
		});
	</script>

</body>
</html>