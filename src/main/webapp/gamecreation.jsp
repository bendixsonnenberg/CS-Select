    <%@ include file="WEB-INF/jspf/header.jspf" %>
        <fmt:bundle basename="locale.Locale">
            <div>
            <%@ include file="WEB-INF/jspf/organiserNavbar.jspf" %>
            <div class="top-buffer"></div>
            <div class="container h-10 title"><fmt:message key="creation"/></div>
            <div class="container" id="gamecreation">
                <div class="row">
                    <div class="col">
                        <div class="row" id="title-area">
                            <gametitle></gametitle>
                        </div>
                        <div class="row" id="invite-area">
                            <invite></invite>
                        </div>
                    </div>
                    <div class="col" id="mode-area">
                        <mode></mode>
                    </div>
                    <div class="col" id="pattern-area">
                        <pat></pat>
                    </div>
                </div>
                <div class="row">
                    <div class="col" id="description-area">
                        <description></description>
                    </div>
                    <div class="col" id="termination-area">
                        <termination></termination>
                    </div>
                    <div class="col">
                        <div class="row" id="feature-area">
                            <features></features>
                        </div>
                        <div class="row" id="database-area">
                            <database></database>
                        </div>
                        <div class="row" id="control-area">
                            <control></control>
                        </div>
                    </div>
                </div>
            </div>
            </div>
            <script src="src/js/gamecreation.js"></script>
        </fmt:bundle>
        <%@ include file="WEB-INF/jspf/footer.jspf" %>