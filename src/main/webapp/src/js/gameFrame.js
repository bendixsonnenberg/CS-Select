const gameFrame = new Vue({
    el: '#gameFrame',
    data: {
        featureList: [],
        options: {},
        gameType: '',
        forceUpdate: 1,
        counter: 0,
        buttonState: true,
        points: undefined,
        alerts: [] // alerts are cleared on nextRound
    },
    mounted() {
        this.getNextRound();
    },
    methods: {
        addAlert(alert) {
            this.alerts.push(alert);
        },
        clearAlerts() {
            this.alerts = [];
        },
        unlockButton(done) {
            this.buttonState = !done;
        },
        getSelectedFeaturesById() {
            let idList = [];
            for (let i = 0; i < this.featureList.length; i++) {
                if (this.featureList[i].toggled) {
                    idList.push(this.featureList[i].id)
                }
            }
            return idList;
        },
        getUselessFeaturesById() {
            let idList = [];
            for (let i = 0; i < this.featureList.length; i++) {
                if (this.featureList[i].useless) {
                    idList.push(this.featureList[i].id);
                }
            }
            return idList;
        },
        sendResults() {
            if (!this.buttonState) { // truly only send if the game is finished

                axios({
                    method: 'post',
                    url: 'games/' + localStorage.getItem("gameId") + "/play",
                    data: {
                        useless: JSON.stringify(this.getUselessFeaturesById()),
                        selected: JSON.stringify(this.getSelectedFeaturesById())
                    }
                }).then(function (response) {
                    gameFrame.points = response.data;
                    gameFrame.getNextRound();
                })

            }
        },
        skip() {
            axios({
                method: 'post',
                url: 'games/' + localStorage.getItem("gameId") + "/skip",
                data: {
                    useless: JSON.stringify(this.getUselessFeaturesById())
                }
            }).then(function (value) {
                gameFrame.getNextRound();
            })

        },
        getNextRound() {
            this.clearAlerts();

            axios({
                method: 'post',
                url: 'games/' + localStorage.getItem("gameId") + "/start"
            }).then(function (response) {
                if (response.status === 204) {
                    localStorage.setItem("gameTerminated", true);
                    gameFrame.quit();
                }
                $('.modal').modal('hide'); // close all open modals
                gameFrame.featureList = response.data.listOfFeatures;
                gameFrame.featureList.forEach(function (feature) {
                    // add necessary properties
                    feature.toggled = false;
                    feature.useless = false;
                });
                gameFrame.options = response.data.options;
                gameFrame.gameType = response.data.gameType;
                gameFrame.forceUpdate++;
                gameFrame.buttonState = true;
                axios('users/streak').then(function (response) {
                    gameFrame.counter = response.data;
                })
            })


        },
        quit() {
            window.location.href = 'player.jsp';
        },

    }


});