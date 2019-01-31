Vue.component('active-games-display', {
    props: ['game', ],
    template: '<div class="container">' +
        '       <div class="container">' +
        '           <div class="row">' +
        '               <div class="col-md-6 col-sm-12 col-xs-12"><div>{{ game.title }}</div>' +
        '                   <div>{{ game.type }}</div>' +
        '                   <div>{{ game.termination  }}</div>' +
        '               </div>' +
        '               <div class="col">' +
        '                   <input type="button" class="btn btn-secondary float-right btn-space"' +
        '                        v-on:click="terminate(game.id)" :value="localisation.terminate">' +
        '                   <input type="button" class="btn btn-primary float-right btn-space" :value="localisation.invite">' +
        '               </div>' +
        '            </div>' +
        '       </div>' +
        '      </div>',
    methods: {
        terminate: function(gameId) {
            axios({
                method: 'post',
                url: 'create/terminate',
                params: {
                    gameId: gameId
                }
            })
        }
    }
});

Vue.component('terminated-games-display', {
    props: ['game'],
    template: '<div class="container">' +
        '       <div class="container">' +
        '           <div class="row">' +
        '               <div class="col-md-6 col-sm-12 col-xs-12"><div>{{ game.title }}</div>' +
        '                   <div>{{ game.type }}</div>' +
        '                   <div>{{ game.termination  }}</div>' +
        '               </div>' +
        '               <div class="col">' +
        '                   <input type="button" class="btn btn-secondary float-right btn-space" v-on:click="remove(game.id)" :value="localisation.del">' +
        '               </div>' +
        '            </div>' +
        '       </div>' +
        '      </div>',
    methods: {
        remove: function (gameId) {
            axios({
                method: 'post',
                url: 'create/delete',
                params: {
                    gameId: gameId
                }
            });
            terminatedGames.listOfGames.forEach(function (value, index) { // remove from the list without reloading page
                if (value.id == gameId) terminatedGames.listOfGames.splice(index, 1);
            });
        }
    }
});

Vue.component('stats-display', {
    props: [''],
    template: ''
});

var activeGames = new Vue({
    el: "#active",
    data: {
        listOfGames: [{title:"myCoolGame", type:"Matrix", termination:0, id: 1},
            {title:"myRatherAmusingGame", type:"Binär", termination:0, id: 2},
            {title:"myUnderwhelmingGame", type:"Matrix", termination:0, id: 3},
            {title:"myDisappointingGame", type:"Matrix", termination:0, id: 4}]
    },
    mounted: function () {
        axios({
            method: 'get',
            url: "create/active"
        }).then(function (response) {
            activeGames.listOfGames = response.data
        })
    }

});

var terminatedGames = new Vue({
    el: "#terminated",
    data: {
        listOfGames: [{title:"myOldGame", type:"Matrix", termination:0, id: 1}]
    },
    mounted: function () {
        axios({
            method: 'get',
            url: "create/terminated"
        }).then(function (response) {
            terminatedGames.listOfGames = response.data
        })
    }
});

var stats = new Vue({
    el: "#stats"
});