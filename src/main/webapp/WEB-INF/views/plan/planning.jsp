<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
  <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
	crossorigin="anonymous"></script>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.22.2/moment.min.js"></script>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/tempusdominus-bootstrap-4/5.0.1/js/tempusdominus-bootstrap-4.min.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/tempusdominus-bootstrap-4/5.0.1/css/tempusdominus-bootstrap-4.min.css" />
<link rel="stylesheet"
	href="https://netdna.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.css" />
<jsp:include page="../base/header.jsp"></jsp:include>

<script>

</script>
</head>
<body>

	<div class="container">
		<div class="row">
			<div class="col">
				<button type="button" class="btn btn-primary" id="firstBtn">일정 생성</button>
				<button type="button" class="btn btn-primary" id="secondBtn">여행지 선택</button>
				<button type="button" class="btn btn-primary" id="thirdBtn">일정 순서 지정</button>
				<button type="button" class="btn btn-primary" id="fourthBtn">메모</button>
			</div>
		</div>

		<!-- 날짜/제목 생성 -->
		<div class="row" id="firstForm">
			<div class="col">
			<c:if test="${!empty seq }">
				<form action="/plan/changeTheme" method="post">
				<input type="hidden" name="seq" value=${seq }>
			</c:if>
			<c:if test="${empty seq }">
				<form action="/plan/chooseTheme" method="post">
			</c:if>
				<div class="row">
					<div class="col-12">
						<h5 class="title">여행 계획 입력</h5>
						<div class="form-group">
							<label for="planTitle">제목</label> 
							<input type="text" name="title" class="form-control" id="planTitle" placeholder="제목을 입력하세요." value="${dto.title }">
						</div>
					</div>
					<div class="col-12">
						<div class="form-group">
							<label for="planConcept">여행 테마 선택</label> 
							<select class="form-control" id="planConcept" name="theme">
								<option value="힐링 여행" <c:if test="${dto.theme eq '힐링 여행'}">selected</c:if>>힐링 여행</option>
								<option value="먹거리 투어"<c:if test="${dto.theme eq '먹거리 투어'}">selected</c:if>>먹거리 투어</option>
								<option value="관광지 투어"<c:if test="${dto.theme eq '관광지 투어'}">selected</c:if>>관광지 투어</option>
								<option value="액티비티" <c:if test="${dto.theme eq '액티비티'}">selected</c:if>>액티비티</option>
								<option value="기분따라" <c:if test="${dto.theme eq '기분따라'}">selected</c:if>>기분따라</option>
							</select>
						</div>
					</div>
						<label for="planDate">날짜 선택</label>
						<div class="form-group col-6">						
								<div class="form-group">
									<div class="input-group date" id="datetimepicker1"
										data-target-input="nearest">
										<input type="text" class="form-control datetimepicker-input" name="startDate"
											data-target="#datetimepicker1" value="${dto.startDate }">
										<div class="input-group-append" data-target="#datetimepicker1"
											data-toggle="datetimepicker">
											<div class="input-group-text">
												<i class="fa fa-calendar"></i>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="form-group col-6">
								<div class="input-group date" id="datetimepicker2"
									data-target-input="nearest">
									<input type="text" class="form-control datetimepicker-input" name="endDate"
										data-target="#datetimepicker2" value="${dto.endDate }">
									<div class="input-group-append" data-target="#datetimepicker2"
										data-toggle="datetimepicker">
										<div class="input-group-text">
											<i class="fa fa-calendar"></i>
										</div>
									</div>
								</div>
							</div>

						<div class="row">
							<div class="col text-end">
							<c:if test="${empty seq }">
								<input id="chooseThemeBtn" type="submit" class="btn btn-primary" value="저장"></input>
							</c:if>
							<c:if test="${!empty seq }">
								<input id="changeThemeBtn" type="submit" class="btn btn-primary" value="수정"></input>
							</c:if>

							</div>
						</div>
					</div>
					</form>
			</div>
		</div>
		
		
		<!-- 여행지 생성 -->
		<div class="row" id="secondForm" style="display:none;">
			<div class="col">
				<div class="row">
					<div class="col">
						<button type="button" id="searchCallBtn">검색</button>
						<button type="button" id="savedCallBtn">찜 목록</button>
					</div>
				</div>
				
				<div class="row" id="search">
					<div class="col">
						<div class="row">
							<div class="col">
								<form id="searchForm" name="searchForm" method="post">
									<input type="text" name="target" placeholder="검색어를 입력하세요." id="searchTarget"> 
									<input type="hidden" value=1 name="page" id="searchPageNo"> 
									<input type="submit" onclick="searching(); return false">
								</form>												
							</div>
						</div>
						
						<form action="/plan/saveList" method="post">
						<div class="row">
								<input type="submit" class="btn" value="저장하기">
							<div class="col" id="searchResult">
								검색 결과가 없습니다.
							</div>
						</div>
						<div class="row">
							<div class="col">
								<input type="hidden" name="seq" value="${seq }">
							</div>
						</div>
						</form>
					</div>
				</div>
				
				<form action="/plan/saveList" method="post">
				<div class="row" id="saved" style="display:none;">
					<div class="col">
						<div class="row justify-content-center mt-4">
							<input type="submit" class="btn" value="저장하기">
							<input type="hidden" name="seq" value="${seq }">
							
				              <c:forEach var='cnt' items="${saveList}" varStatus="status">
				              <input type="checkbox" name="check" class="check" value="${mySaveListSeq[status.index]}&${cnt.name }&${cnt.lo_detail}&${cnt.photo }">
				                <div class="col-9 align-self-center mt-4 delParent">
				                  <div class="row">
				                    <div class="col-4">
				                      <a href="/area/detail?num=${mySaveListSeq[status.index]}">
				                        <img style="height:170px;" class="w-100" src="${cnt.photo}">
				                      </a>
				                    </div>
				                    <div class="col-8">
				                      <div class="row">
				                        <div class="col-10"><a href="/area/detail?num=${mySaveListSeq[status.index]}">${cnt.name}</a>
				                        </div>
				                      </div>
				                      <div class="row">
				                        <div class="col">${cnt.lo_detail}</div>
				                      </div>
				
				                      <div class="row align-items-end mb-0  h-50">
				                        <div class="col-2">
				                          <ul class="list-group-horizontal star p-0">
				                            <li>평점 :&nbsp;</li>
				                            <li>
				                              <c:if test="${empty savedListRate[status.index]}">
				                                -
				                              </c:if>
				                              ${savedListRate[status.index]}
				                            </li>
				                          </ul>
				                        </div>
				                      </div>
				                    </div>
				                  </div>
				                </div>
				              </c:forEach>
				              <div id="seeMoreTag"></div>
            </div>

            <c:if test="${fn:length(isMySaveListMore) == 1}">
            <div class="row mt-4 mb-4" id="seeMoreDel">
              <div class="col text-center">
                <button type="button" class="btn btn-success" id="seeMore">더보기</button>
              </div>
            </div>
            </c:if>
					</div>
				</div>
			</form>
			
			
			</div>			
		</div>
		
		
		<!-- 일정 순서 -->
		<div class="row" id="thirdForm" style="display:none;">
			<div class="col">
				일정순서 지정
			</div>
		</div>
		
		<div class="row" id="fourthForm" style="display:none;">
			<div class="col">
				메모
			</div>
		</div>
	</div>	
					

	<script type="text/javascript">
		$(function() {
			$('#datetimepicker1').datetimepicker({
				format : 'YYYY/MM/DD'
			});
			$('#datetimepicker2').datetimepicker({
				format : 'YYYY/MM/DD',
				useCurrent : false
			});
			$("#datetimepicker1").on("change.datetimepicker", function(e) {
				$('#datetimepicker2').datetimepicker('minDate', e.date);
			});
			$("#datetimepicker2").on("change.datetimepicker", function(e) {
				$('#datetimepicker1').datetimepicker('maxDate', e.date);
			});
		});
	</script>
	
	<script>
	//화면 숨기기/view 처리
		$("#firstBtn").on("click",function(){
			if($("#firstForm").css("display")==='none'){					
				$("#firstForm").show();
				$("#secondForm").hide();
				$("#thirdForm").hide();
				$("#fourthForm").hide();
			}else{
				$("#firstForm").hide();
			}
		})
		
		$("#secondBtn").on("click",function(){
			if(${!empty seq}){
				if($("#secondForm").css("display")==='none'){					
					$("#secondForm").show();
					$("#firstForm").hide();
					$("#thirdForm").hide();
					$("#fourthForm").hide();

				}else{
					$("#secondForm").hide();
				}				
			}else{
				alert("일정을 먼저 생성해주세요.")
			}
		})
		$("#thirdBtn").on("click",function(){
			if(${!empty seq}){
				if($("#thirdForm").css("display")==='none'){					
					$("#thirdForm").show();
					$("#firstForm").hide();
					$("#secondForm").hide();
					$("#fourthForm").hide();
				}else{
					$("#thirdForm").hide();
				}
			}else{
				alert("일정을 먼저 생성해주세요.")				
			}
		})
		$("#fourthBtn").on("click",function(){
			if(${!empty seq}){
				if($("#fourthForm").css("display")==='none'){					
					$("#fourthForm").show();
					$("#firstForm").hide();
					$("#secondForm").hide();
					$("#thirdForm").hide();
				}else{
					$("#fourthForm").hide();
				}	
			}else{
				alert("일정을 먼저 생성해주세요.")				
			}		
		})
		
		$("#searchCallBtn").on("click",function(){
			if($("#search").css("display")==='none'){					
				$("#search").show();
				$("#saved").hide();
			}else{
				$("#search").hide();
			}
		})

		$("#savedCallBtn").on("click",function(){
			if($("#saved").css("display")==='none'){					
				$("#saved").show();
				$("#search").hide();
			}else{
				$("#saved").hide();
			}
		})		
		
		
	function searching() {
        var formData = $('#searchForm').serialize() // serialize 사용
        $.ajax({
            url: "/plan/search",
            type: "POST",
            dataType:"json",
            cache: false,
            data: formData, // data에 바로 serialze한 데이터를 넣는다.
            success: function(data){
            	//리스트 출력
            	let value = "";
                for(let i = 0; i<data.response.body.items.item.length;i++){
			    	let sData = data.response.body.items.item[i];
			    	value += '<div class="row border"> <div class="col"> <input type="checkbox" name="check" class="check" value="'+sData.contentid+'&'+sData.title+'&'+sData.addr1+'&'+sData.firstimage+'"> <div>'+sData.title+'</div><br> <div>'+sData.addr1+'</div><br><div> <img src="'+sData.firstimage+'" style="width:150px;height:100px;"></div> </div></div>';
	            }
                value +='   <div class="btn-toolbar" role="toolbar" aria-label="Pagination"> <div class="btn-group me-2 mb-2" role="group" aria-label="First group">';
				let totalPage = data.response.body.totalCount/data.response.body.numOfRows;
				let pageNo = data.response.body.pageNo;
				//페이징
				value +='<div class="btn-toolbar" role="toolbar" aria-label="Pagination"> <div class="btn-group me-2 mb-2" role="group" aria-label="First group">';
				if(pageNo>1){
					value +='<button type="button" class="btn btn-outline-secondary page" id='+(pageNo-1)+'>이전 페이지</button>';
				}
				if(pageNo<totalPage){
					value +='<button type="button" class="btn btn-outline-secondary page" id='+(pageNo+1)+'>다음 페이지</button>';					
				}
	            $("#searchResult").html(value);
            
            
                console.log(data.response.body.totalCount);
                
            }
        }) 
    }
	
	$(document).on("click",".page",function(){
		console.log(this.id);
		$("#searchPageNo").val(this.id);
		searching();
		return false;
	})
		
	
	//찜목록 더보기
        let btn = 1;
        let isMore;
        $("#seeMore").on("click", function () {
          btn += 7;
          $.ajax({
            url: "/member/moreSaving",
            data: { btn: btn }
          }).done(function (res) {
            let result = JSON.parse(res);
            for (let i = 0; i < result.length; i++) {
              $("#seeMoreTag").before(
                `<input type="checkbox" name="check" class="check" value="\${result[i].seq}&\${result[i].name }&\${result[i].lo_datail}&\${result[i].photo }">
                <div class="col-9 align-self-center mt-4 delParent">
                  <div class="row">
                    <div class="col-4">
                      <a href="/area/detail?num=\${result[i].seq}">
                        <img style="height:170px;" class="w-100" src="\${result[i].photo}">
                      </a>
                    </div>
                    <div class="col-8">
                      <div class="row">
                        <div class="col-10"><a
                            href="/area/detail?num=\${result[i].seq}">\${result[i].name}</a></div>
                      </div>
                      <div class="row">
                        <div class="col">\${result[i].lo_detail}</div>
                      </div>
                      <div class="row align-items-end mb-0  h-50">
                        <div class="col-2">
                          <ul class="list-group-horizontal star p-0">
                            <li>평점 :&nbsp;</li>
                            <li>
                              \${result[i].savedListRate}
                            </li>
                          </ul>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>`
              )
              isMore = result[i].isMore;
            }
            if (isMore == 0) {
              $("#seeMoreDel").empty();
            }
          })
        });
		</script>
</body>
</html>