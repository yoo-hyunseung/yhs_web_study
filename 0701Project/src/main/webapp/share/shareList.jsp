<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class="container">
        <div class="row mb-4">
            <h2 class="col-6 tm-text-primary">
                공유주방
            </h2>
           </div>
        <div class="row tm-mb-90 tm-gallery">
        <!-- forEach -->
        <c:forEach var="slist" items="${slist }">
        	<div class="col-xl-3 col-lg-4 col-md-6 col-sm-6 col-12 mb-5">
                <figure class="effect-ming tm-video-item">
              	  <a href="../share/shareDetail.do?skdno=${slist.skdno }">
                    <img src="${slist.poster }" alt="Image" class="img-fluid rounded" style="width: 250px;height: 150px; border-radius:20px;">
                    <figcaption class="d-flex align-items-center justify-content-center">
                        <h2>
                        	<c:if test="${slist.title.length()>10 }">
                        		${slist.title.substring(0,10) }
                        	</c:if>
                        	<c:if test="${slist.title.length()<10 }">
                        		${slist.title }
                        	</c:if>
                        </h2>
                       
                    </figcaption>   
                     </a>                 
                </figure>
                <div class="d-flex justify-content-between tm-text-gray">
                	<span>${slist.max_mem }</span>               
                    <span>${slist.price }</span>
                </div>
            </div> 
            </c:forEach>
            <!-- forEach end -->  
        </div>
       
        <div class="row">
			<a href="shareList.do?page=${curpage>1?curpage-1:curpage }" class="btn btn-sm btn-info">이전</a>
			${curpage } page / ${totalpage } pages
			<a href="shareList.do?page=${curpage<totalpage?curpage+1:curpage }" class="btn btn-sm btn-warning">다음</a>
        </div>
    </div> 
        <!-- row -->
</body>
</html>