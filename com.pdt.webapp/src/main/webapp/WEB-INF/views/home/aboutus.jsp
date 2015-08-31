<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<div class="main-panel">
        <div class="container">
            <div class="row">
                <div class="col-sm-9">
                    <div class="tour-breadcrumb">
                        <ul class="breadcrumb">
                            <li>
                                <a href='<c:url value="/home"/>'>Home</a>
                            </li>
                            <li class="active">
                                <span>${articles.title}</span>
                            </li>
                        </ul>
                    </div>
                    <div></div>
                    <div class="panel">
						${articles.content}
                    </div>
                </div>
                
                    <div class="tips-filters tour-box">
                       <!--  <div class="tour-head head-info">
                            <h5 class="text-uppercase">Tips</h5>
                            <span class="head-arrow"></span>
                        </div> -->
                        <!-- <div class="tour-content">
                            <div class="tip-item">
                                <table>
                                    <tr>
                                        <td class="photo">
                                            <a href="#">
                                                <img src="Uploads/tip-f-1.jpg" alt="" class="img-responsive" />
                                            </a>
                                        </td>
                                        <td>
                                            <strong>
                                                <a href="#">Món ngon ở Lai Châu</a>
                                            </strong>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                            <div class="tip-item">
                                <table>
                                    <tr>
                                        <td class="photo">
                                            <a href="#">
                                                <img src="Uploads/tip-f-2.jpg" alt="" class="img-responsive" />
                                            </a>
                                        </td>
                                        <td>
                                            <strong>
                                                <a href="#">Những trải nghiệm bạn nhất định phải thử khi đến Đà Nẵng</a>
                                            </strong>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                            <div class="tip-item">
                                <table>
                                    <tr>
                                        <td class="photo">
                                            <a href="#">
                                                <img src="Uploads/tip-f-3.jpg" alt="" class="img-responsive" />
                                            </a>
                                        </td>
                                        <td>
                                            <strong>
                                                <a href="#">Phượt an toàn</a>
                                            </strong>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                            <div class="tip-item">
                                <table>
                                    <tr>
                                        <td class="photo">
                                            <a href="#">
                                                <img src="Uploads/tip-f-4.jpg" alt="" class="img-responsive" />
                                            </a>
                                        </td>
                                        <td>
                                            <strong>
                                                <a href="#">Du lịch Huế từ A đến Z dịp Festival</a>
                                            </strong>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                            <div class="tip-item">
                                <table>
                                    <tr>
                                        <td class="photo">
                                            <a href="#">
                                                <img src="Uploads/tip-f-5.jpg" alt="" class="img-responsive" />
                                            </a>
                                        </td>
                                        <td>
                                            <strong>
                                                <a href="#">Ăn ngon ở Hải Phòng</a>
                                            </strong>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                            <div class="tip-item">
                                <table>
                                    <tr>
                                        <td class="photo">
                                            <a href="#">
                                                <img src="Uploads/tip-f-6.jpg" alt="" class="img-responsive" />
                                            </a>
                                        </td>
                                        <td>
                                            <strong>
                                                <a href="#">Phượt A Pa Chải</a>
                                            </strong>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                            <div class="tip-item">
                                <table>
                                    <tr>
                                        <td class="photo">
                                            <a href="#">
                                                <img src="Uploads/tip-f-7.jpg" alt="" class="img-responsive" />
                                            </a>
                                        </td>
                                        <td>
                                            <strong>
                                                <a href="#">Kinh nghiệm bảo vệ làn da khi đi du lịch</a> 
                                            </strong>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                            <br />
                            <br />
                            <br />
                        </div> -->
                    </div>
                </div>
            </div>
        </div>
    </div>