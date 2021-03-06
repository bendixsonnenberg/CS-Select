<%@ include file="WEB-INF/jspf/header.jspf" %>
<fmt:bundle basename="locale.Locale">
    <div>
        <%@ include file="WEB-INF/jspf/playerNavbar.jspf" %>
        <div class="top-buffer"></div>
        <div class="container">
            <div id="playerAlerts">
            <div class="alert alert-success" v-bind:class="{collapse: !gameTerminated}" role="alert">
                <fmt:message key="gameTerminated"/>
            </div>
            </div>
            <div class="row">
                <div class="col-md-3">
                    <div class="container h-10 title">
                        <fmt:message key="dailyChallenge"/>
                    </div>
                    <div id="daily">
                        <daily-challenge v-bind:daily="daily"></daily-challenge>
                    </div>
                </div>
                <div class="col-md-4" id="stats">
                    <div class="container h-10 title">
                        <fmt:message key="playerInformation"/>
                    </div>
                    <stats-display v-bind:username="username"
                                   v-bind:points="points"></stats-display>
                </div>
                <div class="col-md-5 overflow-auto" id="leaderboard">
                    <div class="container h-10 title">
                        <fmt:message key="topFiveLastWeek"/>
                    </div>
                    <table class="table">
                        <thead>
                        <tr>
                            <th>#</th>
                            <th><fmt:message key="username"/></th>
                            <th><fmt:message key="points"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr is="leaderboard-element" v-for="player in playerList"
                            v-bind:key="player.place"
                            v-bind:place="player.place"
                            v-bind:username="player.username"
                            v-bind:points="player.points">
                        </tr>

                        </tbody>
                    </table>
                </div>
            </div>
            <div class="top-buffer"></div>
            <div class="row">
                <div class="col-md-7 h-100 overflow-auto" id="games">
                    <div class="container h-10 title">
                        <fmt:message key="activeGames"/>
                    </div>
                    <game-display
                            v-for="game in listOfGames"
                            v-bind:game="game"
                            v-bind:key="game.id">

                    </game-display>
                </div>
                <div class="col-md-5 h-100" id="invites">
                    <div class="container h-10 title">
                        <fmt:message key="invitedGames"/>
                    </div>
                    <div class="overflow-auto h-100">
                        <invite-element v-for="invite in listOfInvites"
                                        v-bind:key="invite.id"
                                        v-bind:game-id="invite.id"
                                        v-bind:title="invite.title">

                        </invite-element>
                    </div>
                </div>

            </div>
        </div>
    </div>
    <script src="src/js/player.js"></script>
</fmt:bundle>
<%@ include file="WEB-INF/jspf/footer.jspf" %>